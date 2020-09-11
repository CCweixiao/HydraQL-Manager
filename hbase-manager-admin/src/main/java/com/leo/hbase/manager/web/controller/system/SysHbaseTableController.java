package com.leo.hbase.manager.web.controller.system;

import com.github.CCweixiao.exception.HBaseOperationsException;
import com.leo.hbase.manager.adaptor.model.FamilyDesc;
import com.leo.hbase.manager.adaptor.model.NamespaceDesc;
import com.leo.hbase.manager.adaptor.model.TableDesc;
import com.leo.hbase.manager.adaptor.service.IHBaseAdminService;
import com.leo.hbase.manager.common.annotation.Log;
import com.leo.hbase.manager.common.core.controller.BaseController;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.core.page.TableDataInfo;
import com.leo.hbase.manager.common.enums.BusinessType;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.common.utils.poi.ExcelUtil;
import com.leo.hbase.manager.framework.util.ShiroUtils;
import com.leo.hbase.manager.system.domain.SysHbaseTable;
import com.leo.hbase.manager.system.dto.FamilyDescDto;
import com.leo.hbase.manager.system.dto.NamespaceDescDto;
import com.leo.hbase.manager.system.dto.TableDescDto;
import com.leo.hbase.manager.system.service.ISysHbaseTableService;
import com.leo.hbase.manager.system.service.ISysHbaseTagService;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private ISysHbaseTagService sysHbaseTagService;
    @Autowired
    private IHBaseAdminService ihBaseAdminService;

    @RequiresPermissions("system:table:view")
    @GetMapping()
    public String table(ModelMap mmap) {
        final List<NamespaceDesc> namespaceDescList = ihBaseAdminService.listAllNamespaceDesc();
        mmap.put("namespaces", namespaceDescList);
        mmap.put("tags", sysHbaseTagService.selectAllSysHbaseTagList());
        return prefix + "/table";
    }

    @RequiresPermissions("system:table:detail")
    @GetMapping("/detail/{tableId}")
    public String detail(@PathVariable("tableId") Long tableId, ModelMap mmap) {
        SysHbaseTable sysHbaseTable = sysHbaseTableService.selectSysHbaseTableById(tableId);
        final TableDesc tableDesc = ihBaseAdminService.getTableDesc(sysHbaseTable.getTableName());
        String fullTableName = getFullTableName(tableDesc.getTableName());
        tableDesc.setTableName(fullTableName);
        TableDescDto tableDescDto = new TableDescDto().convertFor(tableDesc);
        String desc = StringUtils.getStringByEnter(110, tableDescDto.getTableDesc());
        tableDescDto.setTableDesc(desc);
        tableDescDto.setStatus(sysHbaseTable.getStatus());
        tableDescDto.setRemark(sysHbaseTable.getRemark());
        mmap.put("hbaseTable", tableDescDto);
        return prefix + "/detail";
    }

    @RequiresPermissions("system:table:detail")
    @GetMapping("/family/detail/{tableId}")
    public String familyDetail(@PathVariable("tableId") Long tableId, ModelMap mmap) {
        SysHbaseTable sysHbaseTable = sysHbaseTableService.selectSysHbaseTableById(tableId);
        String fullTableName = getFullTableName(sysHbaseTable.getTableName());
        sysHbaseTable.setTableName(fullTableName);
        mmap.put("tableObj", sysHbaseTable);
        sysHbaseTable = new SysHbaseTable();
        List<SysHbaseTable> tableList = sysHbaseTableService.selectSysHbaseTableList(sysHbaseTable);
        if (tableList == null || tableList.isEmpty()) {
            mmap.put("tableMapList", new ArrayList<>());
        } else {
            List<Map<String, Object>> tableMapList = new ArrayList<>(tableList.size());
            for (SysHbaseTable hbaseTable : tableList) {
                Map<String, Object> tableMap = new HashMap<>(2);
                tableMap.put("tableId", hbaseTable.getTableId());
                tableMap.put("tableName", getFullTableName(hbaseTable.getTableName()));
                tableMapList.add(tableMap);
                mmap.put("tableMapList", tableMapList);
            }
        }
        return "system/family/family";
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
        ExcelUtil<SysHbaseTable> util = new ExcelUtil<>(SysHbaseTable.class);
        return util.exportExcel(list, "table");
    }

    /**
     * 新增HBase表
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        List<NamespaceDescDto> namespaces = ihBaseAdminService.listAllNamespaceDesc().stream()
                .filter(namespaceDesc -> !"hbase".equals(namespaceDesc.getNamespaceName()))
                .map(namespaceDesc -> new NamespaceDescDto().convertFor(namespaceDesc)).collect(Collectors.toList());
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
    public AjaxResult addSave(@Validated TableDescDto tableDescDto) {
        String fullTableName = tableDescDto.getNamespaceId() + ":" + tableDescDto.getTableName();
        SysHbaseTable sysHbaseTable = sysHbaseTableService.selectSysHbaseTableByName(fullTableName);

        if (sysHbaseTable != null && sysHbaseTable.getTableId() > 0) {
            return error("HBase表[" + fullTableName + "]已经存在！");
        }
        if (ihBaseAdminService.tableIsExists(fullTableName)) {
            return error("HBase表[" + fullTableName + "]已经存在！");
        }

        TableDesc tableDesc = tableDescDto.convertTo();
        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(fullTableName));
        List<String> families = new ArrayList<>(tableDescDto.getFamilies().size());

        for (FamilyDescDto familyDescDto : tableDescDto.getFamilies()) {
            FamilyDesc familyDesc = familyDescDto.convertTo();

            if (families.contains(familyDesc.getFamilyName())) {
                throw new HBaseOperationsException("列簇[" + familyDesc.getFamilyName() + "]已经存在！");
            }

            families.add(familyDesc.getFamilyName());
            HColumnDescriptor columnDescriptor = new HColumnDescriptor(familyDesc.getFamilyName());
            columnDescriptor.setMaxVersions(familyDesc.getMaxVersions());
            columnDescriptor.setTimeToLive(familyDesc.getTimeToLive());
            columnDescriptor.setCompressionType(Compression.Algorithm.valueOf(familyDesc.getCompressionType()));
            tableDescriptor.addFamily(columnDescriptor);
        }
        boolean createTableRes = false;

        String startKey = tableDesc.getStartKey();
        String endKey = tableDesc.getEndKey();
        Integer numRegions = tableDesc.getPreSplitRegions();
        boolean preSplit1 = StringUtils.isNotEmpty(tableDesc.getPreSplitKeys());
        boolean preSplit2 = (StringUtils.isNotEmpty(startKey) && StringUtils.isNotEmpty(endKey) && numRegions > 0);

        if (preSplit1 && preSplit2) {
            return error("只能指定一种预分区方式！");
        }
        if (preSplit1) {
            String[] splitKeys = tableDesc.getPreSplitKeys().split(",");
            createTableRes = ihBaseAdminService.createTable(tableDescriptor, splitKeys);
        }
        if (preSplit2) {
            createTableRes = ihBaseAdminService.createTable(tableDescriptor, startKey, endKey, numRegions);
        }
        if (!preSplit1 && !preSplit2) {
            createTableRes = ihBaseAdminService.createTable(tableDescriptor);
        }
        if (!createTableRes) {
            return error("系统异常，HBase表[" + fullTableName + "]创建失败！");
        }

        sysHbaseTable = new SysHbaseTable();
        sysHbaseTable.setCreateBy(ShiroUtils.getSysUser().getLoginName());
        sysHbaseTable.setTableName(fullTableName);
        sysHbaseTable.setRemark(tableDescDto.getRemark());
        sysHbaseTable.setStatus(tableDescDto.getStatus());
        sysHbaseTable.setTagIds(tableDescDto.getTagIds());


        int saveTableRow = sysHbaseTableService.insertSysHbaseTable(sysHbaseTable);

        if (saveTableRow < 0) {
            return error("系统异常，HBase表信息[" + fullTableName + "]保存失败！");
        }

        return success("HBase表[" + fullTableName + "]创建成功！");
    }

    /**
     * 修改HBase
     */
    @GetMapping("/edit/{tableId}")
    public String edit(@PathVariable("tableId") Long tableId, ModelMap mmap) {
        final List<NamespaceDesc> namespaceDescList = ihBaseAdminService.listAllNamespaceDesc();
        SysHbaseTable sysHbaseTable = sysHbaseTableService.selectSysHbaseTableById(tableId);
        mmap.put("sysHbaseTable", sysHbaseTable);
        mmap.put("namespaces", namespaceDescList);
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
        final String fullHBaseTableName = exitsTable.getTableName();
        boolean changeTableDisabledStatusRes = false;

        if (ihBaseAdminService.isTableDisabled(fullHBaseTableName)) {
            changeTableDisabledStatusRes = ihBaseAdminService.enableTable(fullHBaseTableName);
        }
        if (!ihBaseAdminService.isTableDisabled(fullHBaseTableName)) {
            changeTableDisabledStatusRes = ihBaseAdminService.disableTable(fullHBaseTableName);
        }
        if (!changeTableDisabledStatusRes) {
            return error("系统异常，表状态修改失败！");
        }
        sysHbaseTable.setUpdateBy(ShiroUtils.getSysUser().getLoginName());
        return success("操作成功！");
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
        final String fullHBaseTableName = exitsTable.getTableName();

        if (!ihBaseAdminService.isTableDisabled(fullHBaseTableName)) {
            return error("非禁用状态的表不能被删除");
        }

        boolean deleteTableDisabledStatusRes = ihBaseAdminService.deleteTable(fullHBaseTableName);
        if (!deleteTableDisabledStatusRes) {
            return error("系统异常，表状态修改失败！");
        }
        return toAjax(sysHbaseTableService.deleteSysHbaseTableById(ids));
    }

    private String getFullTableName(String tableName) {
        if (tableName.contains(":")) {
            return tableName;
        } else {
            return "default" + ":" + tableName;
        }
    }
}
