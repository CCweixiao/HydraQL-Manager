package com.leo.hbase.manager.adaptor.service.impl;

import com.leo.hbase.manager.adaptor.service.IHBaseAdminService;
import com.leo.hbase.sdk.core.HBaseAdminTemplate;
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
    public List<String> listAllTableName() {
        return hBaseAdminTemplate.listTableNames();
    }

    @Override
    public boolean tableIsDisabled(String tableName) {
        return hBaseAdminTemplate.tableIsDisabled(tableName);
    }
}
