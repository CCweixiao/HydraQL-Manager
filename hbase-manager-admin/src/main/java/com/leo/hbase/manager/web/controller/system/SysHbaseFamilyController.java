package com.leo.hbase.manager.web.controller.system;

import java.util.List;

import com.leo.hbase.manager.adaptor.service.IHBaseAdminService;
import com.leo.hbase.manager.common.enums.HBaseReplicationScopeFlag;
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
import com.leo.hbase.manager.system.domain.SysHbaseFamily;
import com.leo.hbase.manager.system.service.ISysHbaseFamilyService;
import com.leo.hbase.manager.common.core.controller.BaseController;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.utils.poi.ExcelUtil;
import com.leo.hbase.manager.common.core.page.TableDataInfo;

/**
 * HBase FamilyController
 *
 * @author leojie
 * @date 2020-08-22
 */
@Controller
@RequestMapping("/system/family")
public class SysHbaseFamilyController extends BaseController {
    private String prefix = "system/family";

    @Autowired
    private ISysHbaseFamilyService sysHbaseFamilyService;
    @Autowired
    private IHBaseAdminService adminService;

    @RequiresPermissions("system:family:view")
    @GetMapping()
    public String family() {
        return prefix + "/family";
    }

    /**
     * 查询HBase Family列表
     */
    @RequiresPermissions("system:family:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysHbaseFamily sysHbaseFamily) {
        startPage();
        List<SysHbaseFamily> list = sysHbaseFamilyService.selectSysHbaseFamilyList(sysHbaseFamily);
        return getDataTable(list);
    }

    /**
     * 导出HBase Family列表
     */
    @RequiresPermissions("system:family:export")
    @Log(title = "HBase Family", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysHbaseFamily sysHbaseFamily) {
        List<SysHbaseFamily> list = sysHbaseFamilyService.selectSysHbaseFamilyList(sysHbaseFamily);
        ExcelUtil<SysHbaseFamily> util = new ExcelUtil<SysHbaseFamily>(SysHbaseFamily.class);
        return util.exportExcel(list, "family");
    }

    /**
     * 新增HBase Family
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存HBase Family
     */
    @RequiresPermissions("system:family:add")
    @Log(title = "HBase Family", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysHbaseFamily sysHbaseFamily) {
        return toAjax(sysHbaseFamilyService.insertSysHbaseFamily(sysHbaseFamily));
    }

    /**
     * 修改HBase Family
     */
    @GetMapping("/edit/{familyId}")
    public String edit(@PathVariable("familyId") Long familyId, ModelMap mmap) {
        SysHbaseFamily sysHbaseFamily = sysHbaseFamilyService.selectSysHbaseFamilyById(familyId);
        mmap.put("sysHbaseFamily", sysHbaseFamily);
        return prefix + "/edit";
    }

    /**
     * 修改保存HBase Family
     */
    @RequiresPermissions("system:family:edit")
    @Log(title = "HBase Family", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysHbaseFamily sysHbaseFamily) {
        SysHbaseFamily family = sysHbaseFamilyService.selectSysHbaseFamilyById(sysHbaseFamily.getFamilyId());
        if (family == null || family.getFamilyId() < 1) {
            return error("待修改列簇不存在");
        }
        //TODO 暂时支持对replication scope的修改
        if (HBaseReplicationScopeFlag.OPEN.getCode().equals(sysHbaseFamily.getReplicationScope())) {
            adminService.enableReplication(family.getTableName(), family.getFamilyName());
        } else {
            adminService.disableReplication(family.getTableName(), family.getFamilyName());
        }
        return toAjax(sysHbaseFamilyService.updateSysHbaseFamilyReplicationScope(sysHbaseFamily));
    }

    /**
     * 删除HBase Family
     */
    @RequiresPermissions("system:family:remove")
    @Log(title = "HBase Family", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return error("暂不支持列簇删除！");
        //return toAjax(sysHbaseFamilyService.deleteSysHbaseFamilyByIds(ids));
    }
}
