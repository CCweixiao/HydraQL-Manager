package com.leo.hbase.manager.web.controller.query;

import java.io.Serializable;

/**
 * 表信息列表查询的条件表单数据模型
 *
 * @author leojie 2020/9/26 7:47 下午
 */
public class QueryHBaseFamilyParam implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 表ID
     */
    private Long tableId;

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    @Override
    public String toString() {
        return "QueryHBaseFamilyParam{" +
                "tableId=" + tableId +
                '}';
    }
}
