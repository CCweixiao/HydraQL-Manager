package com.leo.hbase.manager.web.controller.system;

import com.github.CCweixiao.util.StrUtil;
import com.leo.hbase.manager.adaptor.HMHBaseConstant;
import com.leo.hbase.manager.adaptor.model.NamespaceDesc;
import com.leo.hbase.manager.adaptor.service.IHBaseAdminService;
import com.leo.hbase.manager.common.annotation.Log;
import com.leo.hbase.manager.common.core.controller.BaseController;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.core.page.TableDataInfo;
import com.leo.hbase.manager.common.enums.BusinessType;
import com.leo.hbase.manager.common.utils.poi.ExcelUtil;
import com.leo.hbase.manager.system.dto.NamespaceDescDto;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    private IHBaseAdminService hBaseAdminService;

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
    public TableDataInfo list(NamespaceDescDto namespaceDescDto) {
        List<NamespaceDescDto> list = getAllNamespaces();

        if (StrUtil.isNotBlank(namespaceDescDto.getNamespaceName())) {
            list = list.stream().filter(ns -> ns.getNamespaceName().toLowerCase()
                    .contains(namespaceDescDto.getNamespaceName().toLowerCase())).collect(Collectors.toList());
        }
        return getDataTable(list);
    }

    /**
     * 导出HBaseNamespace列表
     */
    @RequiresPermissions("system:namespace:export")
    @Log(title = "HBaseNamespace", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NamespaceDescDto namespaceDescDto) {
        List<NamespaceDescDto> list = getAllNamespaces();
        if (StrUtil.isNotBlank(namespaceDescDto.getNamespaceName())) {
            list = list.stream().filter(ns -> ns.getNamespaceName().toLowerCase()
                    .contains(namespaceDescDto.getNamespaceName().toLowerCase())).collect(Collectors.toList());
        }
        ExcelUtil<NamespaceDescDto> util = new ExcelUtil<>(NamespaceDescDto.class);
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
    public AjaxResult addSave(@Validated NamespaceDescDto namespaceDescDto) {
        final String name = namespaceDescDto.getNamespaceName();
        if (HMHBaseConstant.DEFAULT_SYS_TABLE_NAMESPACE.equals(name.toLowerCase())) {
            return error("命名空间[" + name + "]不允许被创建！");
        }
        final List<String> listAllNamespaceName = hBaseAdminService.listAllNamespaceName();
        if (listAllNamespaceName.contains(namespaceDescDto.getNamespaceName())) {
            return error("namespace[" + name + "]已经存在！");
        }
        NamespaceDesc namespaceDesc = namespaceDescDto.convertTo();
        final boolean createdOrNot = hBaseAdminService.createNamespace(namespaceDesc);

        if (!createdOrNot) {
            return error("namespace[" + name + "]创建失败！");
        }

        return success("namespace[" + name + "]创建成功！");
    }

    /**
     * 修改HBaseNamespace
     */
    @GetMapping("/edit/{namespaceId}")
    public String edit(@PathVariable("namespaceId") String namespaceId, ModelMap mmap) {
        NamespaceDesc namespaceDesc = hBaseAdminService.getNamespaceDesc(namespaceId);
        mmap.put("namespaceDesc", namespaceDesc);
        return prefix + "/edit";
    }

    /**
     * 修改保存HBaseNamespace
     */
    @RequiresPermissions("system:namespace:edit")
    @Log(title = "HBaseNamespace", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated NamespaceDescDto namespaceDescDto) {
        return error("暂不支持对namespace的重命名");
    }

    /**
     * 删除HBaseNamespace
     */
    @RequiresPermissions("system:namespace:remove")
    @Log(title = "HBaseNamespace", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        List<String> tableNames = hBaseAdminService.listAllTableNamesByNamespaceName(ids);
        if (tableNames != null && !tableNames.isEmpty()) {
            return error("namespace[" + ids + "]包含表，删除失败！");
        }

        final boolean deletedOrNot = hBaseAdminService.deleteNamespace(ids);
        if (!deletedOrNot) {
            return error("namespace[" + ids + "]删除失败！");
        }
        return success("namespace[" + ids + "]删除成功！");
    }

    private List<NamespaceDescDto> getAllNamespaces() {
        return hBaseAdminService.listAllNamespaceDesc()
                .stream().map(namespaceDesc -> new NamespaceDescDto().convertFor(namespaceDesc))
                .collect(Collectors.toList());
    }
}
