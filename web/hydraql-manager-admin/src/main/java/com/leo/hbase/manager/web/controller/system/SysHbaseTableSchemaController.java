package com.leo.hbase.manager.web.controller.system;

import com.alibaba.fastjson2.JSON;
import com.leo.hbase.manager.common.annotation.Log;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.core.page.TableDataInfo;
import com.leo.hbase.manager.common.core.text.Convert;
import com.leo.hbase.manager.common.enums.BusinessType;
import com.leo.hbase.manager.system.domain.SysHbaseTable;
import com.leo.hbase.manager.system.domain.SysHbaseTableSchema;
import com.leo.hbase.manager.system.domain.schema.HTableSchema;
import com.leo.hbase.manager.system.dto.TableSchemaDto;
import com.leo.hbase.manager.system.service.ISysHbaseTableSchemaService;
import com.leo.hbase.manager.system.service.ISysHbaseTableService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leojie 2020/11/29 11:13 上午
 */
@Controller
@RequestMapping("/system/schema")
public class SysHbaseTableSchemaController extends SysHbaseBaseController {
    private String prefix = "system/schema";

    @Autowired
    private ISysHbaseTableService sysHbaseTableService;

    @Autowired
    private ISysHbaseTableSchemaService sysHbaseTableSchemaService;


    @RequiresPermissions("hbase:schema:view")
    @GetMapping()
    public String schema(ModelMap mmap) {
        List<SysHbaseTable> sysHbaseTables = sysHbaseTableService.selectSysHbaseTableListByClusterId(clusterCodeOfCurrentSession());
        final List<String> allTableNames = sysHbaseTables.stream().map(SysHbaseTable::getTableName).collect(Collectors.toList());
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
    @RequiresPermissions("hbase:schema:add")
    @Log(title = "HBaseTableSchema", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated TableSchemaDto tableSchemaDto) {
        String clusterId = clusterCodeOfCurrentSession();
        tableSchemaDto.setClusterId(clusterId);
        String tableSchema = tableSchemaDto.getTableSchema();
        HTableSchema hTableSchema;
        try {
            hTableSchema = JSON.parseObject(tableSchema, HTableSchema.class);
        } catch (Exception e) {
            return AjaxResult.error("HBase table scheme 格式异常");
        }
        tableSchemaDto.setTableName(hTableSchema.getTableName());
        SysHbaseTableSchema sysHbaseTableSchema = tableSchemaDto.convertTo();
        sysHbaseTableSchemaService.insertSysHbaseTableSchema(sysHbaseTableSchema);
        return AjaxResult.success("HBaseTableSchema添加成功");
    }

    /**
     * 删除HBaseTableSchema
     */
    @RequiresPermissions("hbase:schema:remove")
    @Log(title = "HBaseTableSchema", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult removeTableSchema(String ids) {
        Integer[] schemaIds = Convert.toIntArray(ids);
        for (Integer schemaId : schemaIds) {
            sysHbaseTableSchemaService.deleteSysHbaseTableSchemaById(schemaId);
        }
        return AjaxResult.success("HBaseTableSchema移除成功");
    }

    /**
     * 查询HBase Schema 列表
     */
    @RequiresPermissions("hbase:schema:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(@RequestParam(name = "tableName") String tableName) {
        String clusterId = clusterCodeOfCurrentSession();
        final List<TableSchemaDto> tableSchemaDtoList = sysHbaseTableSchemaService.selectSysHbaseTableSchemaList(clusterId, tableName)
                .stream().map(ts -> new TableSchemaDto().convertFor(ts)).collect(Collectors.toList());
        return getDataTable(tableSchemaDtoList);
    }

}
