package com.leo.hbase.manager.web.controller.system;

import com.leo.hbase.manager.adaptor.model.HBaseTableDesc;
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
import com.leo.hbase.manager.system.domain.SysHbaseTable;
import com.leo.hbase.manager.system.dto.SysHbaseTableDto;
import com.leo.hbase.manager.system.service.ISysHbaseNamespaceService;
import com.leo.hbase.manager.system.service.ISysHbaseTableService;
import com.leo.hbase.manager.system.service.ISysHbaseTagService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return prefix + "/table";
    }

    @RequiresPermissions("system:table:detail")
    @GetMapping("/detail/{tableId}")
    public String detail(@PathVariable("tableId") Long tableId, ModelMap mmap) {
        SysHbaseTable sysHbaseTable = sysHbaseTableService.selectSysHbaseTableById(tableId);
        HBaseTableDesc hBaseTableDesc = new HBaseTableDesc();
        String namespace = sysHbaseTable.getSysHbaseNamespace().getNamespaceName();
        String tableName = sysHbaseTable.getTableName();
        String fullTableName = namespace + ":" + tableName;
        if ("default".equals(namespace)) {
            fullTableName = tableName;
        }
        hBaseTableDesc.setTableName(fullTableName);
        boolean tableIsDisabled = ihBaseAdminService.tableIsDisabled(fullTableName);
        if(tableIsDisabled){
            hBaseTableDesc.setDisabledStatus(HBaseDisabledFlag.DISABLED.getCode());
        }else{
            hBaseTableDesc.setDisabledStatus(HBaseDisabledFlag.ENABLED.getCode());
        }
        String desc = StringUtils.getStringByEnter(110, ihBaseAdminService.getTableDesc(fullTableName));
        hBaseTableDesc.setTableDesc(desc);
        mmap.put("hbaseTable", hBaseTableDesc);
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
        mmap.put("namespaces", sysHbaseNamespaceService.selectAllSysHbaseNamespaceList());
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
        SysHbaseTable sysHbaseTable = sysHbaseTableDto.convertTo();

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
