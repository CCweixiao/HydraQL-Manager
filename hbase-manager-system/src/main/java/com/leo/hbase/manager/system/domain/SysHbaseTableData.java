package com.leo.hbase.manager.system.domain;

import com.leo.hbase.manager.common.annotation.Excel;
import com.leo.hbase.manager.common.core.domain.BaseEntity;
import com.leo.hbase.manager.system.valid.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * HBase对象 sys_hbase_table_data
 *
 * @author leojie
 * @date 2020-09-07
 */
@GroupSequence(value = {First.class, Second.class, Third.class, Fourth.class, Five.class, Six.class, SysHbaseTableData.class})
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

    private String startTimestamp;

    private String endTimestamp;

    /**
     * 数据值
     */
    @Excel(name = "数据值")
    private String value;

    @NotBlank(message = "HBase表名称不能为空", groups = {First.class})
    @Size(min = 1, max = 200, message = "HBase表名称必须在1~200个字符之间", groups = {Second.class})
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    @NotBlank(message = "Row Key不能为空", groups = {Third.class})
    public String getRowKey() {
        return rowKey;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @NotBlank(message = "列簇名称不能为空", groups = {Fourth.class})
    @Size(min = 1, max = 200, message = "列簇名称必须在1~200个字符之间", groups = {Five.class})
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

    @NotBlank(message = "值不能为空", groups = {Six.class})
    public String getValue() {
        return value;
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

    public String getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(String startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public String getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(String endTimestamp) {
        this.endTimestamp = endTimestamp;
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
                .append("startTimestamp", getStartTimestamp())
                .append("endTimestamp", getEndTimestamp())
                .append("value", getValue())
                .toString();
    }
}
