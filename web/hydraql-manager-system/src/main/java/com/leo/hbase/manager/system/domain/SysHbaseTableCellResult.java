package com.leo.hbase.manager.system.domain;

import java.util.Date;

/**
 * @author leojie 2020/12/12 7:02 下午
 */
public class SysHbaseTableCellResult {
    private Object rowKey;
    private String columnName;
    private Object value;
    private Date tsDate;

    public Object getRowKey() {
        return rowKey;
    }

    public void setRowKey(Object rowKey) {
        this.rowKey = rowKey;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Date getTsDate() {
        return tsDate;
    }

    public void setTsDate(Date tsDate) {
        this.tsDate = tsDate;
    }
}
