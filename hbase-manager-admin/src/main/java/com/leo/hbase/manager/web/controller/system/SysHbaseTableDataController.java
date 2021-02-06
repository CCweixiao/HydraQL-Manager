package com.leo.hbase.manager.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.github.CCweixiao.constant.HMHBaseConstant;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.model.FamilyDesc;
import com.github.CCweixiao.model.TableDesc;
import com.github.CCweixiao.util.StrUtil;
import com.leo.hbase.manager.common.annotation.Log;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.core.domain.CxSelect;
import com.leo.hbase.manager.common.core.page.TableDataInfo;
import com.leo.hbase.manager.common.enums.BusinessType;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.common.utils.poi.ExcelUtil;
import com.leo.hbase.manager.system.domain.SysHbaseTableData;
import com.leo.hbase.manager.system.dto.TableDescDto;
import com.leo.hbase.manager.web.service.IMultiHBaseAdminService;
import com.leo.hbase.manager.web.service.IMultiHBaseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * HBaseController
 *
 * @author leojie
 * @date 2020-09-07
 */
@Controller
@RequestMapping("/system/data")
public class SysHbaseTableDataController extends SysHbaseBaseController {
    private String prefix = "system/data";

    @Autowired
    private IMultiHBaseService multiHBaseService;

    @Autowired
    private IMultiHBaseAdminService multiHBaseAdminService;

    @RequiresPermissions("system:data:view")
    @GetMapping()
    public String data(ModelMap mmap) {
        List<CxSelect> cxTableInfoList = getTableFamilyRelations();
        mmap.put("tableFamilyData", JSON.toJSON(cxTableInfoList));

        return prefix + "/data";
    }

    /**
     * 查询HBase列表数据
     */
    @RequiresPermissions("system:data:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysHbaseTableData sysHbaseTableData) {
        String clusterCode = clusterCodeOfCurrentSession();
        List<SysHbaseTableData> list = new ArrayList<>();


        if (StrUtil.isBlank(sysHbaseTableData.getTableName())) {
            return getDataTable(list);
        }

        if (multiHBaseAdminService.isTableDisabled(clusterCode, sysHbaseTableData.getTableName())) {
            throw new HBaseOperationsException("表[" + sysHbaseTableData.getTableName() + "]处于禁用状态，无法被查询！");
        }

        if (StrUtil.isNotBlank(sysHbaseTableData.getRowKey())) {
            final List<Map<String, Object>> dataMapList = multiHBaseService.get(clusterCode, sysHbaseTableData.getTableName(), sysHbaseTableData.getRowKey(), sysHbaseTableData.getFamilyName());
            if (dataMapList.isEmpty()) {
                return getDataTable(list);
            }
            for (Map<String, Object> dataMap : dataMapList) {
                SysHbaseTableData hbaseTableData = mapToHBaseTableData(sysHbaseTableData.getTableName(), dataMap);
                list.add(hbaseTableData);
            }

            return getDataTable(list);
        }
        List<List<Map<String, Object>>> dataMaps = multiHBaseService.find(clusterCode, sysHbaseTableData.getTableName(), sysHbaseTableData.getFamilyName(), sysHbaseTableData.getStartKey(), sysHbaseTableData.getLimit());
        if (dataMaps == null || dataMaps.isEmpty()) {
            return getDataTable(list);
        }

        list = dataMaps.stream().flatMap(Collection::stream).map(dd -> mapToHBaseTableData(sysHbaseTableData.getTableName(), dd)).collect(Collectors.toList());

        return getDataTable(list);
    }

    @RequiresPermissions("system:data:detail")
    @GetMapping("/detail")
    public String detail(@RequestParam String tableName,
                         @RequestParam String familyName,
                         @RequestParam String rowKey,
                         ModelMap mmap) {
        String[] familyAndQualifierName = parseFamilyAndQualifierName(familyName);

        SysHbaseTableData sysHbaseTableData = new SysHbaseTableData();
        if (familyAndQualifierName != null && StringUtils.isNotBlank(rowKey)) {
            Map<String, Object> data = multiHBaseService.get(clusterCodeOfCurrentSession(), tableName, rowKey, familyAndQualifierName[0], familyAndQualifierName[1]);
            sysHbaseTableData = mapToHBaseTableData(tableName, data);
        }
        mmap.put("sysHbaseTableData", sysHbaseTableData);
        return prefix + "/detail";
    }

    /**
     * 导出HBase列表数据
     */
    @RequiresPermissions("system:data:export")
    @Log(title = "HBase数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysHbaseTableData sysHbaseTableData) {
        return error("暂不支持数据导出");
        /*List<SysHbaseTableData> list = new ArrayList<>();
        ExcelUtil<SysHbaseTableData> util = new ExcelUtil<>(SysHbaseTableData.class);
        return util.exportExcel(list, "data");*/
    }

    /**
     * 新增HBase数据
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        List<CxSelect> cxTableInfoList = getTableFamilyRelations();
        mmap.put("tableFamilyData", JSON.toJSON(cxTableInfoList));
        return prefix + "/add";
    }

    /**
     * 新增保存HBase数据
     */
    @RequiresPermissions("system:data:add")
    @Log(title = "HBase数据", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysHbaseTableData sysHbaseTableData) {
        String clusterCode = clusterCodeOfCurrentSession();
        String tableName = sysHbaseTableData.getTableName();
        if (multiHBaseAdminService.isTableDisabled(clusterCode, tableName)) {
            throw new HBaseOperationsException("表[" + tableName + "]处于禁用状态！");
        }
        String familyName = sysHbaseTableData.getFamilyName() + HMHBaseConstant.TABLE_NAME_SPLIT_CHAR + sysHbaseTableData.getFieldName();
        multiHBaseService.saveOrUpdate(clusterCode, tableName, sysHbaseTableData.getRowKey(), familyName, sysHbaseTableData.getValue());
        return success("数据新增成功！");
    }

    /**
     * 修改HBase数据
     */
    @GetMapping("/edit")
    public String edit(@RequestParam String tableName,
                       @RequestParam String familyName,
                       @RequestParam String rowKey, ModelMap mmap) {
        String[] familyAndQualifierName = parseFamilyAndQualifierName(familyName);
        SysHbaseTableData sysHbaseTableData = new SysHbaseTableData();
        if (familyAndQualifierName != null && StringUtils.isNotBlank(rowKey)) {
            Map<String, Object> data = multiHBaseService.get(clusterCodeOfCurrentSession(), tableName, rowKey, familyAndQualifierName[0], familyAndQualifierName[1]);
            sysHbaseTableData = mapToHBaseTableData(tableName, data);
        }
        mmap.put("sysHbaseTableData", sysHbaseTableData);
        return prefix + "/edit";
    }

    /**
     * 修改保存HBase数据
     */
    @RequiresPermissions("system:data:edit")
    @Log(title = "HBase数据", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysHbaseTableData sysHbaseTableData) {
        String clusterCode = clusterCodeOfCurrentSession();
        String tableName = sysHbaseTableData.getTableName();
        if (multiHBaseAdminService.isTableDisabled(clusterCode, tableName)) {
            throw new HBaseOperationsException("表[" + tableName + "]处于禁用状态！");
        }
        multiHBaseService.saveOrUpdate(clusterCode, tableName, sysHbaseTableData.getRowKey(), sysHbaseTableData.getFamilyName(), sysHbaseTableData.getValue());
        return success("数据修改成功");
    }

    /**
     * 删除HBase数据
     */
    @RequiresPermissions("system:data:remove")
    @Log(title = "HBase数据", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@RequestParam String tableName, @RequestParam String familyName, @RequestParam String rowKey) {
        String[] familyAndQualifierName = parseFamilyAndQualifierName(familyName);
        multiHBaseService.delete(clusterCodeOfCurrentSession(), tableName, rowKey, familyAndQualifierName[0], familyAndQualifierName[1]);
        return success("数据删除成功！");
    }

    private SysHbaseTableData mapToHBaseTableData(String tableName, Map<String, Object> dataMap) {
        SysHbaseTableData hbaseTableData = new SysHbaseTableData();
        hbaseTableData.setTableName(HMHBaseConstant.getFullTableName(tableName));
        hbaseTableData.setRowKey(dataMap.get("rowKey").toString());
        hbaseTableData.setFamilyName(dataMap.get("familyName").toString());
        hbaseTableData.setTimestamp(dataMap.get("timestamp").toString());
        hbaseTableData.setValue(dataMap.get("value").toString());
        return hbaseTableData;
    }

    private List<CxSelect> getTableFamilyRelations() {
        String clusterCode = clusterCodeOfCurrentSession();

        final List<String> allTableNames = multiHBaseAdminService.listAllTableName(clusterCode, true);
        List<CxSelect> cxTableInfoList = new ArrayList<>(allTableNames.size());

        for (String tableName : allTableNames) {
            if (multiHBaseAdminService.isTableDisabled(clusterCode, tableName)) {
                continue;
            }
            TableDesc tableDesc = multiHBaseAdminService.getTableDesc(clusterCode, tableName);

            CxSelect cxSelectTable = new CxSelect();
            cxSelectTable.setN(tableName);
            cxSelectTable.setV(tableName);

            List<String> families = tableDesc.getFamilyDescList().stream().map(FamilyDesc::getFamilyName).collect(Collectors.toList());
            List<CxSelect> tempFamilyList = new ArrayList<>();
            for (String family : families) {
                CxSelect cxSelectFamily = new CxSelect();
                cxSelectFamily.setN(family);
                cxSelectFamily.setV(family);
                tempFamilyList.add(cxSelectFamily);
            }
            cxSelectTable.setS(tempFamilyList);
            cxTableInfoList.add(cxSelectTable);
        }
        return cxTableInfoList;
    }
}
