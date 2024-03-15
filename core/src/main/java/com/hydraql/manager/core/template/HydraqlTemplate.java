package com.hydraql.manager.core.template;

import com.hydraql.manager.core.conf.HydraqlHBaseConfiguration;
import com.hydraql.manager.core.hbase.SplitGoEnum;
import com.hydraql.manager.core.hbase.model.HBaseDataSet;
import com.hydraql.manager.core.hbase.model.Result;
import com.hydraql.manager.core.hbase.model.SnapshotDesc;
import com.hydraql.manager.core.hbase.schema.ColumnFamilyDesc;
import com.hydraql.manager.core.hbase.schema.HTableDesc;
import com.hydraql.manager.core.hbase.schema.NamespaceDesc;
import com.hydraql.manager.core.hbase.model.HBaseRowData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leojie 2024/1/25 16:10
 */
public interface HydraqlTemplate {

    class Factory {
        private static final Logger LOG = LoggerFactory.getLogger(Factory.class);

        private Factory() {}

        public static HydraqlTemplate create(HydraqlHBaseConfiguration conf) {
            List<HydraqlTemplateFactory> factories = HydraqlTemplateFactoryRegistry.findAll(conf);
            if (factories.isEmpty()) {
                throw new IllegalArgumentException("No Hydraql Template Factory found.");
            }
            List<Throwable> errors = new ArrayList<>();
            for (HydraqlTemplateFactory factory : factories) {
                ClassLoader previousClassLoader = Thread.currentThread().getContextClassLoader();
                try {
                    Thread.currentThread().setContextClassLoader(factory.getClass().getClassLoader());
                    HydraqlTemplate hydraqlTemplate = new HydraqlTemplateWithLogging(factory.create(conf));
                    LOG.info("HydraqlTemplate created with factory {}", factory.getClass().getSimpleName());
                    return hydraqlTemplate;
                } catch (Throwable e) {
                    errors.add(e);
                    LOG.warn(String.format("Failed to create HydraqlTemplate by factory %s: %s",
                            factory.getClass().getSimpleName(), e));
                } finally {
                    Thread.currentThread().setContextClassLoader(previousClassLoader);
                }
            }
            IllegalArgumentException e = new IllegalArgumentException(
                    "Unable to create an UnderFileSystem instance for path");
            for (Throwable t : errors) {
                e.addSuppressed(t);
            }
            throw e;
        }
    }

    boolean tableExists(String tableName);

    boolean createNamespace(NamespaceDesc namespaceDesc);

    boolean namespaceIsExists(String namespaceName);

    boolean deleteNamespace(String namespaceName);

    List<NamespaceDesc> listNamespaceDesc();

    NamespaceDesc getNamespaceDesc(String namespaceName);

    List<String> listNamespaceNames();

    boolean createTable(HTableDesc tableDesc);

    boolean createTable(HTableDesc tableDesc, String startKey, String endKey, int numRegions, boolean isAsync);

    boolean createTable(HTableDesc tableDesc, String[] splitKeys, boolean isAsync);

    boolean createTable(HTableDesc tableDesc, SplitGoEnum splitGoEnum, int numRegions, boolean isAsync);

    List<HTableDesc> listTableDesc(boolean includeSysTables);

    List<String> listTableNames();

    List<String> listTableNamesByNamespace(String namespaceName);

    boolean enableTable(String tableName);

    boolean disableTable(String tableName);

    boolean deleteTable(String tableName);

    boolean truncatePreserve(String tableName);

    HTableDesc getHTableDesc(String tableName);

    List<ColumnFamilyDesc> getColumnFamilyDesc(String tableName);

    boolean addFamily(String tableName, ColumnFamilyDesc familyDesc);

    boolean deleteFamily(String tableName, String familyName);

    boolean modifyFamily(String tableName, ColumnFamilyDesc familyDesc);

    boolean enableReplication(String tableName, List<String> families);

    boolean disableReplication(String tableName, List<String> families);

    boolean modifyTable(HTableDesc tableDesc);

    boolean modifyTableProps(HTableDesc tableDesc);

    int totalHRegionServerNum();

    boolean isTableDisabled(String tableName);

    boolean tableIsExists(String tableName);

    boolean createSnapshot(SnapshotDesc snapshotDesc);

    boolean removeSnapshot(String snapshotName);

    List<SnapshotDesc> listAllSnapshotDesc();

    boolean shellSessionIsConnected();

    Result executeShellCommand(String command);

    List<String> getAllShellCommands();

    HBaseRowData getRow(String tableName, String rowKey);

    List<String> showVirtualTables(String hql);

    String showCreateVirtualTable(String hql);

    void createVirtualTable(String hql);

    void dropVirtualTable(String hql);

    void upsert(String hql);

    void delete(String hql);
}
