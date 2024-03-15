package com.leo.hbase.manager.web.controller.system;

import com.leo.hbase.manager.common.core.page.TableDataInfo;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.system.domain.SysHbaseTableSchema;
import com.leo.hbase.manager.system.dto.QueryHBaseSqlDto;
import com.leo.hbase.manager.system.dto.TableSchemaDto;
import com.leo.hbase.manager.system.service.ISysHbaseTableSchemaService;
import com.leo.hbase.manager.web.service.IMultiHBaseAdminService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;

/**
 * @author leojie 2020/11/29 11:13 上午
 */
@Controller
@RequestMapping("/system/sql")
public class SysHbaseTableSqlController extends SysHbaseBaseController {
    private String prefix = "system/sql";

    @Autowired
    private IMultiHBaseAdminService multiHBaseAdminService;

    @Autowired
    private ISysHbaseTableSchemaService sysHbaseTableSchemaService;


    @RequiresPermissions("hbase:sql:view")
    @GetMapping()
    public String sql() {
        return prefix + "/sql";
    }

    /**
     * 用SQL查询HBase表数据
     */
    @RequiresPermissions("hbase:sql:query")
    @PostMapping("/query")
    @ResponseBody
    public TableDataInfo query(@Validated QueryHBaseSqlDto hBaseSqlDto) {
        //todo fix query
        /*String hql = hBaseSqlDto.getHql();
        HBaseDataSet dataSet = null;
        String clusterCode = clusterCodeOfCurrentSession();

        String tableName = multiHBaseService.getTableNameFromHql(clusterCode, hql);
        if (StringUtils.isBlank(tableName)) {
            throw new HBaseOperationsException("表名不可为空");
        }
        if (!multiHBaseAdminService.tableIsExists(clusterCode, tableName)) {
            throw new HBaseOperationsException("HBase表[" + tableName + "]不存在");
        }
        HQLType hqlType = multiHBaseService.getHqlTypeFromHql(clusterCode, hql);
        SysHbaseTableSchema sysHbaseTableSchema = sysHbaseTableSchemaService.selectSysHbaseTableSchemaByTableName(clusterCode, tableName);
        TableSchemaDto tableSchemaDto = new TableSchemaDto().convertFor(sysHbaseTableSchema);
        HBaseTableSchema tableSchema = parseHBaseTableSchema(tableSchemaDto.getTableSchema());
        HBaseSqlContext.registerTableSchema(tableSchema);

        if (hqlType == HQLType.SELECT) {
            dataSet = multiHBaseService.select(clusterCode, hql);
        }
        if (hqlType == HQLType.PUT) {
            multiHBaseService.insert(clusterCode, hql);
        }
        if (hqlType == HQLType.DELETE) {
            multiHBaseService.delete(clusterCode, hql);
        }
        if (dataSet != null) {
            return getDataTable(dataSet.getRowSet());
        } else {
            return getDataTable(new ArrayList<>(0));
        }*/
        return getDataTable(new ArrayList<>(0));
    }
}
