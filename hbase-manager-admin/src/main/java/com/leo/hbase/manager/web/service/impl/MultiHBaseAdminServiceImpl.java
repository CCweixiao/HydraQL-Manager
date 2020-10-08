package com.leo.hbase.manager.web.service.impl;

import com.github.CCweixiao.HBaseAdminTemplate;
import com.github.CCweixiao.model.FamilyDesc;
import com.github.CCweixiao.model.NamespaceDesc;
import com.github.CCweixiao.model.TableDesc;
import com.github.CCweixiao.util.SplitGoEnum;
import com.leo.hbase.manager.common.constant.HBasePropertyConstants;
import com.leo.hbase.manager.framework.util.ShiroUtils;
import com.leo.hbase.manager.web.hds.HBaseClusterDSHolder;
import com.leo.hbase.manager.web.service.IMultiHBaseAdminService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leojie 2020/9/24 9:47 下午
 */
@Service
public class MultiHBaseAdminServiceImpl implements IMultiHBaseAdminService {
    @Override
    public NamespaceDesc getNamespaceDesc(String clusterCode, String namespaceName) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.getNamespaceDesc(namespaceName);
    }

    @Override
    public List<NamespaceDesc> listAllNamespaceDesc(String clusterCode) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.listNamespaceDesc();
    }

    @Override
    public List<String> listAllNamespaceName(String clusterCode) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.listNamespaces();
    }

    @Override
    public List<String> listAllTableNamesByNamespaceName(String clusterCode, String namespaceName) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.listTableNamesByNamespace(namespaceName);
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
    public List<String> listAllTableName(String clusterCode) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.listTableNames();
    }

    @Override
    public List<TableDesc> listAllTableDesc(String clusterCode) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.listTableDesc();
    }

    @Override
    public boolean createTable(String clusterCode, TableDesc tableDesc) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.createTable(tableDesc);
    }

    @Override
    public boolean createTable(String clusterCode, TableDesc tableDesc, String startKey, String endKey, int numRegions, boolean isAsync) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.createTable(tableDesc, startKey, endKey, numRegions, isAsync);
    }

    @Override
    public boolean createTable(String clusterCode, TableDesc tableDesc, String[] splitKeys, boolean isAsync) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.createTable(tableDesc, splitKeys, isAsync);
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
        return hBaseTemplate.deleteTable(tableName);
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
}
