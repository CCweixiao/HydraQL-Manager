package com.leo.hbase.manager.system.domain.schema;

/**
 * @author leojie 2023/4/1 22:07
 */
public class HTableColumn {
    private String familyName;
    private String columnName;
    private String columnType;
    private boolean columnIsRow;

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public boolean isColumnIsRow() {
        return columnIsRow;
    }

    public void setColumnIsRow(boolean columnIsRow) {
        this.columnIsRow = columnIsRow;
    }
}
