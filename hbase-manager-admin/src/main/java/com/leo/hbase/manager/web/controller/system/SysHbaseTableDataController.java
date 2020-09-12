package com.leo.hbase.manager.web.controller.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.util.StrUtil;
import com.leo.hbase.manager.adaptor.service.IHBaseAdminService;
import com.leo.hbase.manager.adaptor.service.IHBaseService;
import com.leo.hbase.manager.common.core.domain.CxSelect;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
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
import com.leo.hbase.manager.system.domain.SysHbaseTableData;
import com.leo.hbase.manager.common.core.controller.BaseController;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.utils.poi.ExcelUtil;
import com.leo.hbase.manager.common.core.page.TableDataInfo;

/**
 * HBaseController
 *
 * @author leojie
 * @date 2020-09-07
 */
@Controller
@RequestMapping("/system/data")
public class SysHbaseTableDataController extends BaseController {
    private String prefix = "system/data";

    @Autowired
    private IHBaseService ihBaseService;

    @Autowired
    private IHBaseAdminService ihBaseAdminService;

    @RequiresPermissions("system:data:view")
    @GetMapping()
    public String data(ModelMap mmap) {
        final List<String> allTableNames = ihBaseAdminService.listAllTableName();
        List<CxSelect> cxTableInfoList = new ArrayList<>(allTableNames.size());

        for (String tableName : allTableNames) {
            if (ihBaseAdminService.isTableDisabled(tableName)) {
                continue;
            }
            HTableDescriptor tableDescriptor = ihBaseAdminService.getTableDescriptor(tableName);
            CxSelect cxSelectTable = new CxSelect();
            cxSelectTable.setN(tableName);
            cxSelectTable.setV(tableName);

            List<String> families = tableDescriptor.getFamilies().stream().map(HColumnDescriptor::getNameAsString).collect(Collectors.toList());
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
        List<SysHbaseTableData> list = new ArrayList<>();


        if (StrUtil.isBlank(sysHbaseTableData.getTableName())) {
            return getDataTable(list);
        }

        if (ihBaseAdminService.isTableDisabled(sysHbaseTableData.getTableName())) {
            throw new HBaseOperationsException("表[" + sysHbaseTableData.getTableName() + "]处于禁用状态，无法被查询！");
        }

        if (StrUtil.isNotBlank(sysHbaseTableData.getRowKey())) {
            final List<Map<String, Object>> dataMapList = ihBaseService.get(sysHbaseTableData.getTableName(), sysHbaseTableData.getRowKey(), sysHbaseTableData.getFamilyName());
            if (dataMapList.isEmpty()) {
                return getDataTable(list);
            }
            for (Map<String, Object> dataMap : dataMapList) {
                SysHbaseTableData hbaseTableData = mapToHBaseTableData(sysHbaseTableData.getTableName(), dataMap);
                list.add(hbaseTableData);
            }

            return getDataTable(list);
        }
        List<List<Map<String, Object>>> dataMaps = ihBaseService.find(sysHbaseTableData.getTableName(), sysHbaseTableData.getFamilyName(), sysHbaseTableData.getStartKey(), sysHbaseTableData.getLimit());
        if (dataMaps == null || dataMaps.isEmpty()) {
            return getDataTable(list);
        }

        list = dataMaps.stream().flatMap(Collection::stream).map(dd -> mapToHBaseTableData(sysHbaseTableData.getTableName(), dd)).collect(Collectors.toList());

        return getDataTable(list);
    }

    /**
     * 导出HBase列表数据
     */
    @RequiresPermissions("system:data:export")
    @Log(title = "HBase数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysHbaseTableData sysHbaseTableData) {
        //TODO 导出数据
        List<SysHbaseTableData> list = new ArrayList<>();
        ExcelUtil<SysHbaseTableData> util = new ExcelUtil<>(SysHbaseTableData.class);
        return util.exportExcel(list, "data");
    }

    /**
     * 新增HBase
     */
    @GetMapping("/add")
    public String add() {
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
        String tableName = sysHbaseTableData.getTableName();
        if (ihBaseAdminService.isTableDisabled(tableName)) {
            throw new HBaseOperationsException("表[" + tableName + "]处于禁用状态！");
        }
        ihBaseService.saveOrUpdate(tableName, sysHbaseTableData.getRowKey(), sysHbaseTableData.getFamilyName(), sysHbaseTableData.getValue());
        return success("数据新增成功！");
    }

    /**
     * 修改HBase数据
     */
    @GetMapping("/edit/{rowKey}")
    public String edit(@PathVariable("rowKey") String rowKey, ModelMap mmap) {
        //TODO 修改数据
        SysHbaseTableData sysHbaseTableData = new SysHbaseTableData();
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
        return error("暂不支持修改数据");
    }

    /**
     * 删除HBase数据
     */
    @RequiresPermissions("system:data:remove")
    @Log(title = "HBase数据", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        if (StrUtil.isBlank(ids)) {
            return error("待删除数据不能为空！");
        }
        String[] conditions = ids.split(",");
        if (conditions.length != 3) {
            return error("请传入有效的删除条件！");
        }
        String tableName = conditions[0];
        String familyName = conditions[1].split(":")[0];
        String qualifier = conditions[1].split(":")[1];
        String rowKey = conditions[2];
        ihBaseService.delete(tableName, rowKey, familyName, qualifier);
        return success("数据删除成功！");
    }

    private SysHbaseTableData mapToHBaseTableData(String tableName, Map<String, Object> dataMap) {
        SysHbaseTableData hbaseTableData = new SysHbaseTableData();
        hbaseTableData.setTableName(tableName);
        hbaseTableData.setRowKey(dataMap.get("rowKey").toString());
        hbaseTableData.setFamilyName(dataMap.get("familyName").toString());
        hbaseTableData.setTimestamp(dataMap.get("timestamp").toString());
        hbaseTableData.setValue(dataMap.get("value").toString());
        return hbaseTableData;
    }
}
