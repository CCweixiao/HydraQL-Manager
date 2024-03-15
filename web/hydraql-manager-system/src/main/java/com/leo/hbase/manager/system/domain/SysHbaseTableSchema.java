package com.leo.hbase.manager.system.domain;

import com.leo.hbase.manager.common.core.domain.BaseEntity;

/**
 * @author leojie 2023/4/1 22:06
 */
public class SysHbaseTableSchema extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Integer schemaId;
    private String clusterId;
    private String tableName;
    private String tableSchema;

    public Integer getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(Integer schemaId) {
        this.schemaId = schemaId;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    @Override
    public String toString() {
        return "SysHbaseTableSchema{" +
                "schemaId=" + schemaId +
                ", clusterId='" + clusterId + '\'' +
                ", tableName='" + tableName + '\'' +
                ", tableSchema='" + tableSchema + '\'' +
                '}';
    }
}
