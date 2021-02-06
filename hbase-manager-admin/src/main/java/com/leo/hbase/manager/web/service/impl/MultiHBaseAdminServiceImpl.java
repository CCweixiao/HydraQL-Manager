package com.leo.hbase.manager.web.service.impl;

import com.github.CCweixiao.HBaseAdminTemplate;
import com.github.CCweixiao.constant.HMHBaseConstant;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.hbtop.Record;
import com.github.CCweixiao.hbtop.RecordFilter;
import com.github.CCweixiao.hbtop.Summary;
import com.github.CCweixiao.hbtop.field.Field;
import com.github.CCweixiao.hbtop.mode.Mode;
import com.github.CCweixiao.model.FamilyDesc;
import com.github.CCweixiao.model.NamespaceDesc;
import com.github.CCweixiao.model.SnapshotDesc;
import com.github.CCweixiao.model.TableDesc;
import com.github.CCweixiao.util.SplitGoEnum;
import com.leo.hbase.manager.common.constant.HBasePropertyConstants;
import com.leo.hbase.manager.common.core.domain.Ztree;
import com.leo.hbase.manager.common.exception.BusinessException;
import com.leo.hbase.manager.common.utils.HBaseConfigUtils;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.common.utils.security.StrEnDeUtils;
import com.leo.hbase.manager.framework.util.ShiroUtils;
import com.leo.hbase.manager.system.domain.SysUserHbaseTable;
import com.leo.hbase.manager.system.mapper.SysUserHbaseTableMapper;
import com.leo.hbase.manager.web.hds.HBaseClusterDSHolder;
import com.leo.hbase.manager.web.service.IMultiHBaseAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leojie 2020/9/24 9:47 下午
 */
@Service
public class MultiHBaseAdminServiceImpl implements IMultiHBaseAdminService {
    @Autowired
    private SysUserHbaseTableMapper userHbaseTableMapper;

    @Override
    public NamespaceDesc getNamespaceDesc(String clusterCode, String namespaceName) {
        final String filterNamespacePrefix = HBaseConfigUtils.getFilterNamespacePrefix(clusterCode);
        if (StringUtils.isNotBlank(filterNamespacePrefix)) {
            if (namespaceName.startsWith(filterNamespacePrefix)) {
                throw new BusinessException("已配置过滤该命名空间的前缀");
            }
        }
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.getNamespaceDesc(namespaceName);
    }

    @Override
    public List<NamespaceDesc> listAllNamespaceDesc(String clusterCode) {
        final String filterNamespacePrefix = HBaseConfigUtils.getFilterNamespacePrefix(clusterCode);
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        final List<NamespaceDesc> namespaceDescList = hBaseTemplate.listNamespaceDesc();
        if (StringUtils.isNotBlank(filterNamespacePrefix)) {
            return namespaceDescList.stream().filter(namespaceDesc -> !namespaceDesc.getNamespaceName().startsWith(filterNamespacePrefix)).collect(Collectors.toList());
        }
        return namespaceDescList;
    }

    @Override
    public List<String> listAllNamespaceName(String clusterCode) {
        final String filterNamespacePrefix = HBaseConfigUtils.getFilterNamespacePrefix(clusterCode);
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        final List<String> namespaces = hBaseTemplate.listNamespaceNames();

        if (StringUtils.isNotBlank(filterNamespacePrefix)) {
            return namespaces.stream().filter(namespace -> !namespace.startsWith(filterNamespacePrefix)).collect(Collectors.toList());
        }
        return namespaces;
    }

    @Override
    public List<String> listAllTableNamesByNamespaceName(String clusterCode, String namespaceName) {
        final String filterNamespacePrefix = HBaseConfigUtils.getFilterNamespacePrefix(clusterCode);
        if (StringUtils.isNotBlank(filterNamespacePrefix)) {
            if (namespaceName.startsWith(filterNamespacePrefix)) {
                throw new BusinessException("已配置过滤该命名空间的前缀");
            }
        }
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.listTableNamesByNamespace(namespaceName);
    }

    @Override
    public List<Ztree> listNamespaceTree(String clusterCode, String namespaceName) {
        List<String> namespaceList = new ArrayList<>();
        if (StringUtils.isNotBlank(namespaceName)) {
            namespaceList.add(namespaceName);
        } else {
            namespaceList = listAllNamespaceName(clusterCode);
        }
        for (String namespace : namespaceList) {
            final List<String> tableList = listAllTableNamesByNamespaceName(clusterCode, namespace);
            for (String table : tableList) {
                Ztree ztree = new Ztree();
                //ztree.setId();
            }
        }
        //TODO 解析命名空间和表的结构
        return null;
    }

    @Override
    public boolean createNamespace(String clusterCode, NamespaceDesc namespace) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.createNamespace(namespace);
    }

    @Override
    public boolean deleteNamespace(String clusterCode, String namespace) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.deleteNamespace(namespace);
    }

    @Override
    public List<String> listAllTableName(String clusterCode, boolean checkAuth) {
        final String filterNamespacePrefix = HBaseConfigUtils.getFilterNamespacePrefix(clusterCode);
        final String filterTableNamePrefix = HBaseConfigUtils.getFilterTableNamePrefix(clusterCode);

        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        List<String> tableNames = hBaseTemplate.listTableNames();

        if (checkAuth) {
            Long userId = ShiroUtils.getUserId();
            final List<SysUserHbaseTable> sysUserHbaseTables = userHbaseTableMapper.selectSysUserHbaseTableListByUserAndClusterAlias(userId, clusterCode);
            if (sysUserHbaseTables != null && !sysUserHbaseTables.isEmpty()) {
                final List<String> authTableNames = sysUserHbaseTables.stream().map(SysUserHbaseTable::getTableName).collect(Collectors.toList());
                tableNames = tableNames.stream().filter(tableName -> authTableNames.contains(HMHBaseConstant.getFullTableName(tableName)))
                        .collect(Collectors.toList());
            }
        }

        if (StringUtils.isNotBlank(filterNamespacePrefix) && StringUtils.isNotBlank(filterTableNamePrefix)) {
            return tableNames.stream().filter(tableName -> {
                final String namespaceName = HMHBaseConstant.getNamespaceName(tableName);
                return !namespaceName.startsWith(filterNamespacePrefix);
            }).filter(tableName -> {
                String shortTableName = HMHBaseConstant.getFullTableName(tableName).split(HMHBaseConstant.TABLE_NAME_SPLIT_CHAR)[1];
                return !shortTableName.startsWith(filterTableNamePrefix);
            }).collect(Collectors.toList());

        } else if (StringUtils.isNotBlank(filterNamespacePrefix) && StringUtils.isBlank(filterTableNamePrefix)) {
            return tableNames.stream().filter(tableName -> {
                final String namespaceName = HMHBaseConstant.getNamespaceName(tableName);
                return !namespaceName.startsWith(filterNamespacePrefix);
            }).collect(Collectors.toList());

        } else if (StringUtils.isBlank(filterNamespacePrefix) && StringUtils.isNotBlank(filterTableNamePrefix)) {
            return tableNames.stream().filter(tableName -> {
                String shortTableName = HMHBaseConstant.getFullTableName(tableName).split(HMHBaseConstant.TABLE_NAME_SPLIT_CHAR)[1];
                return !shortTableName.startsWith(filterTableNamePrefix);
            }).collect(Collectors.toList());
        }
        return tableNames;
    }

    @Override
    public List<TableDesc> listAllTableDesc(String clusterCode, boolean checkAuth) {
        final String filterNamespacePrefix = HBaseConfigUtils.getFilterNamespacePrefix(clusterCode);
        final String filterTableNamePrefix = HBaseConfigUtils.getFilterTableNamePrefix(clusterCode);

        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        List<TableDesc> tableDescList = hBaseTemplate.listTableDesc();

        if (checkAuth) {
            Long userId = ShiroUtils.getUserId();
            final List<SysUserHbaseTable> sysUserHbaseTables = userHbaseTableMapper.selectSysUserHbaseTableListByUserAndClusterAlias(userId, clusterCode);
            if (sysUserHbaseTables != null && !sysUserHbaseTables.isEmpty()) {
                final List<String> authTableNames = sysUserHbaseTables.stream().map(SysUserHbaseTable::getTableName).collect(Collectors.toList());
                tableDescList = tableDescList.stream().filter(tableDesc -> authTableNames.contains(tableDesc.getFullTableName()))
                        .collect(Collectors.toList());
            }
        }

        if (StringUtils.isNotBlank(filterNamespacePrefix) && StringUtils.isNotBlank(filterTableNamePrefix)) {
            return tableDescList.stream().filter(tableDesc -> !tableDesc.getNamespaceName().startsWith(filterNamespacePrefix))
                    .filter(tableDesc -> {
                        String shortTableName = HMHBaseConstant.getFullTableName(tableDesc.getTableName()).split(HMHBaseConstant.TABLE_NAME_SPLIT_CHAR)[1];
                        return !shortTableName.startsWith(filterTableNamePrefix);
                    }).collect(Collectors.toList());

        } else if (StringUtils.isNotBlank(filterNamespacePrefix) && StringUtils.isBlank(filterTableNamePrefix)) {
            return tableDescList.stream().filter(tableDesc -> !tableDesc.getNamespaceName().startsWith(filterNamespacePrefix)).collect(Collectors.toList());

        } else if (StringUtils.isBlank(filterNamespacePrefix) && StringUtils.isNotBlank(filterTableNamePrefix)) {
            return tableDescList.stream().filter(tableDesc -> {
                String shortTableName = HMHBaseConstant.getFullTableName(tableDesc.getTableName()).split(HMHBaseConstant.TABLE_NAME_SPLIT_CHAR)[1];
                return !shortTableName.startsWith(filterTableNamePrefix);
            }).collect(Collectors.toList());
        }
        return tableDescList;
    }

    @Override
    public List<SnapshotDesc> listAllSnapshotDesc(String clusterCode) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        List<SnapshotDesc> snapshotDescList = hBaseTemplate.listSnapshots();

        Long userId = ShiroUtils.getUserId();
        final List<SysUserHbaseTable> sysUserHbaseTables = userHbaseTableMapper.selectSysUserHbaseTableListByUserAndClusterAlias(userId, clusterCode);
        if (sysUserHbaseTables != null && !sysUserHbaseTables.isEmpty()) {
            final List<String> authTableNames = sysUserHbaseTables.stream().map(SysUserHbaseTable::getTableName).collect(Collectors.toList());
            snapshotDescList = snapshotDescList.stream().filter(snapshotDesc -> authTableNames.contains(HMHBaseConstant.getFullTableName(snapshotDesc.getTableName())))
                    .collect(Collectors.toList());
        }
        return snapshotDescList;
    }

    @Override
    public boolean createTable(String clusterCode, TableDesc tableDesc) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        boolean res = hBaseTemplate.createTable(tableDesc);
        addUserTableRelation(tableDesc, clusterCode);
        return res;
    }

    @Override
    public boolean createTable(String clusterCode, TableDesc tableDesc, String startKey, String endKey, int numRegions, boolean isAsync) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        boolean res = hBaseTemplate.createTable(tableDesc, startKey, endKey, numRegions, isAsync);
        addUserTableRelation(tableDesc, clusterCode);
        return res;
    }

    @Override
    public boolean createTable(String clusterCode, TableDesc tableDesc, String[] splitKeys, boolean isAsync) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        boolean res = hBaseTemplate.createTable(tableDesc, splitKeys, isAsync);
        addUserTableRelation(tableDesc, clusterCode);
        return res;
    }

    @Override
    public boolean createSnapshot(String clusterCode, SnapshotDesc snapshotDesc) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.snapshot(snapshotDesc);
    }

    @Override
    public boolean removeSnapshot(String clusterCode, String snapshotName) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.deleteSnapshot(snapshotName);
    }

    @Override
    public boolean createTable(String clusterCode, TableDesc tableDesc, SplitGoEnum splitGoEnum, int numRegions, boolean isAsync) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.createTable(tableDesc, splitGoEnum, numRegions, isAsync);
    }

    @Override
    public boolean enableTable(String clusterCode, String tableName) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.enableTable(tableName, true);
    }

    @Override
    public boolean disableTable(String clusterCode, String tableName) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.disableTable(tableName, true);
    }

    @Override
    public boolean isTableDisabled(String clusterCode, String tableName) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.isTableDisabled(tableName);
    }

    @Override
    public boolean tableIsExists(String clusterCode, String tableName) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.tableExists(tableName);
    }

    @Override
    public boolean deleteTable(String clusterCode, String tableName) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);

        boolean res = hBaseTemplate.deleteTable(tableName);
        deleteUserTableRelation(tableName, clusterCode);
        return res;
    }

    @Override
    public boolean truncatePreserve(String clusterCode, String tableName) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.truncateTable(tableName, true);
    }

    @Override
    public TableDesc getTableDesc(String clusterCode, String tableName) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.getTableDesc(tableName);
    }

    @Override
    public List<FamilyDesc> getFamilyDesc(String clusterCode, String tableName) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.listFamilyDesc(tableName);
    }

    @Override
    public boolean addFamily(String clusterCode, String tableName, FamilyDesc familyDesc) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.addFamily(tableName, familyDesc);
    }

    @Override
    public boolean deleteFamily(String clusterCode, String tableName, String familyName) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.deleteFamily(tableName, familyName);
    }

    @Override
    public boolean modifyFamily(String clusterCode, String tableName, FamilyDesc familyDesc) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.modifyFamily(tableName, familyDesc);
    }

    @Override
    public boolean enableReplication(String clusterCode, String tableName, List<String> families) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.enableReplicationScope(tableName, families);
    }

    @Override
    public boolean disableReplication(String clusterCode, String tableName, List<String> families) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.disableReplicationScope(tableName, families);
    }

    @Override
    public boolean modifyTable(String clusterCode, TableDesc tableDesc) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        tableDesc.addProp(HBasePropertyConstants.LAST_UPDATE_BY, ShiroUtils.getLoginName());
        tableDesc.addProp(HBasePropertyConstants.LAST_UPDATE_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        return hBaseTemplate.modifyTableProps(tableDesc);
    }

    @Override
    public int totalHRegionServerNum(String clusterCode) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);

        return 5;
    }

    @Override
    public int totalNamespaceNum(String clusterCode) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.listNamespaceNames().size();
    }

    @Override
    public int totalTableNum(String clusterCode) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.listTableNames().size();
    }

    @Override
    public int totalSnapshotNum(String clusterCode) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        final List<SnapshotDesc> snapshots = hBaseTemplate.listSnapshots();
        if (snapshots == null || snapshots.isEmpty()) {
            return 0;
        } else {
            return snapshots.size();
        }
    }

    @Override
    public Summary refreshSummary(String clusterCode) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.refreshSummary();
    }

    @Override
    public List<Record> refreshRecords(String clusterCode, Mode currentMode, List<RecordFilter> filters, Field currentSortField, boolean ascendingSort) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.refreshRecords(currentMode, filters, currentSortField, ascendingSort);
    }


    @Transactional(rollbackFor = HBaseOperationsException.class)
    public void addUserTableRelation(TableDesc tableDesc, String clusterCode) {
        SysUserHbaseTable sysUserHbaseTable = new SysUserHbaseTable();
        sysUserHbaseTable.setUserId(ShiroUtils.getUserId());
        sysUserHbaseTable.setTableName(tableDesc.getFullTableName());
        sysUserHbaseTable.setTableId(StrEnDeUtils.encrypt(tableDesc.getFullTableName()));
        sysUserHbaseTable.setClusterAlias(clusterCode);
        sysUserHbaseTable.setNamespaceName(tableDesc.getNamespaceName());
        userHbaseTableMapper.insertSysUserHbaseTable(sysUserHbaseTable);
    }

    @Transactional(rollbackFor = HBaseOperationsException.class)
    public void deleteUserTableRelation(String tableName, String clusterCode) {
        tableName = HMHBaseConstant.getFullTableName(tableName);
        SysUserHbaseTable sysUserHbaseTable = new SysUserHbaseTable();
        sysUserHbaseTable.setUserId(ShiroUtils.getUserId());
        sysUserHbaseTable.setTableName(tableName);
        sysUserHbaseTable.setTableId(StrEnDeUtils.encrypt(tableName));
        sysUserHbaseTable.setClusterAlias(clusterCode);
        userHbaseTableMapper.deleteSysUserHbaseTable(sysUserHbaseTable);
    }

}
