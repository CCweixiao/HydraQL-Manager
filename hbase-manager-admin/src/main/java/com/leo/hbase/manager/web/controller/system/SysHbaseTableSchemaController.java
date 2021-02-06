package com.leo.hbase.manager.web.controller.system;

import com.github.CCweixiao.model.TableDesc;
import com.leo.hbase.manager.common.annotation.Log;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.core.page.TableDataInfo;
import com.leo.hbase.manager.common.enums.BusinessType;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.common.utils.security.StrEnDeUtils;
import com.leo.hbase.manager.system.dto.TableSchemaDto;
import com.leo.hbase.manager.web.service.IMultiHBaseAdminService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author leojie 2020/11/29 11:13 上午
 */
@Controller
@RequestMapping("/system/schema")
public class SysHbaseTableSchemaController extends SysHbaseBaseController {
    private String prefix = "system/schema";

    @Autowired
    private IMultiHBaseAdminService multiHBaseAdminService;


    @RequiresPermissions("system:schema:view")
    @GetMapping()
    public String schema(ModelMap mmap) {
        final List<String> allTableNames = multiHBaseAdminService.listAllTableName(clusterCodeOfCurrentSession(), true);
        mmap.put("allTableNames", allTableNames);
        return prefix + "/schema";
    }

    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存HBaseTableSchema
     */
    @RequiresPermissions("system:schema:add")
    @Log(title = "HBaseTableSchema", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated TableSchemaDto tableSchemaDto) {
        TableDesc tableDesc = tableSchemaDto.convertTo();
        boolean tableExists = multiHBaseAdminService.tableIsExists(clusterCodeOfCurrentSession(), tableDesc.getTableName());
        if (!tableExists) {
            return error("表[" + tableDesc.getTableName() + "]不存在");
        }
        multiHBaseAdminService.modifyTable(clusterCodeOfCurrentSession(), tableDesc);
        return AjaxResult.success("HBaseTableSchema添加成功");
    }

    /**
     * 删除HBaseTableSchema
     */
    @RequiresPermissions("system:schema:remove")
    @Log(title = "HBaseTableSchema", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult removeTableSchema(String ids) {
        final String tableName = StrEnDeUtils.decrypt(ids);
        TableDesc tableDesc = multiHBaseAdminService.getTableDesc(clusterCodeOfCurrentSession(), tableName);
        Map<String, String> tableProps = tableDesc.getTableProps();
        if (tableProps == null || tableProps.isEmpty()) {
            tableProps = new HashMap<>(1);
        } else {
            tableProps.put("tableSchema", "");
        }
        tableDesc.setTableProps(tableProps);
        multiHBaseAdminService.modifyTable(clusterCodeOfCurrentSession(), tableDesc);
        return AjaxResult.success("HBaseTableSchema移除成功");
    }

    /**
     * 查询HBase Schema 列表
     */
    @RequiresPermissions("system:schema:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TableSchemaDto tableSchemaDto) {
        String currentCluster = clusterCodeOfCurrentSession();

        final List<TableSchemaDto> tableSchemaDtoList = multiHBaseAdminService.listAllTableDesc(currentCluster, true)
                .stream().filter(tableDesc -> {
                    if (StringUtils.isBlank(tableSchemaDto.getTableName())) {
                        return true;
                    } else {
                        return tableSchemaDto.getTableName().equals(tableDesc.getTableName());
                    }
                }).filter(tableDesc -> {
                    final Map<String, String> tableProps = tableDesc.getTableProps();
                    if (tableProps == null || tableProps.isEmpty()) {
                        return false;
                    } else {
                        return tableProps.containsKey("tableSchema") && StringUtils.isNotBlank(tableProps.get("tableSchema"));
                    }
                }).map(tableDesc -> new TableSchemaDto().convertFor(tableDesc)).collect(Collectors.toList());

        return getDataTable(tableSchemaDtoList);
    }

}
