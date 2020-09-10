package com.leo.hbase.manager.adaptor.model;

import java.util.List;
import java.util.Map;

/**
 * @author leojie 2020/9/9 10:44 下午
 */
public class TableDesc {
    private String namespaceId;
    private String namespaceName;
    private String tableId;
    private String tableName;
    private boolean disabled;
    private boolean metaTable;
    private Map<String, String> tableProps;
    private List<FamilyDesc> familyDescList;

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

    public boolean getMetaTable() {
        return metaTable;
    }

    public void setMetaTable(boolean metaTable) {
        this.metaTable = metaTable;
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

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
}
