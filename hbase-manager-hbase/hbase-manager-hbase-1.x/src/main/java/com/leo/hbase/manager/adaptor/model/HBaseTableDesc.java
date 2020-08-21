package com.leo.hbase.manager.adaptor.model;

/**
 * @author leojie 2020/8/21 11:16 下午
 */
public class HBaseTableDesc {
    private String tableName;
    private String disabledStatus;
    private String tableDesc;


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDisabledStatus() {
        return disabledStatus;
    }

    public void setDisabledStatus(String disabledStatus) {
        this.disabledStatus = disabledStatus;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    @Override
    public String toString() {
        return "HBaseTableDesc{" +
                ", tableName='" + tableName + '\'' +
                ", disabledStatus='" + disabledStatus + '\'' +
                ", tableDesc='" + tableDesc + '\'' +
                '}';
    }
}
