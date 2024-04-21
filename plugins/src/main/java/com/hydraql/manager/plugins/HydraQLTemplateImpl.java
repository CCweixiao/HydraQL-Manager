package com.hydraql.manager.plugins;

import com.hydraql.adapter.schema.BaseColumnFamilyDesc;
import com.hydraql.adapter.schema.BaseHTableDesc;
import com.hydraql.common.model.data.HBaseRowData;
import com.hydraql.common.query.GetRowParam;
import com.hydraql.manager.core.conf.HydraQLHBaseConfiguration;
import com.hydraql.manager.core.hbase.SplitGoEnum;
import com.hydraql.manager.core.hbase.model.Result;
import com.hydraql.manager.core.hbase.model.SnapshotDesc;
import com.hydraql.manager.core.hbase.schema.ColumnFamilyDesc;
import com.hydraql.manager.core.hbase.schema.HTableDesc;
import com.hydraql.manager.core.hbase.schema.NamespaceDesc;
import com.hydraql.manager.core.template.HydraQLTemplate;
import com.hydraql.shell.HBaseShellCommands;
import com.hydraql.shell.HBaseShellSession;
import com.hydraql.shell.HBaseShellSessionManager;
import com.hydraql.template.HBaseAdminTemplate;
import com.hydraql.template.HBaseSqlTemplate;
import com.hydraql.template.HBaseTableTemplate;
import org.apache.hadoop.conf.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author leojie 2024/1/25 16:20
 */
public class HydraQLTemplateImpl implements HydraQLTemplate {

    private final HBaseAdminTemplate adminTemplate;
    private final HBaseTableTemplate tableTemplate;
    private final HBaseSqlTemplate hqlTemplate;

    private final HydraQLHBaseConfiguration conf;

    public static HydraQLTemplateImpl createInstance(HydraQLHBaseConfiguration conf) {
        return new HydraQLTemplateImpl(conf);
    }

    public HydraQLTemplateImpl(HydraQLHBaseConfiguration conf) {
        this.conf = conf;
        Configuration hbaseConf = new Configuration();
        conf.toMap().forEach((k, v) -> hbaseConf.set(k, v == null ? "" : v.toString()));
        adminTemplate = HBaseAdminTemplate.of(hbaseConf);
        tableTemplate = HBaseTableTemplate.of(hbaseConf);
        hqlTemplate = HBaseSqlTemplate.of(hbaseConf);
    }

    @Override
    public boolean tableExists(String tableName) {
        return adminTemplate.tableExists(tableName);
    }

    @Override
    public boolean createNamespace(NamespaceDesc namespaceDesc) {
        return adminTemplate.createNamespace(convertTo(namespaceDesc), false);
    }

    @Override
    public boolean namespaceIsExists(String namespaceName) {
        return adminTemplate.namespaceIsExists(namespaceName);
    }

    @Override
    public boolean deleteNamespace(String namespaceName) {
        return adminTemplate.deleteNamespace(namespaceName, false);
    }

    @Override
    public List<NamespaceDesc> listNamespaceDesc() {
        List<com.hydraql.common.model.NamespaceDesc> namespaceDescList = adminTemplate.listNamespaceDesc();
        return namespaceDescList.stream().map(this::convertFrom).collect(Collectors.toList());
    }

    @Override
    public NamespaceDesc getNamespaceDesc(String namespaceName) {
        com.hydraql.common.model.NamespaceDesc namespaceDesc = adminTemplate.getNamespaceDesc(namespaceName);
        return convertFrom(namespaceDesc);
    }

    @Override
    public List<String> listNamespaceNames() {
        return adminTemplate.listNamespaceNames();
    }

    @Override
    public boolean createTable(HTableDesc tableDesc) {
        return adminTemplate.createTable(convertTo(tableDesc));
    }

    @Override
    public boolean createTable(HTableDesc tableDesc, String startKey, String endKey, int numRegions, boolean isAsync) {
        return adminTemplate.createTable(convertTo(tableDesc), startKey, endKey, numRegions, isAsync);
    }

    @Override
    public boolean createTable(HTableDesc tableDesc, String[] splitKeys, boolean isAsync) {
        return adminTemplate.createTable(convertTo(tableDesc), splitKeys, isAsync);
    }

    @Override
    public boolean createTable(HTableDesc tableDesc, SplitGoEnum splitGoEnum, int numRegions, boolean isAsync) {
        return adminTemplate.createTable(convertTo(tableDesc),
                com.hydraql.common.util.SplitGoEnum.getSplitGoEnum(splitGoEnum.getSplitGo()), numRegions, isAsync);
    }

    @Override
    public List<HTableDesc> listTableDesc(boolean includeSysTables) {
        List<com.hydraql.adapter.schema.HTableDesc> tableDescList = adminTemplate.listTableDesc(includeSysTables);
        if (tableDescList.isEmpty()) {
            return new ArrayList<>(0);
        }
        return tableDescList.stream().map(this::convertFrom).collect(Collectors.toList());
    }

    @Override
    public List<String> listTableNames() {
        return adminTemplate.listTableNames();
    }

    @Override
    public List<String> listTableNamesByNamespace(String namespaceName) {
        return adminTemplate.listTableNamesByNamespace(namespaceName);
    }

    @Override
    public boolean enableTable(String tableName) {
        return adminTemplate.enableTable(tableName, true);
    }

    @Override
    public boolean disableTable(String tableName) {
        return adminTemplate.disableTable(tableName, true);
    }

    @Override
    public boolean deleteTable(String tableName) {
        return adminTemplate.deleteTable(tableName, true);
    }

    @Override
    public boolean truncatePreserve(String tableName) {
        return adminTemplate.truncateTableAsync(tableName, true);
    }

    @Override
    public HTableDesc getHTableDesc(String tableName) {
        return convertFrom(adminTemplate.getTableDesc(tableName));
    }

    @Override
    public List<ColumnFamilyDesc> getColumnFamilyDesc(String tableName) {
        HTableDesc tableDesc = getHTableDesc(tableName);
        return tableDesc.getColumnFamilyDescList();
    }

    @Override
    public boolean addFamily(String tableName, ColumnFamilyDesc familyDesc) {
        com.hydraql.adapter.schema.BaseColumnFamilyDesc cf = convertTo(familyDesc);
        return adminTemplate.addFamilyAsync(tableName, (com.hydraql.adapter.schema.ColumnFamilyDesc) cf);
    }

    @Override
    public boolean deleteFamily(String tableName, String familyName) {
        return adminTemplate.deleteFamilyAsync(tableName, familyName);
    }

    @Override
    public boolean modifyFamily(String tableName, ColumnFamilyDesc familyDesc) {
        com.hydraql.adapter.schema.BaseColumnFamilyDesc cf = convertTo(familyDesc);
        return adminTemplate.modifyFamilyAsync(tableName, (com.hydraql.adapter.schema.ColumnFamilyDesc) cf);
    }

    @Override
    public boolean enableReplication(String tableName, List<String> families) {
        return adminTemplate.enableReplicationScopeAsync(tableName, families);
    }

    @Override
    public boolean disableReplication(String tableName, List<String> families) {
        return adminTemplate.disableReplicationScopeAsync(tableName, families);
    }

    @Override
    public boolean modifyTable(HTableDesc tableDesc) {
        // todo modifyTable
        return true;
    }

    @Override
    public boolean modifyTableProps(HTableDesc tableDesc) {
        // todo 完善相关API
        // return adminTemplate.modifyTablePropsAsync(tableDesc.getName(), tableDesc.getConfiguration());
        return true;
    }

    @Override
    public int totalHRegionServerNum() {
        return 5;
    }

    @Override
    public boolean isTableDisabled(String tableName) {
        return adminTemplate.isTableDisabled(tableName);
    }

    @Override
    public boolean tableIsExists(String tableName) {
        return adminTemplate.tableExists(tableName);
    }

    @Override
    public boolean createSnapshot(SnapshotDesc snapshotDesc) {
        return adminTemplate.snapshotAsync(convertTo(snapshotDesc));
    }

    @Override
    public boolean removeSnapshot(String snapshotName) {
        return adminTemplate.deleteSnapshot(snapshotName);
    }

    @Override
    public List<SnapshotDesc> listAllSnapshotDesc() {
        List<com.hydraql.common.model.SnapshotDesc> snapshotDescList = adminTemplate.listSnapshots();
        return snapshotDescList.stream().map(this::convertFrom).collect(Collectors.toList());
    }

    @Override
    public boolean shellSessionIsConnected() {
        HBaseShellSession shellSession = HBaseShellSessionManager.getHBaseShellSession(this.getConf().toProp());
        return shellSession.isSessionConnected();
    }

    @Override
    public Result executeShellCommand(String command) {
        HBaseShellSession shellSession = HBaseShellSessionManager.getHBaseShellSession(this.getConf().toProp());
        com.hydraql.shell.Result result = shellSession.execute(command);
        return Result.of(result.isSuccess(), result.getResult());
    }

    @Override
    public List<String> getAllShellCommands() {
        try {
            Set<String> allCommands = HBaseShellCommands.getAllCommands();
            return new ArrayList<>(allCommands);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public com.hydraql.manager.core.hbase.model.HBaseRowData getRow(String tableName, String rowKey) {
        HBaseRowData rowData = tableTemplate.getRow(tableName, GetRowParam.of(rowKey).build());
        com.hydraql.manager.core.hbase.model.HBaseRowData data = new com.hydraql.manager.core.hbase.model.HBaseRowData(rowKey);
        rowData.getColDataContainer().forEach((k, v) -> {
            data.setData(k, v.getValue());
        });
        return data;
    }

    @Override
    public List<String> showVirtualTables(String hql) {
        return hqlTemplate.showVirtualTables(hql);
    }

    @Override
    public String showCreateVirtualTable(String hql) {
        return hqlTemplate.showCreateVirtualTable(hql);
    }

    @Override
    public void createVirtualTable(String hql) {
        hqlTemplate.createVirtualTable(hql);
    }

    @Override
    public void dropVirtualTable(String hql) {
        hqlTemplate.dropVirtualTable(hql);
    }

    @Override
    public void upsert(String hql) {
        hqlTemplate.insert(hql);
    }

    @Override
    public void delete(String hql) {
        hqlTemplate.delete(hql);
    }

    private com.hydraql.common.model.SnapshotDesc convertTo(SnapshotDesc sd) {
        com.hydraql.common.model.SnapshotDesc snapshotDesc = new com.hydraql.common.model.SnapshotDesc();
        snapshotDesc.setSnapshotName(sd.getSnapshotName());
        snapshotDesc.setTableName(sd.getTableName());
        snapshotDesc.setCreateTime(sd.getCreateTime());
        return snapshotDesc;
    }

    private com.hydraql.common.model.NamespaceDesc convertTo(NamespaceDesc ns) {
        com.hydraql.common.model.NamespaceDesc namespaceDesc = new com.hydraql.common.model.NamespaceDesc();
        namespaceDesc.setNamespaceName(ns.getNamespaceName());
        namespaceDesc.setNamespaceProps(ns.getNamespaceProps());
        return namespaceDesc;
    }

    private com.hydraql.adapter.schema.HTableDesc convertTo(HTableDesc hd) {
        List<com.hydraql.adapter.schema.BaseColumnFamilyDesc> cfList =
                new ArrayList<>(hd.getColumnFamilyDescList().size());
        for (ColumnFamilyDesc cf : hd.getColumnFamilyDescList()) {
            cfList.add(convertTo(cf));
        }

        BaseHTableDesc.Builder<com.hydraql.adapter.schema.HTableDesc> builder =
                com.hydraql.adapter.schema.HTableDesc.newBuilder(hd.getName())
                        .maxFileSize(hd.getMaxFileSize())
                        .readOnly(hd.isReadOnly())
                        .memStoreFlushSize(hd.getMemStoreFlushSize())
                        .compactionEnabled(hd.isCompactionEnabled())
                        .regionSplitPolicyClassName(hd.getRegionSplitPolicyClassName());
        for (BaseColumnFamilyDesc familyDesc : cfList) {
            builder.addFamilyDesc(familyDesc);
        }

        if (!hd.getConfiguration().isEmpty()) {
            hd.getConfiguration().forEach(builder::setConfiguration);
        }
        if (!hd.getValues().isEmpty()) {
            hd.getValues().forEach(builder::setValue);
        }
        return builder.build();
    }

    private com.hydraql.adapter.schema.BaseColumnFamilyDesc convertTo(ColumnFamilyDesc cf) {
        BaseColumnFamilyDesc.Builder<com.hydraql.adapter.schema.ColumnFamilyDesc> builder =
                com.hydraql.adapter.schema.ColumnFamilyDesc.newBuilder(cf.getName())
                .replicationScope(cf.getReplicationScope())
                .maxVersions(cf.getMaxVersions())
                .minVersions(cf.getMinVersions())
                .compressionType(cf.getCompressionType())
                .storagePolicy(cf.getStoragePolicy())
                .bloomFilterType(cf.getBloomFilterType())
                .timeToLive(cf.getTimeToLive())
                .blockSize(cf.getBlockSize())
                .blockCacheEnabled(cf.isBlockCacheEnabled())
                .inMemory(cf.isInMemory())
                .keepDeletedCells(cf.getKeepDeletedCells())
                .dataBlockEncoding(cf.getDataBlockEncoding())
                .cacheDataOnWrite(cf.isCacheDataOnWrite())
                .cacheDataInL1(cf.isCacheDataInL1())
                .cacheIndexesOnWrite(cf.isCacheIndexesOnWrite())
                .cacheBloomsOnWrite(cf.isCacheBloomsOnWrite())
                .evictBlocksOnClose(cf.isEvictBlocksOnClose())
                .prefetchBlocksOnOpen(cf.isPrefetchBlocksOnOpen());
        cf.getConfiguration().forEach(builder::setConfiguration);
        cf.getValues().forEach(builder::setValue);
        return builder.build();
    }

    private SnapshotDesc convertFrom(com.hydraql.common.model.SnapshotDesc sd) {
        SnapshotDesc snapshotDesc = new SnapshotDesc();
        snapshotDesc.setSnapshotName(sd.getSnapshotName());
        snapshotDesc.setTableName(sd.getTableName());
        snapshotDesc.setCreateTime(sd.getCreateTime());
        return snapshotDesc;
    }

    private NamespaceDesc convertFrom(com.hydraql.common.model.NamespaceDesc ns) {
        NamespaceDesc namespaceDesc = new NamespaceDesc();
        namespaceDesc.setNamespaceName(ns.getNamespaceName());
        namespaceDesc.setNamespaceProps(ns.getNamespaceProps());
        return namespaceDesc;
    }

    private HTableDesc convertFrom(com.hydraql.adapter.schema.HTableDesc hd) {
        return HTableDesc.of(hd.getTableNameWithNamespace())
                .maxFileSize(hd.getMaxFileSize())
                .readOnly(hd.isReadOnly())
                .memStoreFlushSize(hd.getMemStoreFlushSize())
                .compactionEnabled(hd.isCompactionEnabled())
                .regionSplitPolicyClassName(hd.getRegionSplitPolicyClassName())
                .setConfiguration(hd.getConfiguration())
                .setValue(hd.getValues())
                .addFamilyDesc(convertFrom(hd.getColumnFamilyDescList()))
                .build();
    }

    private List<ColumnFamilyDesc> convertFrom(List<com.hydraql.adapter.schema.BaseColumnFamilyDesc> cfList) {
        List<ColumnFamilyDesc> columnFamilyDescList = new ArrayList<>(cfList.size());
        for (BaseColumnFamilyDesc cf : cfList) {
            columnFamilyDescList.add(convertFrom(cf));
        }
        return columnFamilyDescList;
    }

    private ColumnFamilyDesc convertFrom(com.hydraql.adapter.schema.BaseColumnFamilyDesc cf) {
        return ColumnFamilyDesc.of(cf.getNameAsString())
                .replicationScope(cf.getReplicationScope())
                .maxVersions(cf.getMaxVersions())
                .minVersions(cf.getMinVersions())
                .compressionType(cf.getCompressionType())
                .storagePolicy(cf.getStoragePolicy())
                .bloomFilterType(cf.getBloomFilterType())
                .timeToLive(cf.getTimeToLive())
                .blockSize(cf.getBlockSize())
                .blockCacheEnabled(cf.isBlockCacheEnabled())
                .inMemory(cf.isInMemory())
                .keepDeletedCells(cf.getKeepDeletedCells())
                .dataBlockEncoding(cf.getDataBlockEncoding())
                .cacheDataOnWrite(cf.isCacheDataOnWrite())
                .cacheDataInL1(cf.isCacheDataInL1())
                .cacheIndexesOnWrite(cf.isCacheIndexesOnWrite())
                .cacheBloomsOnWrite(cf.isCacheBloomsOnWrite())
                .evictBlocksOnClose(cf.isEvictBlocksOnClose())
                .prefetchBlocksOnOpen(cf.isPrefetchBlocksOnOpen())
                .setConfiguration(cf.getConfiguration())
                .setValue(cf.getValues())
                .build();
    }

    public HydraQLHBaseConfiguration getConf() {
        return conf;
    }
}
