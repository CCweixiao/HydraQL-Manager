package com.leo.hbase.manager.web.controller.system;

import com.alibaba.fastjson2.JSON;
import com.hydraql.manager.core.hbase.model.HBaseRowData;
import com.hydraql.manager.core.hbase.schema.ColumnFamilyDesc;
import com.hydraql.manager.core.hbase.schema.HTableDesc;
import com.hydraql.manager.core.util.HConstants;
import com.leo.hbase.manager.common.annotation.Log;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.core.domain.CxSelect;
import com.leo.hbase.manager.common.core.page.TableDataInfo;
import com.leo.hbase.manager.common.enums.BusinessType;
import com.leo.hbase.manager.common.exception.BusinessException;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.system.domain.SysHbaseTableData;
import com.leo.hbase.manager.web.service.IMultiHBaseAdminService;
//import com.leo.hbase.manager.web.service.IMultiHBaseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
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

//    @Autowired
//    private IMultiHBaseService multiHBaseService;

    @Autowired
    private IMultiHBaseAdminService multiHBaseAdminService;

    @RequiresPermissions("hbase:data:view")
    @GetMapping()
    public String data(ModelMap mmap) {
        List<CxSelect> cxTableInfoList = getTableFamilyRelations();
        mmap.put("tableFamilyData", JSON.toJSON(cxTableInfoList));

        return prefix + "/data";
    }

    /**
     * 查询HBase列表数据
     */
    @RequiresPermissions("hbase:data:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysHbaseTableData sysHbaseTableData) {
        String clusterCode = clusterCodeOfCurrentSession();
        final List<SysHbaseTableData> list = new ArrayList<>();


        if (StringUtils.isBlank(sysHbaseTableData.getTableName())) {
            return getDataTable(list);
        }

        if (multiHBaseAdminService.isTableDisabled(clusterCode, sysHbaseTableData.getTableName())) {
            throw new BusinessException("表[" + sysHbaseTableData.getTableName() + "]处于禁用状态，无法被查询！");
        }

        /*if (StringUtils.isNotBlank(sysHbaseTableData.getRowKey())) {
            final HBaseRowData rowData = multiHBaseService.get(clusterCode, sysHbaseTableData.getTableName(),
                    sysHbaseTableData.getRowKey(), sysHbaseTableData.getFamilyName());

            if (rowData.getData() == null || rowData.getData() .isEmpty()) {
                return getDataTable(list);
            }
            List<SysHbaseTableData> hbaseTableDataList = mapToHBaseTableDataList(sysHbaseTableData.getTableName(), rowData);
            list.addAll(hbaseTableDataList);

            return getDataTable(list);
        }
        List<HBaseRowData> rowDataList = multiHBaseService.find(clusterCode, sysHbaseTableData.getTableName(), sysHbaseTableData.getFamilyName(), sysHbaseTableData.getStartKey(), sysHbaseTableData.getLimit());
        if (rowDataList == null || rowDataList.isEmpty()) {
            return getDataTable(list);
        }

        rowDataList.stream().map(rowData ->
                mapToHBaseTableDataList(sysHbaseTableData.getTableName(), rowData)).forEach(list::addAll);

        return getDataTable(list);*/
        return getDataTable(list);
    }

    @RequiresPermissions("hbase:data:detail")
    @GetMapping("/detail")
    public String detail(@RequestParam String tableName,
                         @RequestParam String familyName,
                         @RequestParam String rowKey,
                         ModelMap mmap) {
        String[] familyAndQualifierName = parseFamilyAndQualifierName(familyName);

        SysHbaseTableData sysHbaseTableData = new SysHbaseTableData();
        if (familyAndQualifierName != null && StringUtils.isNotBlank(rowKey)) {
            //HBaseRowData rowData = multiHBaseService.get(clusterCodeOfCurrentSession(), tableName, rowKey, familyAndQualifierName[0], familyAndQualifierName[1]);
            // sysHbaseTableData = mapToHBaseTableDataList(tableName, rowData).get(0);
        }
        mmap.put("sysHbaseTableData", sysHbaseTableData);
        return prefix + "/detail";
    }

    /**
     * 导出HBase列表数据
     */
    @RequiresPermissions("hbase:data:export")
    @Log(title = "HBase数据导出", businessType = BusinessType.EXPORT)
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
    @RequiresPermissions("hbase:data:add")
    @Log(title = "HBase数据新增", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysHbaseTableData sysHbaseTableData) {
        String clusterCode = clusterCodeOfCurrentSession();
        String tableName = sysHbaseTableData.getTableName();
        if (multiHBaseAdminService.isTableDisabled(clusterCode, tableName)) {
            throw new BusinessException("表[" + tableName + "]处于禁用状态！");
        }
        String familyName = sysHbaseTableData.getFamilyName() +
                HConstants.TABLE_NAME_SPLIT_CHAR + sysHbaseTableData.getFieldName();
        //multiHBaseService.saveOrUpdate(clusterCode, tableName, sysHbaseTableData.getRowKey(),
        //        familyName, sysHbaseTableData.getValue());
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
            //HBaseRowData rowData = multiHBaseService.get(clusterCodeOfCurrentSession(), tableName, rowKey, familyAndQualifierName[0], familyAndQualifierName[1]);
            //sysHbaseTableData = mapToHBaseTableDataList(tableName, rowData).get(0);
        }
        mmap.put("sysHbaseTableData", sysHbaseTableData);
        return prefix + "/edit";
    }

    /**
     * 修改保存HBase数据
     */
    @RequiresPermissions("hbase:data:edit")
    @Log(title = "HBase数据更新", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysHbaseTableData sysHbaseTableData) {
        String clusterCode = clusterCodeOfCurrentSession();
        String tableName = sysHbaseTableData.getTableName();
        if (multiHBaseAdminService.isTableDisabled(clusterCode, tableName)) {
            throw new BusinessException("表[" + tableName + "]处于禁用状态！");
        }
        // multiHBaseService.saveOrUpdate(clusterCode, tableName, sysHbaseTableData.getRowKey(), sysHbaseTableData.getFamilyName(), sysHbaseTableData.getValue());
        return success("数据修改成功");
    }

    /**
     * 删除HBase数据
     */
    @RequiresPermissions("hbase:data:remove")
    @Log(title = "HBase数据删除", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@RequestParam String tableName, @RequestParam String familyName, @RequestParam String rowKey) {
        String[] familyAndQualifierName = parseFamilyAndQualifierName(familyName);
        //multiHBaseService.delete(clusterCodeOfCurrentSession(), tableName, rowKey, familyAndQualifierName[0], familyAndQualifierName[1]);
        return success("数据删除成功！");
    }

    private List<SysHbaseTableData> mapToHBaseTableDataList(String tableName, HBaseRowData rowData) {
        List<SysHbaseTableData> hbaseTableDataList = new ArrayList<>();
        rowData.getData().forEach((key, value) -> {
            SysHbaseTableData sysHbaseTableData = new SysHbaseTableData();
            sysHbaseTableData.setTableName(HConstants.getFullTableName(tableName));
            sysHbaseTableData.setRowKey(rowData.getRow());
            sysHbaseTableData.setFamilyName(key);
            //todo fix timestamp
            sysHbaseTableData.setTimestamp("0");
            sysHbaseTableData.setValue(value);
            hbaseTableDataList.add(sysHbaseTableData);
        });

        return hbaseTableDataList;
    }

    private List<CxSelect> getTableFamilyRelations() {
        String clusterCode = clusterCodeOfCurrentSession();

        final List<String> allTableNames = multiHBaseAdminService.listAllTableName(clusterCode);
        List<CxSelect> cxTableInfoList = new ArrayList<>(allTableNames.size());

        for (String tableName : allTableNames) {
            if (multiHBaseAdminService.isTableDisabled(clusterCode, tableName)) {
                continue;
            }
            HTableDesc tableDesc = multiHBaseAdminService.getHTableDesc(clusterCode, tableName);

            CxSelect cxSelectTable = new CxSelect();
            cxSelectTable.setN(tableName);
            cxSelectTable.setV(tableName);

            List<String> families = tableDesc.getColumnFamilyDescList().stream().map(ColumnFamilyDesc::getName).collect(Collectors.toList());
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
