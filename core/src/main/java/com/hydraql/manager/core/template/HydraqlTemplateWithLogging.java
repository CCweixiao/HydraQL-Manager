package com.hydraql.manager.core.template;

import com.hydraql.manager.core.hbase.SplitGoEnum;
import com.hydraql.manager.core.hbase.model.Result;
import com.hydraql.manager.core.hbase.model.SnapshotDesc;
import com.hydraql.manager.core.hbase.schema.ColumnFamilyDesc;
import com.hydraql.manager.core.hbase.schema.HTableDesc;
import com.hydraql.manager.core.hbase.schema.NamespaceDesc;
import com.hydraql.manager.core.hbase.model.HBaseRowData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author leojie 2024/1/26 10:41
 */
public class HydraqlTemplateWithLogging implements HydraqlTemplate {
    private static final Logger LOG = LoggerFactory.getLogger(HydraqlTemplateWithLogging.class);

    private final HydraqlTemplate template;

    HydraqlTemplateWithLogging(HydraqlTemplate template) {
        this.template = template;
    }

    @Override
    public boolean tableExists(String tableName) {
        return template.tableExists(tableName);
    }

    @Override
    public boolean createNamespace(NamespaceDesc namespaceDesc) {
        return template.createNamespace(namespaceDesc);
    }

    @Override
    public boolean namespaceIsExists(String namespaceName) {
        return template.namespaceIsExists(namespaceName);
    }

    @Override
    public boolean deleteNamespace(String namespaceName) {
        return template.deleteNamespace(namespaceName);
    }

    @Override
    public List<NamespaceDesc> listNamespaceDesc() {
        return template.listNamespaceDesc();
    }

    @Override
    public NamespaceDesc getNamespaceDesc(String namespaceName) {
        return template.getNamespaceDesc(namespaceName);
    }

    @Override
    public List<String> listNamespaceNames() {
        return template.listNamespaceNames();
    }

    @Override
    public boolean createTable(HTableDesc tableDesc) {
        return template.createTable(tableDesc);
    }

    @Override
    public boolean createTable(HTableDesc tableDesc, String startKey, String endKey, int numRegions, boolean isAsync) {
        return template.createTable(tableDesc, startKey, endKey, numRegions, isAsync);
    }

    @Override
    public boolean createTable(HTableDesc tableDesc, String[] splitKeys, boolean isAsync) {
        return template.createTable(tableDesc, splitKeys, isAsync);
    }

    @Override
    public boolean createTable(HTableDesc tableDesc, SplitGoEnum splitGoEnum, int numRegions, boolean isAsync) {
        return template.createTable(tableDesc, splitGoEnum, numRegions, isAsync);
    }

    @Override
    public List<HTableDesc> listTableDesc(boolean includeSysTables) {
        return template.listTableDesc(includeSysTables);
    }

    @Override
    public List<String> listTableNames() {
        return template.listTableNames();
    }

    @Override
    public List<String> listTableNamesByNamespace(String namespaceName) {
        return template.listTableNamesByNamespace(namespaceName);
    }

    @Override
    public boolean enableTable(String tableName) {
        return template.enableTable(tableName);
    }

    @Override
    public boolean disableTable(String tableName) {
        return template.disableTable(tableName);
    }

    @Override
    public boolean deleteTable(String tableName) {
        return template.deleteTable(tableName);
    }

    @Override
    public boolean truncatePreserve(String tableName) {
        return template.truncatePreserve(tableName);
    }

    @Override
    public HTableDesc getHTableDesc(String tableName) {
        return template.getHTableDesc(tableName);
    }

    @Override
    public List<ColumnFamilyDesc> getColumnFamilyDesc(String tableName) {
        return template.getColumnFamilyDesc(tableName);
    }

    @Override
    public boolean addFamily(String tableName, ColumnFamilyDesc familyDesc) {
        return template.addFamily(tableName, familyDesc);
    }

    @Override
    public boolean deleteFamily(String tableName, String familyName) {
        return template.deleteFamily(tableName, familyName);
    }

    @Override
    public boolean modifyFamily(String tableName, ColumnFamilyDesc familyDesc) {
        return template.modifyFamily(tableName, familyDesc);
    }

    @Override
    public boolean enableReplication(String tableName, List<String> families) {
        return template.enableReplication(tableName, families);
    }

    @Override
    public boolean disableReplication(String tableName, List<String> families) {
        return template.disableReplication(tableName, families);
    }

    @Override
    public boolean modifyTable(HTableDesc tableDesc) {
        return template.modifyTable(tableDesc);
    }

    @Override
    public boolean modifyTableProps(HTableDesc tableDesc) {
        return template.modifyTableProps(tableDesc);
    }

    @Override
    public int totalHRegionServerNum() {
        return template.totalHRegionServerNum();
    }

    @Override
    public boolean isTableDisabled(String tableName) {
        return template.isTableDisabled(tableName);
    }

    @Override
    public boolean tableIsExists(String tableName) {
        return template.tableIsExists(tableName);
    }

    @Override
    public boolean createSnapshot(SnapshotDesc snapshotDesc) {
        return template.createSnapshot(snapshotDesc);
    }

    @Override
    public boolean removeSnapshot(String snapshotName) {
        return template.removeSnapshot(snapshotName);
    }

    @Override
    public List<SnapshotDesc> listAllSnapshotDesc() {
        return template.listAllSnapshotDesc();
    }

    @Override
    public boolean shellSessionIsConnected() {
        return template.shellSessionIsConnected();
    }

    @Override
    public Result executeShellCommand(String command) {
        return template.executeShellCommand(command);
    }

    @Override
    public List<String> getAllShellCommands() {
        return template.getAllShellCommands();
    }

    @Override
    public HBaseRowData getRow(String tableName, String rowKey) {
        return template.getRow(tableName, rowKey);
    }

    @Override
    public List<String> showVirtualTables(String hql) {
        return template.showVirtualTables(hql);
    }

    @Override
    public String showCreateVirtualTable(String hql) {
        return template.showCreateVirtualTable(hql);
    }

    @Override
    public void createVirtualTable(String hql) {
        template.createVirtualTable(hql);
    }

    @Override
    public void dropVirtualTable(String hql) {
        template.dropVirtualTable(hql);
    }

    @Override
    public void upsert(String hql) {
        template.upsert(hql);
    }

    @Override
    public void delete(String hql) {
        template.delete(hql);
    }
}
