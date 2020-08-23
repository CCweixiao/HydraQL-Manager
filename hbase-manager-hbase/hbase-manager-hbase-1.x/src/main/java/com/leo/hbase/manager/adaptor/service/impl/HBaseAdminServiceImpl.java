package com.leo.hbase.manager.adaptor.service.impl;

import com.leo.hbase.manager.adaptor.service.IHBaseAdminService;
import com.leo.hbase.sdk.core.HBaseAdminTemplate;
import com.leo.hbase.sdk.core.exception.HBaseOperationsException;
import com.leo.hbase.sdk.core.model.HTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return hBaseAdminTemplate.createNamespace(namespace);
    }

    @Override
    public boolean deleteNamespace(String namespace) {
        //TODO 删除命名空间
        throw new HBaseOperationsException("暂未支持删除命名空间！");
    }

    @Override
    public List<String> listAllTableName() {
        return hBaseAdminTemplate.listTableNames();
    }

    @Override
    public boolean createTable(HTableModel hTableModel, String startKey, String endKey, int numRegions) {
        return hBaseAdminTemplate.createTable(hTableModel, startKey, endKey, numRegions);
    }

    @Override
    public boolean createTable(HTableModel hTableModel, String[] splitKeys) {
        return hBaseAdminTemplate.createTable(hTableModel, splitKeys);
    }

    @Override
    public boolean createTable(HTableModel hTableModel) {
        return hBaseAdminTemplate.createTable(hTableModel);
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
    public boolean tableIsDisabled(String tableName) {
        return hBaseAdminTemplate.tableIsDisabled(tableName);
    }

    @Override
    public boolean tableIsExists(String tableName) {
        return hBaseAdminTemplate.tableIsExists(tableName);
    }

    @Override
    public boolean deleteTable(String tableName) {
        return hBaseAdminTemplate.deleteTable(tableName);
    }

    @Override
    public String getTableDesc(String tableName) {
        return hBaseAdminTemplate.getTableDescriptor(tableName);
    }

    @Override
    public boolean enableReplication(String tableName, String... families) {
        return hBaseAdminTemplate.enableReplicationScope(tableName, families);
    }

    @Override
    public boolean disableReplication(String tableName, String... families) {
        return hBaseAdminTemplate.disableReplicationScope(tableName, families);
    }
}
