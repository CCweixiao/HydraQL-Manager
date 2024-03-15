package com.leo.hbase.manager.web.controller.system;

import com.alibaba.fastjson2.JSON;
import com.hydraql.manager.core.hbase.SplitGoEnum;
import com.hydraql.manager.core.hbase.schema.ColumnFamilyDesc;
import com.hydraql.manager.core.hbase.schema.HTableDesc;
import com.hydraql.manager.core.util.HConstants;
import com.leo.hbase.manager.common.annotation.Log;
import com.leo.hbase.manager.common.constant.HBaseManagerConstants;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.core.domain.CxSelect;
import com.leo.hbase.manager.common.core.page.TableDataInfo;
import com.leo.hbase.manager.common.core.text.Convert;
import com.leo.hbase.manager.common.enums.BusinessType;
import com.leo.hbase.manager.common.enums.HBaseDisabledFlag;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.common.utils.poi.ExcelUtil;
import com.leo.hbase.manager.framework.util.ShiroUtils;
import com.leo.hbase.manager.system.domain.SysHbaseTable;
import com.leo.hbase.manager.system.domain.SysHbaseTableTag;
import com.leo.hbase.manager.system.domain.SysHbaseTag;
import com.leo.hbase.manager.system.domain.SysHbaseUserTable;
import com.leo.hbase.manager.system.dto.HTableDescDto;
import com.leo.hbase.manager.system.dto.NamespaceDescDto;
import com.leo.hbase.manager.system.service.ISysHbaseTableService;
import com.leo.hbase.manager.system.service.ISysHbaseTableTagService;
import com.leo.hbase.manager.system.service.ISysHbaseUserTableService;
import com.leo.hbase.manager.system.service.ISysUserService;
import com.leo.hbase.manager.web.controller.params.EnableOrDisableTableParam;
import com.leo.hbase.manager.web.controller.query.QueryHBaseTableForm;
import com.leo.hbase.manager.web.service.IMultiHBaseAdminService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import static com.leo.hbase.manager.common.constant.HBasePropertyConstants.HBASE_TABLE_DISABLE_FLAG;
import static com.leo.hbase.manager.common.constant.HBasePropertyConstants.HBASE_TABLE_ENABLE_FLAG;

/**
 * HBaseController
 *
 * @author leojie
 * @date 2020-08-16
 */
@Controller
@RequestMapping("/system/table")
public class SysHbaseTableController extends SysHbaseBaseController {
    private String prefix = "system/table";

    @Autowired
    private IMultiHBaseAdminService multiHBaseAdminService;

    @Autowired
    private ISysHbaseUserTableService hbaseUserTableService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysHbaseTableService sysHbaseTableService;

    @Autowired
    private ISysHbaseTableTagService sysHbaseTableTagService;


    @RequiresPermissions("hbase:table:view")
    @GetMapping()
    public String table(ModelMap mmap) {
        final List<NamespaceDescDto> namespaceDescList = getAllNamespaces();
        mmap.put("namespaces", namespaceDescList);
        mmap.put("tags", sysHbaseTagService.selectAllSysHbaseTagList());
        return prefix + "/table";
    }

    @GetMapping("/data/{tableId}")
    public String data(@PathVariable("tableId") Long tableId, ModelMap mmap) {
        SysHbaseTable sysHbaseTable = sysHbaseTableService.selectSysHbaseTableById(tableId);
        checkSysHbaseTableExists(sysHbaseTable);
        mmap.put("tableFamilyData", JSON.toJSON(getTableSelectData(sysHbaseTable.getTableName())));
        mmap.put("tableId", tableId);
        return prefix + "/data";
    }

    @GetMapping("/data/{tableId}/add")
    public String addData(@PathVariable("tableId") Long tableId, ModelMap mmap) {
        SysHbaseTable sysHbaseTable = sysHbaseTableService.selectSysHbaseTableById(tableId);
        checkSysHbaseTableExists(sysHbaseTable);
        mmap.put("tableFamilyData", JSON.toJSON(getTableSelectData(sysHbaseTable.getTableName())));
        return prefix + "/addData";
    }


    @RequiresPermissions("hbase:table:detail")
    @GetMapping("/detail/{tableId}")
    public String detail(@PathVariable("tableId") Long tableId, ModelMap mmap) {
        SysHbaseTable sysHbaseTable = sysHbaseTableService.selectSysHbaseTableById(tableId);
        checkSysHbaseTableExists(sysHbaseTable);
        HTableDescDto tableDescDto = new HTableDescDto().convertFor(sysHbaseTable);
        final HTableDesc tableDesc = multiHBaseAdminService.getHTableDesc(clusterCodeOfCurrentSession(), tableDescDto.getTableName());
        String tableDescToStr = tableDesc.toString();
        tableDescDto.setTableDesc(tableDescToStr);
        mmap.put("hbaseTable", tableDescDto);
        return prefix + "/detail";
    }

    @RequiresPermissions("hbase:table:detail")
    @GetMapping("/family/detail/{tableId}")
    public String familyDetail(@PathVariable("tableId") Long tableId, ModelMap mmap) {
        SysHbaseTable sysHbaseTable = sysHbaseTableService.selectSysHbaseTableById(tableId);
        checkSysHbaseTableExists(sysHbaseTable);
        String clusterCode = clusterCodeOfCurrentSession();
        HTableDesc tableDesc = multiHBaseAdminService.getHTableDesc(clusterCode, sysHbaseTable.getTableName());
        HTableDescDto tableDescDto = new HTableDescDto().convertFor(sysHbaseTable);
        tableDescDto.setTableDesc(tableDesc.toString());
        mmap.put("tableObj", tableDescDto);

        List<SysHbaseTable> sysHbaseTableList = sysHbaseTableService.selectSysHbaseTableList(new SysHbaseTable());
        if (sysHbaseTableList == null || sysHbaseTableList.isEmpty()) {
            mmap.put("tableMapList", new ArrayList<>());
        } else {
            List<Map<String, Object>> tableMapList = new ArrayList<>(sysHbaseTableList.size());
            for (SysHbaseTable table : sysHbaseTableList) {
                Map<String, Object> tableMap = new HashMap<>(2);
                tableMap.put("tableId", table.getTableId());
                tableMap.put("tableName", table.getTableName());
                tableMapList.add(tableMap);
                mmap.put("tableMapList", tableMapList);
            }
        }
        return "system/family/family";
    }

    /**
     * 查询HBase列表
     */
    @RequiresPermissions("hbase:table:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(QueryHBaseTableForm queryHBaseTableForm) {
        SysHbaseTable sysHbaseTable = queryHBaseTableForm.getQuerySysHbaseTableParams(clusterCodeOfCurrentSession());
        List<SysHbaseTable> sysHbaseTableList = sysHbaseTableService.selectSysHbaseTableList(sysHbaseTable);
        return getDataTable(sysHbaseTableList);
    }

    /**
     * 导出HBase列表
     */
    @RequiresPermissions("hbase:table:export")
    @Log(title = "HBase表信息导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(QueryHBaseTableForm queryHBaseTableForm) {
        SysHbaseTable sysHbaseTable = queryHBaseTableForm.getQuerySysHbaseTableParams(clusterCodeOfCurrentSession());
        List<SysHbaseTable> sysHbaseTableList = sysHbaseTableService.selectSysHbaseTableList(sysHbaseTable);
        ExcelUtil<SysHbaseTable> util = new ExcelUtil<>(SysHbaseTable.class);
        return util.exportExcel(sysHbaseTableList, "table");
    }

    /**
     * 新增HBase表
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        List<NamespaceDescDto> namespaces = multiHBaseAdminService.listAllNamespaceDesc(clusterCodeOfCurrentSession()).stream()
                .filter(namespaceDesc -> !HConstants.DEFAULT_SYS_TABLE_NAMESPACE.equals(namespaceDesc.getNamespaceName()))
                .map(namespaceDesc -> new NamespaceDescDto().convertFor(namespaceDesc)).collect(Collectors.toList());
        mmap.put("namespaces", namespaces);
        mmap.put("tags", sysHbaseTagService.selectAllSysHbaseTagList());
        return prefix + "/add";
    }

    /**
     * 新增保存HBase
     */
    @RequiresPermissions("hbase:table:add")
    @Log(title = "HBase表新增", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated HTableDescDto tableDescDto) {
        String clusterId = clusterCodeOfCurrentSession();
        tableDescDto.setClusterId(clusterId);
        final String tableName = tableDescDto.getTableName();
        if (multiHBaseAdminService.tableIsExists(clusterId, tableName)) {
            return error(String.format("HBase表[%s]已经在集群[%s]中存在!", tableName, clusterId));
        }
        tableDescDto.setCreateBy(ShiroUtils.getLoginName());
        SysHbaseTable sysHbaseTable = tableDescDto.convertToSysHbaseTable();
        if (sysHbaseTableService.selectSysHbaseTableCountByNameInOneCluster(sysHbaseTable) > 0) {
            return error(String.format("HBase表[%s]的记录信息已存在!", tableName));
        }
        HTableDesc tableDesc = tableDescDto.convertTo();
        boolean createTableRes = false;
        if (StringUtils.isBlank(tableDescDto.getSplitWay())) {
            createTableRes = multiHBaseAdminService.createTable(clusterId, tableDesc);
        } else if (HBaseManagerConstants.SPLIT_1.equals(tableDescDto.getSplitWay())) {
            if (StringUtils.isBlank(tableDescDto.getStartKey())) {
                return error("预分区开始的key不能为空");
            }
            if (StringUtils.isBlank(tableDescDto.getEndKey())) {
                return error("预分区结束的key不能为空");
            }
            if (tableDescDto.getPreSplitRegions() < 1) {
                return error("预分区数不能为0");
            }
            createTableRes = multiHBaseAdminService.createTable(clusterId, tableDesc, tableDescDto.getStartKey(),
                    tableDescDto.getEndKey(), tableDescDto.getPreSplitRegions(), false);

        } else if (HBaseManagerConstants.SPLIT_2.equals(tableDescDto.getSplitWay())) {
            String[] splitKeys = Convert.toStrArray(tableDescDto.getPreSplitKeys());
            if (splitKeys.length < 1) {
                return error("预分区的key不能为空");
            }
            createTableRes = multiHBaseAdminService.createTable(clusterId, tableDesc, splitKeys, false);
        } else if (HBaseManagerConstants.SPLIT_3.equals(tableDescDto.getSplitWay())) {
            final SplitGoEnum splitGoEnum = SplitGoEnum.getSplitGoEnum(tableDescDto.getSplitGo());
            if (splitGoEnum == null) {
                return error("预分区Key的分隔策略不能为空");
            }
            if (tableDescDto.getNumRegions() < 1) {
                return error("预分区数不能为0");
            }

            createTableRes = multiHBaseAdminService.createTable(clusterId, tableDesc, splitGoEnum, tableDescDto.getNumRegions(), false);
        }

        if (!createTableRes) {
            return error("系统异常，HBase表[" + tableName + "]创建失败！");
        }
        int record = sysHbaseTableService.insertSysHbaseTable(sysHbaseTable);
        if (record < 1) {
            return error("系统异常，HBase表[" + tableName + "]元数据信息保存失败！");
        }
        Long tableId = sysHbaseTable.getTableId();
        Long[] tagIds = tableDescDto.getTagIds();
        if (tagIds == null || tagIds.length == 0) {
            // 清除该表所有tags
            sysHbaseTableTagService.deleteSysHbaseTableTagByTableId(tableId);
        } else {
            List<SysHbaseTableTag> sysHbaseTableTagList = new ArrayList<>(tagIds.length);
            for (Long tagId : tagIds) {
                SysHbaseTableTag sysHbaseTableTag = new SysHbaseTableTag();
                sysHbaseTableTag.setTagId(tagId);
                sysHbaseTableTag.setTableId(tableId);
                sysHbaseTableTagList.add(sysHbaseTableTag);
            }
            sysHbaseTableTagService.batchAddSysHbaseTableTag(sysHbaseTableTagList);
        }

        SysHbaseUserTable sysHbaseUserTable = new SysHbaseUserTable();
        sysHbaseUserTable.setTableId(tableId);
        sysHbaseUserTable.setUserId(ShiroUtils.getUserId());
        record = hbaseUserTableService.insertSysHbaseUserTable(sysHbaseUserTable);
        if (record < 1) {
            return error("系统异常，HBase表[" + tableName + "]和用户id绑定失败！");
        }

        return success("HBase表[" + tableName + "]创建成功！");
    }

    /**
     * 修改HBase
     */
    @GetMapping("/edit/{tableId}")
    public String edit(@PathVariable("tableId") Long tableId, ModelMap mmap) {
        SysHbaseTable sysHbaseTable = sysHbaseTableService.selectSysHbaseTableById(tableId);
        checkSysHbaseTableExists(sysHbaseTable);
        HTableDescDto tableDescDto = new HTableDescDto().convertFor(sysHbaseTable);
        mmap.put("tableDescDto", tableDescDto);
        mmap.put("tags", selectHBaseTagsByTable(sysHbaseTable.getTags()));
        return prefix + "/edit";
    }


    /**
     * 修改保存HBase
     */
    @RequiresPermissions("hbase:table:edit")
    @Log(title = "HBase表信息修改", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(HTableDescDto tableDescDto) {
        tableDescDto.setUpdateBy(ShiroUtils.getLoginName());
        SysHbaseTable sysHbaseTable = tableDescDto.convertToSysHbaseTable();
        Long tableId = sysHbaseTable.getTableId();
        Long[] tagIds = tableDescDto.getTagIds();
        sysHbaseTableTagService.deleteSysHbaseTableTagByTableId(tableId);
        if (tagIds != null && tagIds.length > 0) {
            List<SysHbaseTableTag> sysHbaseTableTagList = new ArrayList<>(tagIds.length);
            for (Long tagId : tagIds) {
                SysHbaseTableTag sysHbaseTableTag = new SysHbaseTableTag();
                sysHbaseTableTag.setTagId(tagId);
                sysHbaseTableTag.setTableId(tableId);
                sysHbaseTableTagList.add(sysHbaseTableTag);
            }
            sysHbaseTableTagService.batchAddSysHbaseTableTag(sysHbaseTableTagList);
        }
        return toAjax(sysHbaseTableService.updateSysHbaseTable(sysHbaseTable));
    }

    /**
     * 修改保存HBase表禁用状态
     */
    @RequiresPermissions("hbase:table:edit")
    @Log(title = "HBase表状态修改", businessType = BusinessType.UPDATE)
    @PostMapping("/changeDisableStatus")
    @ResponseBody
    public AjaxResult enableOrDisableTable(@Validated EnableOrDisableTableParam enableOrDisableTableParam) {
        SysHbaseTable sysHbaseTable = sysHbaseTableService.selectSysHbaseTableById(enableOrDisableTableParam.getTableId());
        checkSysHbaseTableExists(sysHbaseTable);
        String tableName = sysHbaseTable.getTableName();
        String clusterId = clusterCodeOfCurrentSession();
        String disableFlag = enableOrDisableTableParam.getDisableFlag();
        boolean changeTableDisabledStatusRes = false;
        boolean disableOp = false;
        boolean enableOp = false;
        if (HBaseDisabledFlag.DISABLED.getCode().equals(disableFlag)) {
            disableOp = true;
        }
        if (HBaseDisabledFlag.ENABLED.getCode().equals(disableFlag)) {
            enableOp = true;
        }
        boolean tableDisabled = multiHBaseAdminService.isTableDisabled(clusterId, tableName);
        if (disableOp) {
            if (tableDisabled) {
                return success();
            }
            changeTableDisabledStatusRes = multiHBaseAdminService.disableTable(clusterId, tableName);
        }
        if (enableOp) {
            if (!tableDisabled) {
                return success();
            }
            changeTableDisabledStatusRes = multiHBaseAdminService.enableTable(clusterId, tableName);
        }

        if (!changeTableDisabledStatusRes) {
            return error("系统异常，禁用|启用表失败");
        }
        SysHbaseTable editSysHbaseTable = new SysHbaseTable();
        editSysHbaseTable.setTableId(sysHbaseTable.getTableId());
        editSysHbaseTable.setDisableFlag(disableFlag);
        int record = sysHbaseTableService.updateSysHbaseTable(editSysHbaseTable);
        if (record < 1) {
            return error("表禁用｜启用状态更新失败");
        }
        return success();
    }


    /**
     * 删除HBase表
     */
    @RequiresPermissions("hbase:table:remove")
    @Log(title = "HBase表删除", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(Long tableId) {
        SysHbaseTable sysHbaseTable = sysHbaseTableService.selectSysHbaseTableById(tableId);
        checkSysHbaseTableExists(sysHbaseTable);
        final String clusterId = clusterCodeOfCurrentSession();
        final String tableName = sysHbaseTable.getTableName();
        boolean tableIsExistsInCluster = multiHBaseAdminService.tableIsExists(clusterId, tableName);
        boolean deleteTableSuccess;

        if (tableIsExistsInCluster) {
            if (!multiHBaseAdminService.isTableDisabled(clusterId, tableName)) {
                return error("非禁用状态的表不能被删除");
            }
            deleteTableSuccess = multiHBaseAdminService.deleteTable(clusterId, tableName);
        } else {
            deleteTableSuccess = true;
        }

        if (!deleteTableSuccess) {
            return error("系统异常，表删除失败！");
        }
        sysHbaseTableService.deleteSysHbaseTableById(tableId);
        hbaseUserTableService.deleteSysHbaseUserTableByTableId(tableId);
        sysHbaseTableTagService.deleteSysHbaseTableTagByTableId(tableId);
        return success();
    }


    /**
     * 清空HBase表的数据
     */
    @RequiresPermissions("hbase:table:clear")
    @Log(title = "HBase表数据清空", businessType = BusinessType.DELETE)
    @PostMapping("/truncatePreserveTable")
    @ResponseBody
    public AjaxResult truncatePreserveTable(Long tableId) {
        SysHbaseTable sysHbaseTable = sysHbaseTableService.selectSysHbaseTableById(tableId);
        checkSysHbaseTableExists(sysHbaseTable);
        final String clusterId = clusterCodeOfCurrentSession();
        final String tableName = sysHbaseTable.getTableName();
        if (!multiHBaseAdminService.isTableDisabled(clusterId, tableName)) {
            return error("非禁用状态的表不能被清空数据");
        }

        multiHBaseAdminService.truncatePreserve(clusterId, tableName);
        return success("表[" + tableName + "]中数据已被清空");
    }

    /**
     * HBase表元数据从集群中拉取
     */
    @RequiresPermissions("hbase:table:fetchTable")
    @Log(title = "HBase表元数据同步", businessType = BusinessType.INSERT)
    @PostMapping("/fetchTableFromCluster")
    @ResponseBody
    public AjaxResult fetchTableFromCluster(String operation) {
        final String clusterId = clusterCodeOfCurrentSession();
        List<String> tableNameList = multiHBaseAdminService.listAllTableName(clusterId);
        if (tableNameList == null || tableNameList.isEmpty()) {
            return success();
        }
        List<SysHbaseTable> sysHbaseTables = new ArrayList<>(tableNameList.size());

        try {
            for (String tableName : tableNameList) {
                Future<SysHbaseTable> sysHbaseTablesFuture =
                        multiHBaseAdminService.fetchSysHbaseTableFromCluster(clusterId, tableName);
                SysHbaseTable sysHbaseTable = sysHbaseTablesFuture.get();
                sysHbaseTables.add(sysHbaseTable);
            }
            return toAjax(sysHbaseTableService.batchSaveSysHbaseTableList(sysHbaseTables));
        } catch (InterruptedException | ExecutionException e) {
           return AjaxResult.error("同步HBase表元数据超时");
        }
    }


    private List<NamespaceDescDto> getAllNamespaces() {
        return multiHBaseAdminService.listAllNamespaceDesc(clusterCodeOfCurrentSession())
                .stream().map(namespaceDesc -> new NamespaceDescDto().convertFor(namespaceDesc))
                .collect(Collectors.toList());
    }


    private List<SysHbaseTag> selectHBaseTagsByTable(List<SysHbaseTag> hbaseTags) {
        List<SysHbaseTag> tags = sysHbaseTagService.selectAllSysHbaseTagList();
        if (hbaseTags == null || hbaseTags.isEmpty()) {
            return tags;
        }
        for (SysHbaseTag tag : tags) {
            for (SysHbaseTag hbaseTag : hbaseTags) {
                if (tag.getTagId().longValue() == hbaseTag.getTagId().longValue()) {
                    tag.setFlag(true);
                    break;
                }
            }
        }
        return tags;
    }

    private List<CxSelect> getTableSelectData(String tableName) {
        List<String> families = multiHBaseAdminService.getColumnFamilyDesc(clusterCodeOfCurrentSession(), tableName)
                .stream().map(ColumnFamilyDesc::getName).collect(Collectors.toList());
        List<CxSelect> cxTableInfoList = new ArrayList<>();
        CxSelect cxSelectTable = new CxSelect();
        cxSelectTable.setN(tableName);
        cxSelectTable.setV(tableName);
        List<CxSelect> tempFamilyList = new ArrayList<>();
        for (String family : families) {
            CxSelect cxSelectFamily = new CxSelect();
            cxSelectFamily.setN(family);
            cxSelectFamily.setV(family);
            tempFamilyList.add(cxSelectFamily);
        }
        cxSelectTable.setS(tempFamilyList);
        cxTableInfoList.add(cxSelectTable);
        return cxTableInfoList;
    }

}
