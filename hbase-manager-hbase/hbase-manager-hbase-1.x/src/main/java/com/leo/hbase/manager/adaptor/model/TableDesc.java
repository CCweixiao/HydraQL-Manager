package com.leo.hbase.manager.adaptor.model;

import java.util.List;
import java.util.Map;

/**
 * @author leojie 2020/9/9 10:44 下午
 */
public class TableDesc {
    private Long tableId;
    private String tableName;

    private String namespaceId;
    private String namespaceName;

    private boolean disabled;
    private boolean metaTable;
    private Map<String, String> tableProps;
    private List<FamilyDesc> familyDescList;

    /**
     * 预分区开始的key
     */
    private String startKey;
    /**
     * 预分区结束的key
     */
    private String endKey;
    /**
     * 分区数
     */
    private Integer preSplitRegions;
    /**
     * 以指定分区key的方式分区
     */
    private String preSplitKeys;

    public String getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
    }

    public String getNamespaceName() {
        return namespaceName;
    }

    public void setNamespaceName(String namespaceName) {
        this.namespaceName = namespaceName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Map<String, String> getTableProps() {
        return tableProps;
    }

    public void setTableProps(Map<String, String> tableProps) {
        this.tableProps = tableProps;
    }

    public List<FamilyDesc> getFamilyDescList() {
        return familyDescList;
    }

    public void setFamilyDescList(List<FamilyDesc> familyDescList) {
        this.familyDescList = familyDescList;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public boolean isMetaTable() {
        return metaTable;
    }

    public void setMetaTable(boolean metaTable) {
        this.metaTable = metaTable;
    }

    public String getStartKey() {
        return startKey;
    }

    public void setStartKey(String startKey) {
        this.startKey = startKey;
    }

    public String getEndKey() {
        return endKey;
    }

    public void setEndKey(String endKey) {
        this.endKey = endKey;
    }

    public Integer getPreSplitRegions() {
        return preSplitRegions;
    }

    public void setPreSplitRegions(Integer preSplitRegions) {
        this.preSplitRegions = preSplitRegions;
    }

    public String getPreSplitKeys() {
        return preSplitKeys;
    }

    public void setPreSplitKeys(String preSplitKeys) {
        this.preSplitKeys = preSplitKeys;
    }
}
