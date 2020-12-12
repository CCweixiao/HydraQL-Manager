package com.leo.hbase.manager.web.controller.system;

import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.model.HQLType;
import com.github.CCweixiao.model.TableDesc;
import com.github.CCwexiao.dsl.client.HBaseCellResult;
import com.github.CCwexiao.dsl.config.HBaseTableConfig;
import com.github.CCwexiao.dsl.util.TreeUtil;
import com.leo.hbase.manager.common.core.page.TableDataInfo;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.system.domain.SysHbaseTableCellResult;
import com.leo.hbase.manager.system.domain.SysHbaseTableData;
import com.leo.hbase.manager.system.dto.QueryHBaseSqlDto;
import com.leo.hbase.manager.system.dto.TableSchemaDto;
import com.leo.hbase.manager.web.service.IMultiHBaseAdminService;
import com.leo.hbase.manager.web.service.IMultiHBaseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

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
    private IMultiHBaseService multiHBaseService;


    @RequiresPermissions("system:sql:view")
    @GetMapping()
    public String sql() {
        return prefix + "/sql";
    }

    /**
     * 用SQL查询HBase表数据
     */
    @RequiresPermissions("system:sql:query")
    @PostMapping("/query")
    @ResponseBody
    public TableDataInfo query(@Validated QueryHBaseSqlDto hBaseSqlDto) {
        String hql = hBaseSqlDto.getHql();
        List<List<HBaseCellResult>> hbaseCellResultList = new ArrayList<>();
        String clusterCode = clusterCodeOfCurrentSession();
        String tableName = TreeUtil.parseTableName(hql);
        if (StringUtils.isBlank(tableName)) {
            throw new HBaseOperationsException("表名不可为空");
        }
        if (!multiHBaseAdminService.tableIsExists(clusterCode, tableName)) {
            throw new HBaseOperationsException("HBase表[" + tableName + "]不存在");
        }
        HQLType hqlType = TreeUtil.parseHQLType(hql);
        final TableDesc tableDesc = multiHBaseAdminService.getTableDesc(clusterCode, tableName);
        TableSchemaDto tableSchemaDto = new TableSchemaDto().convertFor(tableDesc);

        HBaseTableConfig tableConfig = parseHBaseConfigFromTableSchema(tableSchemaDto.getTableSchema());

        if (hqlType == HQLType.SELECT) {
            hbaseCellResultList = multiHBaseService.select(clusterCode, tableConfig, hql);
        }
        if (hqlType == HQLType.PUT) {
            multiHBaseService.insert(clusterCode, tableConfig, hql);
        }
        if (hqlType == HQLType.DELETE) {
            multiHBaseService.delete(clusterCode, tableConfig, hql);
        }
        final List<SysHbaseTableCellResult> sysHbaseTableCellResultList = hbaseCellResultList.stream().map(results -> {
            List<SysHbaseTableCellResult> cellResultList = new ArrayList<>();
            results.forEach(result -> {
                SysHbaseTableCellResult cellResult = new SysHbaseTableCellResult();
                cellResult.setRowKey(result.getRowKey());
                cellResult.setColumnName(result.getColumnName());
                cellResult.setTsDate(result.getTsDate());
                cellResult.setValue(result.getValue());
                cellResultList.add(cellResult);
            });
            return cellResultList;
        }).flatMap(Collection::stream).collect(Collectors.toList());
        return getDataTable(sysHbaseTableCellResultList);
    }
}
