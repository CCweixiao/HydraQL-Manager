package com.leo.hbase.manager.web.controller.query;

import java.io.Serializable;

/**
 * 表信息列表查询的条件表单数据模型
 *
 * @author leojie 2020/9/26 7:47 下午
 */
public class QueryHBaseTableForm implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 表ID
     */
    private String tableId;

    /**
     * 表的命名空间
     */
    private String namespaceName;

    /**
     * HBase表名称
     */
    private String tableName;

    /**
     * 表的禁用状态
     */
    private String disableFlag;

    /**
     * 状态（0线上表 1测试表 2启用表）
     */
    private String status;

    /**
     * 表标签筛选
     */
    private String queryHBaseTagIdStr;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
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

    public String getDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(String disableFlag) {
        this.disableFlag = disableFlag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQueryHBaseTagIdStr() {
        return queryHBaseTagIdStr;
    }

    public void setQueryHBaseTagIdStr(String queryHBaseTagIdStr) {
        this.queryHBaseTagIdStr = queryHBaseTagIdStr;
    }

    @Override
    public String toString() {
        return "QueryHBaseTableForm{" +
                ", tableId='" + tableId + '\'' +
                ", namespaceName='" + namespaceName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", disableFlag='" + disableFlag + '\'' +
                ", status='" + status + '\'' +
                ", queryHBaseTagIdStr='" + queryHBaseTagIdStr + '\'' +
                '}';
    }
}
