package com.leo.hbase.manager.web.controller.system;

import com.leo.hbase.manager.adaptor.model.FamilyDesc;
import com.leo.hbase.manager.adaptor.model.TableDesc;
import com.leo.hbase.manager.adaptor.service.IHBaseAdminService;
import com.leo.hbase.manager.common.annotation.Log;
import com.leo.hbase.manager.common.core.controller.BaseController;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.core.page.TableDataInfo;
import com.leo.hbase.manager.common.enums.BusinessType;
import com.leo.hbase.manager.system.domain.SysHbaseFamily;
import com.leo.hbase.manager.system.domain.SysHbaseTable;
import com.leo.hbase.manager.system.dto.FamilyDescDto;
import com.leo.hbase.manager.system.service.ISysHbaseTableService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    private ISysHbaseTableService sysHbaseTableService;
    @Autowired
    private IHBaseAdminService ihBaseAdminService;

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
    public TableDataInfo list(SysHbaseTable sysHbaseTable) {
        SysHbaseTable existsTable = sysHbaseTableService.selectSysHbaseTableById(sysHbaseTable.getTableId());

        final List<FamilyDesc> familyDescList = ihBaseAdminService.getFamilyDesc(existsTable.getTableName());
        final List<FamilyDescDto> familyDescDtoList = familyDescList.stream().map(familyDesc -> {
            FamilyDescDto familyDescDto =new FamilyDescDto().convertFor(familyDesc);
            familyDescDto.setTableName(existsTable.getTableName());
            return familyDescDto;
        }).collect(Collectors.toList());
        return getDataTable(familyDescDtoList);
    }

    /**
     * 导出HBase Family列表
     */
    @RequiresPermissions("system:family:export")
    @Log(title = "HBase Family", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysHbaseFamily sysHbaseFamily) {
        /*List<SysHbaseFamily> list = sysHbaseFamilyService.selectSysHbaseFamilyList(sysHbaseFamily);
        ExcelUtil<SysHbaseFamily> util = new ExcelUtil<SysHbaseFamily>(SysHbaseFamily.class);
        return util.exportExcel(list, "family");*/
        return error("暂不支持列簇的导出");
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
        return error("暂不支持列簇的保存");
        // return toAjax(sysHbaseFamilyService.insertSysHbaseFamily(sysHbaseFamily));
    }

    /**
     * 修改HBase Family
     */
    @GetMapping("/edit/{familyId}")
    public String edit(@PathVariable("familyId") String familyId, ModelMap mmap) {
        /*SysHbaseFamily sysHbaseFamily = sysHbaseFamilyService.selectSysHbaseFamilyById(familyId);
        mmap.put("sysHbaseFamily", sysHbaseFamily);*/
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
        return error("暂不支持列簇的保存");
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
