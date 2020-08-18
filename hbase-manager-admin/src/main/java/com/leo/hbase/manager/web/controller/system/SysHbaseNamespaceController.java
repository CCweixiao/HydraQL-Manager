package com.leo.hbase.manager.web.controller.system;

import java.util.List;

import com.leo.hbase.manager.system.domain.SysHbaseTable;
import com.leo.hbase.manager.system.service.ISysHbaseTableService;
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
import com.leo.hbase.manager.system.domain.SysHbaseNamespace;
import com.leo.hbase.manager.system.service.ISysHbaseNamespaceService;
import com.leo.hbase.manager.common.core.controller.BaseController;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.utils.poi.ExcelUtil;
import com.leo.hbase.manager.common.core.page.TableDataInfo;

/**
 * HBaseNamespaceController
 *
 * @author leojie
 * @date 2020-08-16
 */
@Controller
@RequestMapping("/system/namespace")
public class SysHbaseNamespaceController extends BaseController {
    private String prefix = "system/namespace";

    @Autowired
    private ISysHbaseNamespaceService sysHbaseNamespaceService;

    @Autowired
    private ISysHbaseTableService sysHbaseTableService;

    @RequiresPermissions("system:namespace:view")
    @GetMapping()
    public String namespace() {
        return prefix + "/namespace";
    }

    /**
     * 查询HBaseNamespace列表
     */
    @RequiresPermissions("system:namespace:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysHbaseNamespace sysHbaseNamespace) {
        startPage();
        List<SysHbaseNamespace> list = sysHbaseNamespaceService.selectSysHbaseNamespaceList(sysHbaseNamespace);
        return getDataTable(list);
    }

    /**
     * 导出HBaseNamespace列表
     */
    @RequiresPermissions("system:namespace:export")
    @Log(title = "HBaseNamespace", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysHbaseNamespace sysHbaseNamespace) {
        List<SysHbaseNamespace> list = sysHbaseNamespaceService.selectSysHbaseNamespaceList(sysHbaseNamespace);
        ExcelUtil<SysHbaseNamespace> util = new ExcelUtil<SysHbaseNamespace>(SysHbaseNamespace.class);
        return util.exportExcel(list, "namespace");
    }

    /**
     * 新增HBaseNamespace
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存HBaseNamespace
     */
    @RequiresPermissions("system:namespace:add")
    @Log(title = "HBaseNamespace", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysHbaseNamespace sysHbaseNamespace) {
        final String name = sysHbaseNamespace.getNamespaceName();
        SysHbaseNamespace namespace = sysHbaseNamespaceService.selectSysHbaseNamespaceByName(name);
        String msg = "namespace[" + name + "]已经存在!";

        if (namespace != null && namespace.getNamespaceId() > 0) {
            return error(msg);
        }

        return toAjax(sysHbaseNamespaceService.insertSysHbaseNamespace(sysHbaseNamespace));
    }

    /**
     * 修改HBaseNamespace
     */
    @GetMapping("/edit/{namespaceId}")
    public String edit(@PathVariable("namespaceId") Long namespaceId, ModelMap mmap) {
        SysHbaseNamespace sysHbaseNamespace = sysHbaseNamespaceService.selectSysHbaseNamespaceById(namespaceId);
        mmap.put("sysHbaseNamespace", sysHbaseNamespace);
        return prefix + "/edit";
    }

    /**
     * 修改保存HBaseNamespace
     */
    @RequiresPermissions("system:namespace:edit")
    @Log(title = "HBaseNamespace", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysHbaseNamespace sysHbaseNamespace) {
        if (sysHbaseNamespace.getNamespaceId() == null || sysHbaseNamespace.getNamespaceId() < 1) {
            return error("待修改namespace的id不能为空");
        }
        final String editNamespaceName = sysHbaseNamespace.getNamespaceName();
        SysHbaseNamespace editNamespace = sysHbaseNamespaceService.selectSysHbaseNamespaceByName(editNamespaceName);

        String msg = "namespace[" + editNamespaceName + "]已经存在!";

        if (editNamespace != null && editNamespace.getNamespaceId().longValue() != sysHbaseNamespace.getNamespaceId().longValue()) {
            return error(msg);
        }

        return toAjax(sysHbaseNamespaceService.updateSysHbaseNamespace(sysHbaseNamespace));
    }

    /**
     * 删除HBaseNamespace
     */
    @RequiresPermissions("system:namespace:remove")
    @Log(title = "HBaseNamespace", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(Long ids) {
        //判断namespace id是否有使用
        List<SysHbaseTable> tableList = sysHbaseTableService.selectSysHbaseTableListByNamespaceId(ids);
        if (tableList != null && !tableList.isEmpty()) {
            return error("该namespace中存在表，不能被删除!");
        }
        return toAjax(sysHbaseNamespaceService.deleteSysHbaseNamespaceById(ids));
    }
}
