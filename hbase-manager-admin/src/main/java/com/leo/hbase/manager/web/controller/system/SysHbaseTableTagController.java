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
import com.leo.hbase.manager.system.domain.SysHbaseTableTag;
import com.leo.hbase.manager.system.service.ISysHbaseTableTagService;
import com.leo.hbase.manager.common.core.controller.BaseController;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.utils.poi.ExcelUtil;
import com.leo.hbase.manager.common.core.page.TableDataInfo;

/**
 * HBase所属TagController
 * 
 * @author leojie
 * @date 2020-08-16
 */
@Controller
@RequestMapping("/system/table_tag")
public class SysHbaseTableTagController extends BaseController
{
    private String prefix = "system/table_tag";

    @Autowired
    private ISysHbaseTableTagService sysHbaseTableTagService;

    @RequiresPermissions("system:tag:view")
    @GetMapping()
    public String tag()
    {
        return prefix + "/tag";
    }

    /**
     * 查询HBase所属Tag列表
     */
    @RequiresPermissions("system:tag:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysHbaseTableTag sysHbaseTableTag)
    {
        startPage();
        List<SysHbaseTableTag> list = sysHbaseTableTagService.selectSysHbaseTableTagList(sysHbaseTableTag);
        return getDataTable(list);
    }

    /**
     * 导出HBase所属Tag列表
     */
    @RequiresPermissions("system:tag:export")
    @Log(title = "HBase所属Tag", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysHbaseTableTag sysHbaseTableTag)
    {
        List<SysHbaseTableTag> list = sysHbaseTableTagService.selectSysHbaseTableTagList(sysHbaseTableTag);
        ExcelUtil<SysHbaseTableTag> util = new ExcelUtil<SysHbaseTableTag>(SysHbaseTableTag.class);
        return util.exportExcel(list, "tag");
    }

    /**
     * 新增HBase所属Tag
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存HBase所属Tag
     */
    @RequiresPermissions("system:tag:add")
    @Log(title = "HBase所属Tag", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysHbaseTableTag sysHbaseTableTag)
    {
        return toAjax(sysHbaseTableTagService.insertSysHbaseTableTag(sysHbaseTableTag));
    }

    /**
     * 修改HBase所属Tag
     */
    @GetMapping("/edit/{tableId}")
    public String edit(@PathVariable("tableId") Long tableId, ModelMap mmap)
    {
        SysHbaseTableTag sysHbaseTableTag = sysHbaseTableTagService.selectSysHbaseTableTagById(tableId);
        mmap.put("sysHbaseTableTag", sysHbaseTableTag);
        return prefix + "/edit";
    }

    /**
     * 修改保存HBase所属Tag
     */
    @RequiresPermissions("system:tag:edit")
    @Log(title = "HBase所属Tag", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysHbaseTableTag sysHbaseTableTag)
    {
        return toAjax(sysHbaseTableTagService.updateSysHbaseTableTag(sysHbaseTableTag));
    }

    /**
     * 删除HBase所属Tag
     */
    @RequiresPermissions("system:tag:remove")
    @Log(title = "HBase所属Tag", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysHbaseTableTagService.deleteSysHbaseTableTagByIds(ids));
    }
}
