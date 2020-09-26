package com.leo.hbase.manager.web.controller.system;

import com.github.CCweixiao.constant.HMHBaseConstant;
import com.github.CCweixiao.model.NamespaceDesc;
import com.github.CCweixiao.model.TableDesc;
import com.leo.hbase.manager.common.annotation.Log;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.core.page.TableDataInfo;
import com.leo.hbase.manager.common.enums.BusinessType;
import com.leo.hbase.manager.system.domain.SysHbaseTable;
import com.leo.hbase.manager.system.dto.NamespaceDescDto;
import com.leo.hbase.manager.system.dto.TableDescDto;
import com.leo.hbase.manager.system.service.ISysHbaseTagService;
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
import java.util.stream.Collectors;

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
    private ISysHbaseTagService sysHbaseTagService;
    @Autowired
    private IMultiHBaseAdminService multiHBaseAdminService;

    @RequiresPermissions("system:table:view")
    @GetMapping()
    public String table(ModelMap mmap) {
        final List<NamespaceDesc> namespaceDescList = multiHBaseAdminService.listAllNamespaceDesc(clusterCodeOfCurrentSession());
        mmap.put("namespaces", namespaceDescList);
        mmap.put("tags", sysHbaseTagService.selectAllSysHbaseTagList());
        return prefix + "/table";
    }

    @RequiresPermissions("system:table:detail")
    @GetMapping("/detail/{tableId}")
    public String detail(@PathVariable("tableId") String tableId, ModelMap mmap) {
        final TableDesc tableDesc = multiHBaseAdminService.getTableDesc(clusterCodeOfCurrentSession(), tableId);
        final Map<String, String> tableProps = tableDesc.getTableProps();

        String fullTableName = HMHBaseConstant.getFullTableName(tableDesc.getTableName());
        tableDesc.setTableName(fullTableName);
        TableDescDto tableDescDto = new TableDescDto().convertFor(tableDesc);
        if (tableProps != null && !tableProps.isEmpty()) {
            tableDescDto.setStatus(tableProps.getOrDefault("status", "0"));
            tableDescDto.setRemark(tableProps.getOrDefault("remark", "暂无备注"));
        }

        mmap.put("hbaseTable", tableDescDto);
        return prefix + "/detail";
    }

    @RequiresPermissions("system:table:detail")
    @GetMapping("/family/detail/{tableId}")
    public String familyDetail(@PathVariable("tableId") String tableId, ModelMap mmap) {
        String clusterCode = clusterCodeOfCurrentSession();
        TableDesc tableDesc = multiHBaseAdminService.getTableDesc(clusterCode, tableId);
        TableDescDto tableDescDto = new TableDescDto().convertFor(tableDesc);
        mmap.put("tableObj", tableDescDto);

        final List<String> listAllTableName = multiHBaseAdminService.listAllTableName(clusterCode);

        if (listAllTableName == null || listAllTableName.isEmpty()) {
            mmap.put("tableMapList", new ArrayList<>());
        } else {
            List<Map<String, Object>> tableMapList = new ArrayList<>(listAllTableName.size());
            for (String tableName : listAllTableName) {
                Map<String, Object> tableMap = new HashMap<>(2);
                tableMap.put("tableId", tableName);
                tableMap.put("tableName", HMHBaseConstant.getFullTableName(tableName));
                tableMapList.add(tableMap);
                mmap.put("tableMapList", tableMapList);
            }
        }
        return "system/family/family";
    }

    /**
     * 查询HBase列表
     */
    @RequiresPermissions("system:table:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysHbaseTable sysHbaseTable) {
        startPage();
        final List<TableDesc> tableDescList = multiHBaseAdminService.listAllTableDesc(clusterCodeOfCurrentSession());
        final List<TableDescDto> tableDescDtoList = tableDescList.stream().map(tableDesc -> new TableDescDto().convertFor(tableDesc)).collect(Collectors.toList());
        return getDataTable(tableDescDtoList);
    }

    /**
     * 导出HBase列表
     */
    @RequiresPermissions("system:table:export")
    @Log(title = "HBase", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysHbaseTable sysHbaseTable) {
        return error("暂时不支持列表导出");
       /* List<SysHbaseTable> list = sysHbaseTableService.selectSysHbaseTableList(sysHbaseTable);
        ExcelUtil<SysHbaseTable> util = new ExcelUtil<>(SysHbaseTable.class);
        return util.exportExcel(list, "table");*/
    }

    /**
     * 新增HBase表
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        List<NamespaceDescDto> namespaces = multiHBaseAdminService.listAllNamespaceDesc(clusterCodeOfCurrentSession()).stream()
                .filter(namespaceDesc -> !HMHBaseConstant.DEFAULT_SYS_TABLE_NAMESPACE.equals(namespaceDesc.getNamespaceName()))
                .map(namespaceDesc -> new NamespaceDescDto().convertFor(namespaceDesc)).collect(Collectors.toList());
        mmap.put("namespaces", namespaces);
        mmap.put("tags", sysHbaseTagService.selectAllSysHbaseTagList());
        return prefix + "/add";
    }

    /**
     * 新增保存HBase
     */
    @RequiresPermissions("system:table:add")
    @Log(title = "HBase", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated TableDescDto tableDescDto) {
        String clusterCode = clusterCodeOfCurrentSession();

        String fullTableName = tableDescDto.getNamespaceId() + ":" + tableDescDto.getTableName();

        if (multiHBaseAdminService.tableIsExists(clusterCode, fullTableName)) {
            return error("HBase表[" + fullTableName + "]已经存在！");
        }

        TableDesc tableDesc = tableDescDto.convertTo();
        boolean createTableRes = multiHBaseAdminService.createTable(clusterCode, tableDesc);

        if (!createTableRes) {
            return error("系统异常，HBase表[" + fullTableName + "]创建失败！");
        }
        return success("HBase表[" + fullTableName + "]创建成功！");
    }

    /**
     * 修改HBase
     */
    @GetMapping("/edit/{tableId}")
    public String edit(@PathVariable("tableId") String tableId, ModelMap mmap) {
        String clusterCode = clusterCodeOfCurrentSession();
        final List<NamespaceDesc> namespaceDescList = multiHBaseAdminService.listAllNamespaceDesc(clusterCode);
        mmap.put("namespaces", namespaceDescList);
        TableDesc tableDesc = multiHBaseAdminService.getTableDesc(clusterCode, tableId);
        mmap.put("sysHbaseTable", new TableDescDto().convertFor(tableDesc));

        //mmap.put("tags", sysHbaseTagService.selectSysHbaseTagsByTableId(tableId));
        return prefix + "/edit";
    }

    /**
     * 修改保存HBase
     */
    @RequiresPermissions("system:table:edit")
    @Log(title = "HBase", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysHbaseTable sysHbaseTable) {
        return error("暂时不支持HBase表的修改");
       /* if (sysHbaseTable == null || sysHbaseTable.getTableId() < 1) {
            return error("待修改信息的HBase表的id不能为空！");
        }
        SysHbaseTable exitsTable = sysHbaseTableService.selectSysHbaseTableById(sysHbaseTable.getTableId());
        if (exitsTable == null || exitsTable.getTableId() < 1) {
            return error("待修改信息的表[" + sysHbaseTable.getTableId() + "]不存在！");
        }
        sysHbaseTable.setUpdateBy(ShiroUtils.getSysUser().getLoginName());

        return toAjax(sysHbaseTableService.updateSysHbaseTable(sysHbaseTable));*/
    }

    /**
     * 修改保存HBase表禁用状态
     */
    @RequiresPermissions("system:table:edit")
    @Log(title = "HBase", businessType = BusinessType.UPDATE)
    @PostMapping("/changeDisableStatus")
    @ResponseBody
    public AjaxResult changeDisableStatus(SysHbaseTable sysHbaseTable) {
        String clusterCode = clusterCodeOfCurrentSession();
        if (sysHbaseTable == null || sysHbaseTable.getTableId() < 1) {
            return error("待修改表的id不能为空！");
        }
        final String fullHBaseTableName = HMHBaseConstant.getFullTableName(sysHbaseTable.getTableName());

        boolean changeTableDisabledStatusRes = false;

        if (multiHBaseAdminService.isTableDisabled(clusterCode, fullHBaseTableName)) {
            changeTableDisabledStatusRes = multiHBaseAdminService.enableTable(clusterCode, fullHBaseTableName);
        }
        if (!multiHBaseAdminService.isTableDisabled(clusterCode, fullHBaseTableName)) {
            changeTableDisabledStatusRes = multiHBaseAdminService.disableTable(clusterCode, fullHBaseTableName);
        }
        if (!changeTableDisabledStatusRes) {
            return error("系统异常，表状态修改失败！");
        }
        // sysHbaseTable.setUpdateBy(ShiroUtils.getSysUser().getLoginName());
        return success();
    }


    /**
     * 删除HBase表
     */
    @RequiresPermissions("system:table:remove")
    @Log(title = "HBase", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        final String clusterCode = clusterCodeOfCurrentSession();
        final String fullHBaseTableName = HMHBaseConstant.getFullTableName(ids);

        if (!multiHBaseAdminService.isTableDisabled(clusterCode, fullHBaseTableName)) {
            return error("非禁用状态的表不能被删除");
        }

        boolean deleteTableDisabledStatusRes = multiHBaseAdminService.deleteTable(clusterCode, fullHBaseTableName);
        if (!deleteTableDisabledStatusRes) {
            return error("系统异常，表状态修改失败！");
        }
        return success();
    }
}
