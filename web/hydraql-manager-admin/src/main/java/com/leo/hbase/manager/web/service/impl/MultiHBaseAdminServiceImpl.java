package com.leo.hbase.manager.web.service.impl;

import com.hydraql.manager.core.hbase.SplitGoEnum;
import com.hydraql.manager.core.hbase.model.SnapshotDesc;
import com.hydraql.manager.core.hbase.schema.ColumnFamilyDesc;
import com.hydraql.manager.core.hbase.schema.HTableDesc;
import com.hydraql.manager.core.hbase.schema.NamespaceDesc;
import com.hydraql.manager.core.template.HydraQLTemplate;
import com.hydraql.manager.core.util.HConstants;
import com.leo.hbase.manager.common.enums.HBaseTableStatus;
import com.leo.hbase.manager.common.exception.BusinessException;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.framework.util.ShiroUtils;
import com.leo.hbase.manager.system.domain.SysHbaseTable;
import com.leo.hbase.manager.system.dto.SysHbaseClusterDto;
import com.leo.hbase.manager.system.mapper.SysHbaseUserTableMapper;
import com.leo.hbase.manager.system.service.ISysHbaseClusterService;
import com.leo.hbase.manager.web.hds.HBaseClusterDSConfig;
import com.leo.hbase.manager.web.service.IMultiHBaseAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static com.leo.hbase.manager.common.constant.HBasePropertyConstants.HBASE_TABLE_DISABLE_FLAG;
import static com.leo.hbase.manager.common.constant.HBasePropertyConstants.HBASE_TABLE_ENABLE_FLAG;

/**
 * @author leojie 2020/9/24 9:47 下午
 */
@Service
public class MultiHBaseAdminServiceImpl implements IMultiHBaseAdminService {
    @Autowired
    HBaseClusterDSConfig hBaseClusterDSConfig;

    @Autowired
    ISysHbaseClusterService sysHbaseClusterService;

    @Autowired
    private SysHbaseUserTableMapper userHbaseTableMapper;

    @Override
    public boolean testConnectionCluster(SysHbaseClusterDto sysHbaseClusterDto) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(sysHbaseClusterDto);
        List<String> namespaceNames = template.listNamespaceNames();
        return namespaceNames != null && !namespaceNames.isEmpty();
    }

    @Override
    public NamespaceDesc getNamespaceDesc(String clusterCode, String namespaceName) {
        final String filterNamespacePrefix = sysHbaseClusterService.getFilterNamespacePrefix(clusterCode);
        if (StringUtils.isNotBlank(filterNamespacePrefix)) {
            if (namespaceName.startsWith(filterNamespacePrefix)) {
                throw new BusinessException("已配置过滤该命名空间的前缀");
            }
        }
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.getNamespaceDesc(namespaceName);
    }

    @Override
    public List<NamespaceDesc> listAllNamespaceDesc(String clusterCode) {
        final String filterNamespacePrefix = sysHbaseClusterService.getFilterNamespacePrefix(clusterCode);
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        final List<NamespaceDesc> namespaceDescList = template.listNamespaceDesc();
        if (StringUtils.isNotBlank(filterNamespacePrefix)) {
            return namespaceDescList.stream().filter(namespaceDesc -> !namespaceDesc.getNamespaceName().startsWith(filterNamespacePrefix)).collect(Collectors.toList());
        }
        return namespaceDescList;
    }

    @Override
    public List<String> listAllNamespaceName(String clusterCode) {
        final String filterNamespacePrefix = sysHbaseClusterService.getFilterNamespacePrefix(clusterCode);
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        final List<String> namespaces = template.listNamespaceNames();

        if (StringUtils.isNotBlank(filterNamespacePrefix)) {
            return namespaces.stream().filter(namespace ->
                    !namespace.startsWith(filterNamespacePrefix)).collect(Collectors.toList());
        }
        return namespaces;
    }

    @Override
    public List<String> listAllTableNamesByNamespaceName(String clusterCode, String namespaceName) {
        final String filterNamespacePrefix = sysHbaseClusterService.getFilterNamespacePrefix(clusterCode);
        if (StringUtils.isNotBlank(filterNamespacePrefix)) {
            if (namespaceName.startsWith(filterNamespacePrefix)) {
                throw new BusinessException("已配置过滤该命名空间的前缀");
            }
        }
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.listTableNamesByNamespace(namespaceName);
    }

    @Override
    public boolean createNamespace(String clusterCode, NamespaceDesc namespace) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.createNamespace(namespace);
    }

    @Override
    public boolean deleteNamespace(String clusterCode, String namespace) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.deleteNamespace(namespace);
    }

    @Override
    public List<String> listAllTableName(String clusterCode) {
        final String filterNamespacePrefix = sysHbaseClusterService.getFilterNamespacePrefix(clusterCode);
        final String filterTableNamePrefix = sysHbaseClusterService.getFilterTableNamePrefix(clusterCode);

        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        List<String> tableNames = template.listTableNames();
        return tableNames.stream().filter(tableName -> {
            final String namespaceName = HConstants.getNamespaceName(tableName);
            if (StringUtils.isNotBlank(filterNamespacePrefix)) {
                return !namespaceName.startsWith(filterNamespacePrefix);
            }
            return true;
        }).filter(tableName -> {
            String shortTableName = HConstants.getFullTableName(tableName).split(HConstants.TABLE_NAME_SPLIT_CHAR)[1];
            if (StringUtils.isNotBlank(filterTableNamePrefix)) {
                return !shortTableName.startsWith(filterTableNamePrefix);
            }
            return true;
        }).collect(Collectors.toList());
    }

    @Override
    public Future<SysHbaseTable> fetchSysHbaseTableFromCluster(String clusterId, String tableName) {
        SysHbaseTable sysHbaseTable = new SysHbaseTable();
        sysHbaseTable.setNamespaceName(HConstants.getNamespaceName(tableName));
        sysHbaseTable.setTableName(tableName);
        sysHbaseTable.setClusterId(clusterId);
        boolean tableDisabled = this.isTableDisabled(clusterId, tableName);
        if (tableDisabled) {
            sysHbaseTable.setDisableFlag(HBASE_TABLE_DISABLE_FLAG);
            sysHbaseTable.setStatus(HBaseTableStatus.DISABLED.getCode());
        } else {
            sysHbaseTable.setDisableFlag(HBASE_TABLE_ENABLE_FLAG);
            sysHbaseTable.setStatus(HBaseTableStatus.ONLINE.getCode());
        }
        sysHbaseTable.setCreateBy(ShiroUtils.getLoginName());
        sysHbaseTable.setRemark("同步集群表生成元数据记录");
        return new AsyncResult<>(sysHbaseTable);
    }

    @Override
    public List<HTableDesc> listAllHTableDesc(String clusterCode) {
        final String filterNamespacePrefix = sysHbaseClusterService.getFilterNamespacePrefix(clusterCode);
        final String filterTableNamePrefix = sysHbaseClusterService.getFilterTableNamePrefix(clusterCode);

        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        List<HTableDesc> tableNames = template.listTableDesc(true);
        return tableNames.stream().filter(tableDesc -> {
            final String namespaceName = HConstants.getNamespaceName(tableDesc.getName());
            if (StringUtils.isNotBlank(filterNamespacePrefix)) {
                return !namespaceName.startsWith(filterNamespacePrefix);
            }
            return true;
        }).filter(tableDesc -> {
            String shortTableName = HConstants.getFullTableName(tableDesc.getName())
                    .split(HConstants.TABLE_NAME_SPLIT_CHAR)[1];
            if (StringUtils.isNotBlank(filterTableNamePrefix)) {
                return !shortTableName.startsWith(filterTableNamePrefix);
            }
            return true;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SnapshotDesc> listAllSnapshotDesc(String clusterCode) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.listAllSnapshotDesc();
    }

    @Override
    public boolean createTable(String clusterCode, HTableDesc tableDesc) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.createTable(tableDesc);
    }

    @Override
    public boolean createTable(String clusterCode, HTableDesc tableDesc, String startKey, String endKey, int numRegions, boolean isAsync) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.createTable(tableDesc, startKey, endKey, numRegions, isAsync);

    }

    @Override
    public boolean createTable(String clusterCode, HTableDesc tableDesc, String[] splitKeys, boolean isAsync) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.createTable(tableDesc, splitKeys, isAsync);
    }

    @Override
    public boolean createSnapshot(String clusterCode, SnapshotDesc snapshotDesc) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.createSnapshot(snapshotDesc);
    }

    @Override
    public boolean removeSnapshot(String clusterCode, String snapshotName) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.removeSnapshot(snapshotName);
    }

    @Override
    public boolean createTable(String clusterCode, HTableDesc tableDesc, SplitGoEnum splitGoEnum, int numRegions, boolean isAsync) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.createTable(tableDesc, splitGoEnum, numRegions, isAsync) ;
    }

    @Override
    public boolean enableTable(String clusterCode, String tableName) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.enableTable(tableName);
    }

    @Override
    public boolean disableTable(String clusterCode, String tableName) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.disableTable(tableName);
    }

    @Override
    public boolean isTableDisabled(String clusterCode, String tableName) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.isTableDisabled(tableName);
    }

    @Override
    public boolean tableIsExists(String clusterCode, String tableName) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.tableExists(tableName);
    }

    @Override
    public boolean deleteTable(String clusterCode, String tableName) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.deleteTable(tableName);
    }

    @Override
    public boolean truncatePreserve(String clusterCode, String tableName) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.truncatePreserve(tableName);
    }

    @Override
    public HTableDesc getHTableDesc(String clusterCode, String tableName) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.getHTableDesc(tableName);
    }

    @Override
    public List<ColumnFamilyDesc> getColumnFamilyDesc(String clusterCode, String tableName) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.getColumnFamilyDesc(tableName);
    }

    @Override
    public boolean addFamily(String clusterCode, String tableName, ColumnFamilyDesc familyDesc) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.addFamily(tableName, familyDesc);
    }

    @Override
    public boolean deleteFamily(String clusterCode, String tableName, String familyName) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.deleteFamily(tableName, familyName);
    }

    @Override
    public boolean modifyFamily(String clusterCode, String tableName, ColumnFamilyDesc familyDesc) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.modifyFamily(tableName, familyDesc);
    }

    @Override
    public boolean enableReplication(String clusterCode, String tableName, List<String> families) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.enableReplication(tableName, families);
    }

    @Override
    public boolean disableReplication(String clusterCode, String tableName, List<String> families) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.disableReplication(tableName, families);
    }

    @Override
    public boolean modifyTable(String clusterCode, HTableDesc tableDesc) {
        // todo modifyTable
        return true;
    }

    @Override
    public boolean modifyTableProps(String clusterCode, HTableDesc tableDesc) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.modifyTableProps(tableDesc);
    }

    @Override
    public int totalHRegionServerNum(String clusterCode) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);

        return 5;
    }

    @Override
    public int totalNamespaceNum(String clusterCode) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.listNamespaceNames().size();
    }

    @Override
    public int totalTableNum(String clusterCode) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        return template.listTableNames().size();
    }

    @Override
    public int totalSnapshotNum(String clusterCode) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        final List<SnapshotDesc> snapshots = template.listAllSnapshotDesc();
        if (snapshots == null || snapshots.isEmpty()) {
            return 0;
        } else {
            return snapshots.size();
        }
    }

}
