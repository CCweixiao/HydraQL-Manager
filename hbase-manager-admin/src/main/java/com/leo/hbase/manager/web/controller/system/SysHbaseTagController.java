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
import com.leo.hbase.manager.system.domain.SysHbaseTag;
import com.leo.hbase.manager.system.service.ISysHbaseTagService;
import com.leo.hbase.manager.common.core.controller.BaseController;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.utils.poi.ExcelUtil;
import com.leo.hbase.manager.common.core.page.TableDataInfo;

/**
 * HBaseTagController
 * 
 * @author leojie
 * @date 2020-08-16
 */
@Controller
@RequestMapping("/system/tag")
public class SysHbaseTagController extends BaseController
{
    private String prefix = "system/tag";

    @Autowired
    private ISysHbaseTagService sysHbaseTagService;

    @RequiresPermissions("system:tag:view")
    @GetMapping()
    public String tag()
    {
        return prefix + "/tag";
    }

    /**
     * 查询HBaseTag列表
     */
    @RequiresPermissions("system:tag:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysHbaseTag sysHbaseTag)
    {
        startPage();
        List<SysHbaseTag> list = sysHbaseTagService.selectSysHbaseTagList(sysHbaseTag);
        return getDataTable(list);
    }

    /**
     * 导出HBaseTag列表
     */
    @RequiresPermissions("system:tag:export")
    @Log(title = "HBaseTag", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysHbaseTag sysHbaseTag)
    {
        List<SysHbaseTag> list = sysHbaseTagService.selectSysHbaseTagList(sysHbaseTag);
        ExcelUtil<SysHbaseTag> util = new ExcelUtil<SysHbaseTag>(SysHbaseTag.class);
        return util.exportExcel(list, "tag");
    }

    /**
     * 新增HBaseTag
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存HBaseTag
     */
    @RequiresPermissions("system:tag:add")
    @Log(title = "HBaseTag", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysHbaseTag sysHbaseTag)
    {
        return toAjax(sysHbaseTagService.insertSysHbaseTag(sysHbaseTag));
    }

    /**
     * 修改HBaseTag
     */
    @GetMapping("/edit/{tagId}")
    public String edit(@PathVariable("tagId") Long tagId, ModelMap mmap)
    {
        SysHbaseTag sysHbaseTag = sysHbaseTagService.selectSysHbaseTagById(tagId);
        mmap.put("sysHbaseTag", sysHbaseTag);
        return prefix + "/edit";
    }

    /**
     * 修改保存HBaseTag
     */
    @RequiresPermissions("system:tag:edit")
    @Log(title = "HBaseTag", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysHbaseTag sysHbaseTag)
    {
        return toAjax(sysHbaseTagService.updateSysHbaseTag(sysHbaseTag));
    }

    /**
     * 删除HBaseTag
     */
    @RequiresPermissions("system:tag:remove")
    @Log(title = "HBaseTag", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysHbaseTagService.deleteSysHbaseTagByIds(ids));
    }
}
