package com.leo.hbase.manager.web.controller.system;

import com.leo.hbase.manager.adaptor.service.IHBaseAdminService;
import com.leo.hbase.manager.common.annotation.Log;
import com.leo.hbase.manager.common.core.controller.BaseController;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.core.page.TableDataInfo;
import com.leo.hbase.manager.common.enums.BusinessType;
import com.leo.hbase.manager.common.enums.HBaseDisabledFlag;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.common.utils.poi.ExcelUtil;
import com.leo.hbase.manager.framework.util.ShiroUtils;
import com.leo.hbase.manager.system.domain.SysHbaseNamespace;
import com.leo.hbase.manager.system.domain.SysHbaseTable;
import com.leo.hbase.manager.system.dto.SysHbaseFamilyModel;
import com.leo.hbase.manager.system.dto.SysHbaseTableDto;
import com.leo.hbase.manager.system.service.ISysHbaseNamespaceService;
import com.leo.hbase.manager.system.service.ISysHbaseTableService;
import com.leo.hbase.manager.system.service.ISysHbaseTagService;
import com.leo.hbase.manager.system.vo.HBaseTableDetailVo;
import com.leo.hbase.sdk.core.exception.HBaseOperationsException;
import com.leo.hbase.sdk.core.model.HFamilyBuilder;
import com.leo.hbase.sdk.core.model.HTableModel;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * HBaseController
 *
 * @author leojie
 * @date 2020-08-16
 */
@Controller
@RequestMapping("/system/table")
public class SysHbaseTableController extends BaseController {
    private String prefix = "system/table";

    @Autowired
    private ISysHbaseTableService sysHbaseTableService;
    @Autowired
    private ISysHbaseNamespaceService sysHbaseNamespaceService;
    @Autowired
    private ISysHbaseTagService sysHbaseTagService;
    @Autowired
    private IHBaseAdminService ihBaseAdminService;

    @RequiresPermissions("system:table:view")
    @GetMapping()
    public String table(ModelMap mmap) {
        mmap.put("namespaces", sysHbaseNamespaceService.selectAllSysHbaseNamespaceList());
        mmap.put("tags", sysHbaseTagService.selectAllSysHbaseTagList());
        return prefix + "/table";
    }

    @RequiresPermissions("system:table:detail")
    @GetMapping("/detail/{tableId}")
    public String detail(@PathVariable("tableId") Long tableId, ModelMap mmap) {
        SysHbaseTable sysHbaseTable = sysHbaseTableService.selectSysHbaseTableById(tableId);

        HBaseTableDetailVo hBaseTableDetailVo = new HBaseTableDetailVo();

        String namespace = sysHbaseTable.getSysHbaseNamespace().getNamespaceName();
        String tableName = sysHbaseTable.getTableName();
        String fullTableName = namespace + ":" + tableName;
        if ("default".equals(namespace)) {
            fullTableName = tableName;
        }
        hBaseTableDetailVo.setTableName(fullTableName);
        boolean tableIsDisabled = ihBaseAdminService.tableIsDisabled(fullTableName);
        if (tableIsDisabled) {
            hBaseTableDetailVo.setDisabledStatus(HBaseDisabledFlag.DISABLED.getCode());
        } else {
            hBaseTableDetailVo.setDisabledStatus(HBaseDisabledFlag.ENABLED.getCode());
        }
        String desc = StringUtils.getStringByEnter(110, ihBaseAdminService.getTableDesc(fullTableName));
        hBaseTableDetailVo.setTableDesc(desc);
        hBaseTableDetailVo.setRemark(sysHbaseTable.getRemark());
        mmap.put("hbaseTable", hBaseTableDetailVo);
        return prefix + "/detail";
    }

    /**
     * 查询HBase列表
     */
    @RequiresPermissions("system:table:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysHbaseTable sysHbaseTable) {
        startPage();
        List<SysHbaseTable> list = sysHbaseTableService.selectSysHbaseTableList(sysHbaseTable);
        return getDataTable(list);
    }

    /**
     * 导出HBase列表
     */
    @RequiresPermissions("system:table:export")
    @Log(title = "HBase", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysHbaseTable sysHbaseTable) {
        List<SysHbaseTable> list = sysHbaseTableService.selectSysHbaseTableList(sysHbaseTable);
        ExcelUtil<SysHbaseTable> util = new ExcelUtil<SysHbaseTable>(SysHbaseTable.class);
        return util.exportExcel(list, "table");
    }

    /**
     * 新增HBase表
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        List<SysHbaseNamespace> namespaces = sysHbaseNamespaceService.selectAllSysHbaseNamespaceList();
        namespaces = namespaces.stream().filter(namespace -> !"hbase".equals(namespace.getNamespaceName().toLowerCase()))
                .collect(Collectors.toList());
        mmap.put("namespaces", namespaces);
        mmap.put("tags", sysHbaseTagService.selectAllSysHbaseTagList());
        return prefix + "/add";
    }

    /**
     * 新增保存HBase
     */
    @RequiresPermissions("system:table:add")
    @Log(title = "HBase", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysHbaseTableDto sysHbaseTableDto) {
        SysHbaseNamespace sysHbaseNamespace = sysHbaseNamespaceService.selectSysHbaseNamespaceById(sysHbaseTableDto.getNamespaceId());
        String fullTableName = sysHbaseNamespace.getNamespaceName() + ":" + sysHbaseTableDto.getTableName();
        SysHbaseTable sysHbaseTable = sysHbaseTableService.selectSysHbaseTableByNamespaceAndTableName(sysHbaseTableDto.getNamespaceId(), sysHbaseTableDto.getTableName());

        if (sysHbaseTable != null && sysHbaseTable.getTableId() > 0) {
            return error("HBase表[" + fullTableName + "]已经存在！");
        }
        if (ihBaseAdminService.tableIsExists(fullTableName)) {
            return error("HBase表[" + fullTableName + "]已经存在！");
        }

        HTableModel hTableModel = new HTableModel();
        hTableModel.setTableName(fullTableName);
        List<HFamilyBuilder> familyBuilders = new ArrayList<>(sysHbaseTableDto.getFamilies().size());
        List<String> families = new ArrayList<>(sysHbaseTableDto.getFamilies().size());

        for (SysHbaseFamilyModel family : sysHbaseTableDto.getFamilies()) {
            if (families.contains(family.getName())) {
                throw new HBaseOperationsException("列簇[" + family.getName() + "]已经存在！");
            }
            families.add(family.getName());

            HFamilyBuilder familyBuilder = new HFamilyBuilder.Builder().familyName(family.getName())
                    .maxVersions(family.getMaxVersions()).timeToLive(family.getTimeToLive())
                    .compressionType(family.getCompressionType()).build();
            familyBuilders.add(familyBuilder);
        }
        hTableModel.sethFamilies(familyBuilders);

        boolean createTableRes = false;

        String startKey = sysHbaseTableDto.getStartKey();
        String endKey = sysHbaseTableDto.getEndKey();
        Integer numRegions = sysHbaseTableDto.getPreSplitRegions();
        boolean preSplit1 = StringUtils.isNotEmpty(sysHbaseTableDto.getPreSplitKeys());
        boolean preSplit2 = (StringUtils.isNotEmpty(startKey) && StringUtils.isNotEmpty(endKey) && numRegions > 0);

        if (preSplit1 && preSplit2) {
            return error("只能指定一种预分区方式！");
        }
        if (preSplit1) {
            String[] splitKeys = sysHbaseTableDto.getPreSplitKeys().split(",");
            createTableRes = ihBaseAdminService.createTable(hTableModel, splitKeys);
        }
        if (preSplit2) {
            createTableRes = ihBaseAdminService.createTable(hTableModel, startKey, endKey, numRegions);
        }
        if (!createTableRes) {
            return error("系统异常，HBase表[" + fullTableName + "]创建失败！");
        }
        sysHbaseTable = sysHbaseTableDto.convertTo();
        sysHbaseTable.setCreateBy(ShiroUtils.getSysUser().getLoginName());
        return toAjax(sysHbaseTableService.insertSysHbaseTable(sysHbaseTable));
    }

    /**
     * 修改HBase
     */
    @GetMapping("/edit/{tableId}")
    public String edit(@PathVariable("tableId") Long tableId, ModelMap mmap) {
        SysHbaseTable sysHbaseTable = sysHbaseTableService.selectSysHbaseTableById(tableId);
        mmap.put("sysHbaseTable", sysHbaseTable);
        mmap.put("namespaces", sysHbaseNamespaceService.selectAllSysHbaseNamespaceList());
        mmap.put("tags", sysHbaseTagService.selectSysHbaseTagsByTableId(tableId));
        return prefix + "/edit";
    }

    /**
     * 修改保存HBase
     */
    @RequiresPermissions("system:table:edit")
    @Log(title = "HBase", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysHbaseTable sysHbaseTable) {
        if (sysHbaseTable == null || sysHbaseTable.getTableId() < 1) {
            return error("待修改信息的HBase表的id不能为空！");
        }
        SysHbaseTable exitsTable = sysHbaseTableService.selectSysHbaseTableById(sysHbaseTable.getTableId());
        if (exitsTable == null || exitsTable.getTableId() < 1) {
            return error("待修改信息的表[" + sysHbaseTable.getTableId() + "]不存在！");
        }
        sysHbaseTable.setUpdateBy(ShiroUtils.getSysUser().getLoginName());

        return toAjax(sysHbaseTableService.updateSysHbaseTable(sysHbaseTable));
    }

    /**
     * 修改保存HBase表禁用状态
     */
    @RequiresPermissions("system:table:edit")
    @Log(title = "HBase", businessType = BusinessType.UPDATE)
    @PostMapping("/changeDisableStatus")
    @ResponseBody
    public AjaxResult changeDisableStatus(SysHbaseTable sysHbaseTable) {
        if (sysHbaseTable == null || sysHbaseTable.getTableId() < 1) {
            return error("待修改表的id不能为空！");
        }
        SysHbaseTable exitsTable = sysHbaseTableService.selectSysHbaseTableById(sysHbaseTable.getTableId());
        if (exitsTable == null || exitsTable.getTableId() < 1) {
            return error("待修改的表[" + sysHbaseTable.getTableId() + "]不存在！");
        }
        final String fullHBaseTableName = exitsTable.getSysHbaseNamespace().getNamespaceName() + ":" + exitsTable.getTableName();
        boolean changeTableDisabledStatusRes;
        if (HBaseDisabledFlag.ENABLED.getCode().equals(sysHbaseTable.getDisableFlag())) {
            changeTableDisabledStatusRes = ihBaseAdminService.enableTable(fullHBaseTableName);
        } else if (HBaseDisabledFlag.DISABLED.getCode().equals(sysHbaseTable.getDisableFlag())) {
            changeTableDisabledStatusRes = ihBaseAdminService.disableTable(fullHBaseTableName);
        } else {
            return error("暂时不支持的表禁用状态修改操作[" + sysHbaseTable.getDisableFlag() + "]");
        }
        if (!changeTableDisabledStatusRes) {
            return error("系统异常，表状态修改失败！");
        }

        sysHbaseTable.setUpdateBy(ShiroUtils.getSysUser().getLoginName());
        return toAjax(sysHbaseTableService.updateSysHbaseTableDisabledStatus(sysHbaseTable));
    }


    /**
     * 删除HBase
     */
    @RequiresPermissions("system:table:remove")
    @Log(title = "HBase", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(Long ids) {
        SysHbaseTable sysHbaseTable = sysHbaseTableService.selectSysHbaseTableById(ids);
        if (sysHbaseTable == null || sysHbaseTable.getTableId() < 1) {
            return error("待删除表的id不能为空！");
        }
        SysHbaseTable exitsTable = sysHbaseTableService.selectSysHbaseTableById(sysHbaseTable.getTableId());
        if (exitsTable == null || exitsTable.getTableId() < 1) {
            return error("待删除的表[" + sysHbaseTable.getTableId() + "]不存在！");
        }
        if (HBaseDisabledFlag.ENABLED.getCode().equals(sysHbaseTable.getDisableFlag())) {
            return error("非禁用状态的表不能被删除");
        }
        final String fullHBaseTableName = exitsTable.getSysHbaseNamespace().getNamespaceName() + ":" + exitsTable.getTableName();
        if (!ihBaseAdminService.tableIsDisabled(fullHBaseTableName)) {
            return error("非禁用状态的表不能被删除");
        }
        boolean deleteTableDisabledStatusRes = ihBaseAdminService.deleteTable(fullHBaseTableName);
        if (!deleteTableDisabledStatusRes) {
            return error("系统异常，表状态修改失败！");
        }
        return toAjax(sysHbaseTableService.deleteSysHbaseTableById(ids));
    }
}
