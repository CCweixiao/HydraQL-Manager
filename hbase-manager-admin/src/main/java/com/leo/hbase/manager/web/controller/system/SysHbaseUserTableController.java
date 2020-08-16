package com.leo.hbase.manager.web.controller.system;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.leo.hbase.manager.common.annotation.Log;
import com.leo.hbase.manager.common.enums.BusinessType;
import com.leo.hbase.manager.system.domain.SysHbaseUserTable;
import com.leo.hbase.manager.system.service.ISysHbaseUserTableService;
import com.leo.hbase.manager.common.core.controller.BaseController;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.utils.poi.ExcelUtil;
import com.leo.hbase.manager.common.core.page.TableDataInfo;

/**
 * HBase所属用户Controller
 * 
 * @author leojie
 * @date 2020-08-16
 */
@Controller
@RequestMapping("/system/user_table")
public class SysHbaseUserTableController extends BaseController
{
    private String prefix = "system/user_table";

    @Autowired
    private ISysHbaseUserTableService sysHbaseUserTableService;

    @RequiresPermissions("system:table:view")
    @GetMapping()
    public String table()
    {
        return prefix + "/table";
    }

    /**
     * 查询HBase所属用户列表
     */
    @RequiresPermissions("system:table:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysHbaseUserTable sysHbaseUserTable)
    {
        startPage();
        List<SysHbaseUserTable> list = sysHbaseUserTableService.selectSysHbaseUserTableList(sysHbaseUserTable);
        return getDataTable(list);
    }

    /**
     * 导出HBase所属用户列表
     */
    @RequiresPermissions("system:table:export")
    @Log(title = "HBase所属用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysHbaseUserTable sysHbaseUserTable)
    {
        List<SysHbaseUserTable> list = sysHbaseUserTableService.selectSysHbaseUserTableList(sysHbaseUserTable);
        ExcelUtil<SysHbaseUserTable> util = new ExcelUtil<SysHbaseUserTable>(SysHbaseUserTable.class);
        return util.exportExcel(list, "table");
    }

    /**
     * 新增HBase所属用户
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存HBase所属用户
     */
    @RequiresPermissions("system:table:add")
    @Log(title = "HBase所属用户", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysHbaseUserTable sysHbaseUserTable)
    {
        return toAjax(sysHbaseUserTableService.insertSysHbaseUserTable(sysHbaseUserTable));
    }

    /**
     * 修改HBase所属用户
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        SysHbaseUserTable sysHbaseUserTable = sysHbaseUserTableService.selectSysHbaseUserTableById(userId);
        mmap.put("sysHbaseUserTable", sysHbaseUserTable);
        return prefix + "/edit";
    }

    /**
     * 修改保存HBase所属用户
     */
    @RequiresPermissions("system:table:edit")
    @Log(title = "HBase所属用户", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysHbaseUserTable sysHbaseUserTable)
    {
        return toAjax(sysHbaseUserTableService.updateSysHbaseUserTable(sysHbaseUserTable));
    }

    /**
     * 删除HBase所属用户
     */
    @RequiresPermissions("system:table:remove")
    @Log(title = "HBase所属用户", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysHbaseUserTableService.deleteSysHbaseUserTableByIds(ids));
    }
}
