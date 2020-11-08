package com.leo.hbase.manager.web.controller.system;

import com.github.CCweixiao.model.FamilyDesc;
import com.leo.hbase.manager.common.annotation.Log;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.core.page.TableDataInfo;
import com.leo.hbase.manager.common.enums.BusinessType;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.common.utils.poi.ExcelUtil;
import com.leo.hbase.manager.system.dto.FamilyDescDto;
import com.leo.hbase.manager.web.controller.query.QueryHBaseTableForm;
import com.leo.hbase.manager.web.service.IMultiHBaseAdminService;
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
public class SysHbaseFamilyController extends SysHbaseBaseController {
    private String prefix = "system/family";

    @Autowired
    private IMultiHBaseAdminService multiHBaseAdminService;

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
    public TableDataInfo list(QueryHBaseTableForm queryHBaseTableForm) {
        final String tableName = parseTableNameFromTableId(queryHBaseTableForm.getTableId());
        final List<FamilyDesc> familyDescList = multiHBaseAdminService.getFamilyDesc(clusterCodeOfCurrentSession(), tableName);
        List<FamilyDescDto> familyDescDtoList = filterFamilyList(tableName, familyDescList);
        return getDataTable(familyDescDtoList);
    }

    /**
     * 导出HBase Family列表
     */
    @RequiresPermissions("system:family:export")
    @Log(title = "HBase Family", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(QueryHBaseTableForm queryHBaseTableForm) {
        final String tableName = parseTableNameFromTableId(queryHBaseTableForm.getTableId());
        final List<FamilyDesc> familyDescList = multiHBaseAdminService.getFamilyDesc(clusterCodeOfCurrentSession(), tableName);
        List<FamilyDescDto> familyDescDtoList = filterFamilyList(tableName, familyDescList);
        ExcelUtil<FamilyDescDto> util = new ExcelUtil<>(FamilyDescDto.class);
        return util.exportExcel(familyDescDtoList, "family");
    }

    /**
     * 新增HBase Family
     */
    @GetMapping("/add/{tableId}")
    public String add(@PathVariable String tableId, ModelMap mm) {
        String tableName = parseTableNameFromTableId(tableId);
        mm.put("tableName", tableName);
        mm.put("maxVersions", 1);
        mm.put("timeToLive", 2147483647);
        return prefix + "/add";
    }

    /**
     * 新增保存HBase Family
     */
    @RequiresPermissions("system:family:add")
    @Log(title = "HBase Family", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FamilyDescDto familyDescDto) {
        FamilyDesc familyDesc = familyDescDto.convertTo();
        boolean res = multiHBaseAdminService.addFamily(clusterCodeOfCurrentSession(), familyDescDto.getTableName(), familyDesc);
        if (res) {
            return success("列簇[" + familyDesc.getFamilyName() + "]添加成功");
        }
        return error("列簇[" + familyDesc.getFamilyName() + "]添加失败");
    }

    /**
     * 修改HBase Family
     */
    @GetMapping("/edit/{familyId}")
    public String edit(@PathVariable("familyId") String familyId, ModelMap mmap) {
        String tableName = familyId.substring(0, familyId.lastIndexOf(":"));
        String familyName = familyId.substring(familyId.lastIndexOf(":") + 1);

        final List<FamilyDesc> familyDescList = multiHBaseAdminService.getFamilyDesc(clusterCodeOfCurrentSession(), tableName);
        final List<FamilyDescDto> familyDescDtoList = familyDescList.stream()
                .filter(familyDesc -> familyDesc.getFamilyName().equals(familyName))
                .map(familyDesc -> new FamilyDescDto().convertFor(familyDesc))
                .collect(Collectors.toList());
        final FamilyDescDto familyDescDto = familyDescDtoList.get(0);
        familyDescDto.setTableName(tableName);
        familyDescDto.setFamilyId(familyName);
        mmap.put("familyDescDto", familyDescDto);
        return prefix + "/edit";
    }

    /**
     * 修改保存HBase Family
     */
    @RequiresPermissions("system:family:edit")
    @Log(title = "HBase Family", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FamilyDescDto familyDescDto) {
        final FamilyDesc familyDesc = familyDescDto.convertTo();
        boolean res = multiHBaseAdminService.modifyFamily(clusterCodeOfCurrentSession(), familyDescDto.getTableName(), familyDesc);
        if (res) {
            return success("列簇[" + familyDesc.getFamilyName() + "]更新成功");
        }
        return success("列簇[" + familyDesc.getFamilyName() + "]更新失败");
    }

    /**
     * 删除HBase Family
     */
    @RequiresPermissions("system:family:remove")
    @Log(title = "HBase Family", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        if (StringUtils.isBlank(ids)) {
            return error("待删除列簇ID不能为空");
        }
        final String tableName = ids.substring(0, ids.lastIndexOf(":"));
        final String familyName = ids.substring(ids.lastIndexOf(":") + 1);
        boolean res = multiHBaseAdminService.deleteFamily(clusterCodeOfCurrentSession(), tableName, familyName);
        if (res) {
            return success("列簇[" + familyName + "]删除成功");
        }
        return success("列簇[" + familyName + "]删除失败");
    }

    private List<FamilyDescDto> filterFamilyList(String tableName, List<FamilyDesc> familyDescList) {
        return familyDescList.stream().map(familyDesc -> {
            FamilyDescDto familyDescDto = new FamilyDescDto().convertFor(familyDesc);
            familyDescDto.setTableName(tableName);
            return familyDescDto;
        }).collect(Collectors.toList());
    }
}
