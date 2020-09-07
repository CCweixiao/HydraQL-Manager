package com.leo.hbase.manager.system.domain;

import com.leo.hbase.manager.common.annotation.Excel;
import com.leo.hbase.manager.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * HBase对象 sys_hbase_table_data
 *
 * @author leojie
 * @date 2020-09-07
 */
public class SysHbaseTableData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String tableName;
    private String startKey;
    private Integer limit;

    /**
     * row key
     */
    @Excel(name = "row key")
    private String rowKey;

    /**
     * 列簇名称
     */
    @Excel(name = "列簇名称")
    private String familyName;

    /**
     * 时间戳
     */
    @Excel(name = "时间戳")
    private String timestamp;

    /**
     * 数据值
     */
    @Excel(name = "数据值")
    private String value;

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getStartKey() {
        return startKey;
    }

    public void setStartKey(String startKey) {
        this.startKey = startKey;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("tableName", getTableName())
                .append("startKey", getStartKey())
                .append("limit", getLimit())
                .append("rowKey", getRowKey())
                .append("familyName", getFamilyName())
                .append("timestamp", getTimestamp())
                .append("value", getValue())
                .toString();
    }
}
