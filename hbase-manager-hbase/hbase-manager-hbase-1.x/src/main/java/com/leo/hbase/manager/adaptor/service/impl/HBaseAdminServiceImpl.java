package com.leo.hbase.manager.adaptor.service.impl;

import com.github.CCweixiao.HBaseAdminTemplate;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.leo.hbase.manager.adaptor.model.FamilyDesc;
import com.leo.hbase.manager.adaptor.model.NamespaceDesc;
import com.leo.hbase.manager.adaptor.model.TableDesc;
import com.leo.hbase.manager.adaptor.service.IHBaseAdminService;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leojie 2020/8/19 11:04 下午
 */
@Service
public class HBaseAdminServiceImpl implements IHBaseAdminService {
    public static final String TABLE_NAME_SPLIT_CHAR = ":";
    public static final String DEFAULT_NAMESPACE_NAME = "default";

    @Autowired
    private HBaseAdminTemplate hBaseAdminTemplate;

    @Override
    public NamespaceDesc getNamespaceDesc(String namespaceName) {
        final NamespaceDescriptor namespaceDescriptor = hBaseAdminTemplate.getNamespaceDescriptor(namespaceName);
        if (namespaceDescriptor == null) {
            throw new HBaseOperationsException("namespace[" + namespaceName + "]不存在");
        }
        NamespaceDesc namespaceDesc = new NamespaceDesc();
        namespaceDesc.setNamespaceId(namespaceDescriptor.getName());
        namespaceDesc.setNamespaceName(namespaceDescriptor.getName());
        namespaceDesc.setNamespaceProps(namespaceDescriptor.getConfiguration());
        return namespaceDesc;
    }

    @Override
    public List<NamespaceDesc> listAllNamespaceDesc() {
        return Arrays.stream(hBaseAdminTemplate.listNamespaceDescriptors()).map(namespaceDescriptor -> {
            NamespaceDesc namespaceDesc = new NamespaceDesc();
            namespaceDesc.setNamespaceId(namespaceDescriptor.getName());
            namespaceDesc.setNamespaceName(namespaceDescriptor.getName());
            namespaceDesc.setNamespaceProps(namespaceDescriptor.getConfiguration());
            return namespaceDesc;
        }).collect(Collectors.toList());
    }

    @Override
    public List<String> listAllNamespaceName() {
        return hBaseAdminTemplate.listNamespaces();
    }

    @Override
    public List<String> listAllTableNamesByNamespaceName(String namespaceName) {
        return hBaseAdminTemplate.listTableNamesByNamespace(namespaceName);
    }

    @Override
    public boolean createNamespace(NamespaceDesc namespace) {
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(namespace.getNamespaceName()).build();
        if (namespace.getNamespaceProps() != null && !namespace.getNamespaceProps().isEmpty()) {
            namespace.getNamespaceProps().forEach(namespaceDescriptor::setConfiguration);
        }
        return hBaseAdminTemplate.createNamespace(namespaceDescriptor);
    }

    @Override
    public boolean deleteNamespace(String namespace) {
        return hBaseAdminTemplate.deleteNamespace(namespace);
    }

    @Override
    public List<String> listAllTableName() {
        return Arrays.stream(hBaseAdminTemplate.listTables()).map(HTableDescriptor::getNameAsString).collect(Collectors.toList());
    }

    @Override
    public boolean createTable(HTableDescriptor tableDescriptor, String startKey, String endKey, int numRegions) {
        return hBaseAdminTemplate.createTable(tableDescriptor, Bytes.toBytes(startKey), Bytes.toBytes(endKey), numRegions);
    }

    @Override
    public boolean createTable(HTableDescriptor tableDescriptor, String[] splitKeys) {
        if (splitKeys == null || splitKeys.length < 1) {
            throw new HBaseOperationsException("请指定正确格式的需要预分区的key");
        }
        final List<byte[]> bytes = Arrays.stream(splitKeys).distinct().map(Bytes::toBytes).collect(Collectors.toList());
        byte[][] splitKeyBytes = new byte[bytes.size()][];
        for (int i = 0; i < bytes.size(); i++) {
            splitKeyBytes[i] = bytes.get(i);
        }

        // byte[][] splitKeyBytes = (byte[][]) Arrays.stream(splitKeys).map(Bytes::toBytes).toArray();
        return hBaseAdminTemplate.createTable(tableDescriptor, splitKeyBytes);
    }

    @Override
    public boolean createTable(HTableDescriptor tableDescriptor) {
        return hBaseAdminTemplate.createTable(tableDescriptor);
    }


    @Override
    public boolean enableTable(String tableName) {
        return hBaseAdminTemplate.enableTableAsync(tableName);
    }

    @Override
    public boolean disableTable(String tableName) {
        return hBaseAdminTemplate.disableTableAsync(tableName);
    }

    @Override
    public boolean isTableDisabled(String tableName) {
        return hBaseAdminTemplate.isTableDisabled(tableName);
    }

    @Override
    public boolean tableIsExists(String tableName) {
        return hBaseAdminTemplate.tableExists(tableName);
    }

    @Override
    public boolean deleteTable(String tableName) {
        return hBaseAdminTemplate.deleteTable(tableName);
    }

    @Override
    public TableDesc getTableDesc(String tableName) {
        checkTableExists(tableName);
        HTableDescriptor tableDescriptor = hBaseAdminTemplate.getTableDescriptor(tableName);
        TableDesc tableDesc = new TableDesc();
        String fullTableName = tableDescriptor.getNameAsString();
        String namespaceName = DEFAULT_NAMESPACE_NAME;

        if (fullTableName.contains(TABLE_NAME_SPLIT_CHAR)) {
            namespaceName = fullTableName.split(TABLE_NAME_SPLIT_CHAR)[0];
        } else {
            fullTableName = DEFAULT_NAMESPACE_NAME + TABLE_NAME_SPLIT_CHAR + fullTableName;
        }
        tableDesc.setTableName(fullTableName);
        tableDesc.setNamespaceId(namespaceName);
        tableDesc.setNamespaceName(namespaceName);
        tableDesc.setMetaTable(tableDescriptor.isMetaTable());
        tableDesc.setDisabled(hBaseAdminTemplate.isTableDisabled(tableName));
        tableDesc.setTableDesc(tableDescriptor.toString());

        return tableDesc;
    }

    @Override
    public String getTableDescToString(String tableName) {
        return hBaseAdminTemplate.getTableDescriptor(tableName).toString();
    }

    @Override
    public List<FamilyDesc> getFamilyDesc(String tableName) {
        checkTableExists(tableName);
        HTableDescriptor tableDescriptor = hBaseAdminTemplate.getTableDescriptor(tableName);
        return tableDescriptor.getFamilies().stream().map(hColumnDescriptor -> new FamilyDesc.Builder()
                .familyName(hColumnDescriptor.getNameAsString())
                .compressionType(hColumnDescriptor.getCompressionType().getName())
                .maxVersions(hColumnDescriptor.getMaxVersions())
                .timeToLive(hColumnDescriptor.getTimeToLive())
                .replicationScope(hColumnDescriptor.getScope())
                .build()).collect(Collectors.toList());
    }

    @Override
    public HTableDescriptor getTableDescriptor(String tableName) {
        return hBaseAdminTemplate.getTableDescriptor(tableName);
    }

    @Override
    public boolean enableReplication(String tableName, List<String> families) {
        final HTableDescriptor tableDescriptor = hBaseAdminTemplate.getTableDescriptor(tableName);
        final List<String> tempFamilies = new ArrayList<>(4);

        families.forEach(familyName -> {
            HColumnDescriptor columnDescriptor = tableDescriptor.getFamily(Bytes.toBytes(familyName));
            int scope = columnDescriptor.getScope();
            if (scope == 0) {
                tempFamilies.add(familyName);
            }
        });
        if (tempFamilies.isEmpty()) {
            return true;
        }
        return hBaseAdminTemplate.enableReplicationScope(tableName, tempFamilies);
    }

    @Override
    public boolean disableReplication(String tableName, List<String> families) {
        final HTableDescriptor tableDescriptor = hBaseAdminTemplate.getTableDescriptor(tableName);
        final List<String> tempFamilies = new ArrayList<>(4);

        families.forEach(familyName -> {
            HColumnDescriptor columnDescriptor = tableDescriptor.getFamily(Bytes.toBytes(familyName));
            int scope = columnDescriptor.getScope();
            if (scope == 1) {
                tempFamilies.add(familyName);
            }
        });
        if (tempFamilies.isEmpty()) {
            return true;
        }

        return hBaseAdminTemplate.disableReplicationScope(tableName, families);
    }

    private void checkTableExists(String tableName) {
        if (tableName == null || "".equals(tableName.trim())) {
            throw new HBaseOperationsException("the name of table is not empty.");
        }
        if (!hBaseAdminTemplate.tableExists(tableName)) {
            throw new HBaseOperationsException("the table in hbase is not exists.");
        }
    }
}
