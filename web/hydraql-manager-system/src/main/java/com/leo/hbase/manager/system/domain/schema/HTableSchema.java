package com.leo.hbase.manager.system.domain.schema;

import java.util.List;

/**
 * @author leojie 2023/7/23 21:22
 */
public class HTableSchema {
    private String tableName;
    private String defaultFamily;

    private List<HTableColumn> columnList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDefaultFamily() {
        return defaultFamily;
    }

    public void setDefaultFamily(String defaultFamily) {
        this.defaultFamily = defaultFamily;
    }

    public List<HTableColumn> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<HTableColumn> columnList) {
        this.columnList = columnList;
    }
}
