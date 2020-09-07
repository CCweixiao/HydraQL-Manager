package com.leo.hbase.manager.adaptor.service.impl;

import com.github.CCweixiao.HBaseAdminTemplate;
import com.leo.hbase.manager.adaptor.service.IHBaseAdminService;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leojie 2020/8/19 11:04 下午
 */
@Service
public class HBaseAdminServiceImpl implements IHBaseAdminService {
    @Autowired
    private HBaseAdminTemplate hBaseAdminTemplate;

    @Override
    public List<String> listAllNamespace() {
        return hBaseAdminTemplate.listNamespaces();
    }

    @Override
    public boolean createNamespace(String namespace) {
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(namespace).build();
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

        byte[][] splitKeyBytes = (byte[][]) Arrays.stream(splitKeys).map(Bytes::toBytes).toArray();
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
    public String getTableDesc(String tableName) {
        return hBaseAdminTemplate.getTableDescriptor(tableName).toString();
    }

    @Override
    public boolean enableReplication(String tableName, List<String> families) {
        return hBaseAdminTemplate.enableReplicationScope(tableName, families);
    }

    @Override
    public boolean disableReplication(String tableName, List<String> families) {
        return hBaseAdminTemplate.disableReplicationScope(tableName, families);
    }
}
