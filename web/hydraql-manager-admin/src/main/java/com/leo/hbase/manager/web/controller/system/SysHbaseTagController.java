package com.leo.hbase.manager.web.controller.system;

import com.leo.hbase.manager.common.annotation.Log;
import com.leo.hbase.manager.common.core.controller.BaseController;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.core.page.TableDataInfo;
import com.leo.hbase.manager.common.enums.BusinessType;
import com.leo.hbase.manager.common.utils.poi.ExcelUtil;
import com.leo.hbase.manager.system.domain.SysHbaseTag;
import com.leo.hbase.manager.system.service.ISysHbaseTagService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HBaseTagController
 *
 * @author leojie
 * @date 2020-08-16
 */
@Controller
@RequestMapping("/system/tag")
public class SysHbaseTagController extends BaseController {
    private String prefix = "system/tag";

    @Autowired
    private ISysHbaseTagService sysHbaseTagService;

    @RequiresPermissions("hbase:tag:view")
    @GetMapping()
    public String tag() {
        return prefix + "/tag";
    }

    /**
     * 查询HBaseTag列表
     */
    @RequiresPermissions("hbase:tag:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysHbaseTag sysHbaseTag) {
        startPage();
        List<SysHbaseTag> list = sysHbaseTagService.selectSysHbaseTagList(sysHbaseTag);
        return getDataTable(list);
    }

    /**
     * 导出HBaseTag列表
     */
    @RequiresPermissions("hbase:tag:export")
    @Log(title = "HBase标签导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysHbaseTag sysHbaseTag) {
        List<SysHbaseTag> list = sysHbaseTagService.selectSysHbaseTagList(sysHbaseTag);
        ExcelUtil<SysHbaseTag> util = new ExcelUtil<SysHbaseTag>(SysHbaseTag.class);
        return util.exportExcel(list, "tag");
    }

    /**
     * 新增HBaseTag
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存HBaseTag
     */
    @RequiresPermissions("hbase:tag:add")
    @Log(title = "HBase标签新增", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysHbaseTag sysHbaseTag) {
        final String tagName = sysHbaseTag.getTagName();

        SysHbaseTag existsTag = sysHbaseTagService.selectSysHbaseTagByName(tagName);
        String msg = "tag [" + tagName + "]已经存在!";

        if (existsTag != null && existsTag.getTagId() > 0) {
            return error(msg);
        }
        return toAjax(sysHbaseTagService.insertSysHbaseTag(sysHbaseTag));
    }

    /**
     * 修改HBaseTag
     */
    @GetMapping("/edit/{tagId}")
    public String edit(@PathVariable("tagId") Long tagId, ModelMap mmap) {
        SysHbaseTag sysHbaseTag = sysHbaseTagService.selectSysHbaseTagById(tagId);
        mmap.put("sysHbaseTag", sysHbaseTag);
        return prefix + "/edit";
    }

    /**
     * 修改保存HBaseTag
     */
    @RequiresPermissions("hbase:tag:edit")
    @Log(title = "HBase标签更新", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysHbaseTag sysHbaseTag) {
        if (sysHbaseTag.getTagId() == null || sysHbaseTag.getTagId() < 1) {
            return error("待修改HBase表标签id不能为空!");
        }
        final String tagName = sysHbaseTag.getTagName();

        SysHbaseTag existsTag = sysHbaseTagService.selectSysHbaseTagByName(tagName);

        String msg = "tag [" + tagName + "]已经存在!";

        if (existsTag != null && existsTag.getTagId() > 0) {
            if (!existsTag.getTagId().equals(sysHbaseTag.getTagId())) {
                return error(msg);
            }
        }
        return toAjax(sysHbaseTagService.updateSysHbaseTag(sysHbaseTag));
    }

    /**
     * 删除HBaseTag
     */
    @RequiresPermissions("hbase:tag:remove")
    @Log(title = "HBase标签删除", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(Long ids) {
        return toAjax(sysHbaseTagService.deleteSysHbaseTagById(ids));
    }
}
