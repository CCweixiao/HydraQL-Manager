package com.leo.hbase.manager.web.controller.system;

import java.util.List;

import com.leo.hbase.manager.system.dto.SysHbaseTableDto;
import com.leo.hbase.manager.system.service.ISysHbaseNamespaceService;
import com.leo.hbase.manager.system.service.ISysHbaseTagService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.leo.hbase.manager.common.annotation.Log;
import com.leo.hbase.manager.common.enums.BusinessType;
import com.leo.hbase.manager.system.domain.SysHbaseTable;
import com.leo.hbase.manager.system.service.ISysHbaseTableService;
import com.leo.hbase.manager.common.core.controller.BaseController;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.utils.poi.ExcelUtil;
import com.leo.hbase.manager.common.core.page.TableDataInfo;

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

    @RequiresPermissions("system:table:view")
    @GetMapping()
    public String table(ModelMap mmap) {
        mmap.put("namespaces", sysHbaseNamespaceService.selectAllSysHbaseNamespaceList());
        return prefix + "/table";
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
        //TODO 保存表信息时，同名表进行判断
        SysHbaseTable sysHbaseTable = sysHbaseTableDto.convertTo();
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
        return toAjax(sysHbaseTableService.updateSysHbaseTable(sysHbaseTable));
    }

    /**
     * 删除HBase
     */
    @RequiresPermissions("system:table:remove")
    @Log(title = "HBase", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysHbaseTableService.deleteSysHbaseTableByIds(ids));
    }
}
