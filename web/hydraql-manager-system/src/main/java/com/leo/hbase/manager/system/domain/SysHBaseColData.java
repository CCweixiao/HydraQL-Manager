package com.leo.hbase.manager.system.domain;

import com.leo.hbase.manager.common.annotation.Excel;

import java.io.Serializable;

/**
 * @author leojie 2023/5/9 21:35
 */
public class SysHBaseColData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tableName;
    @Excel(name = "row key")
    private String rowKey;
    @Excel(name = "列簇名称")
    private String family;
    @Excel(name = "字段名称")
    private String qualifier;
    @Excel(name = "值")
    private String value;
    @Excel(name = "写入时间戳")
    private String timestamp;
    @Excel(name = "版本")
    private int version;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
