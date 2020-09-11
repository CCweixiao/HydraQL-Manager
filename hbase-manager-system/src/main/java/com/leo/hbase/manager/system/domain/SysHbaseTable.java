package com.leo.hbase.manager.system.domain;

import com.leo.hbase.manager.common.annotation.Excel;
import com.leo.hbase.manager.common.core.domain.BaseEntity;

import java.util.Arrays;
import java.util.List;

/**
 * HBase对象 sys_hbase_table
 *
 * @author leojie
 * @date 2020-08-16
 */
public class SysHbaseTable extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * HBase表编号
     */
    private Long tableId;

    /**
     * 表的命名空间
     */
    private String namespaceName;

    /**
     * HBase表名称
     */
    @Excel(name = "HBase表名称")
    private String tableName;

    /**
     * 表的禁用状态
     */
    private String disableFlag;


    /**
     * 状态（0线上表 1测试表 2启用表）
     */
    @Excel(name = "状态", readConverterExp = "0=线上表,1=待上线表,2=测试表,3=弃用表")
    private String status;

    /**
     * 表的备注信息
     */
    private String remark;


    private Long[] tagIds;


    private String queryHBaseTagIdStr;

    private String[] queryHBaseTagIds;

    private List<SysHbaseTag> sysHbaseTagList;


    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long[] getTagIds() {
        return tagIds;
    }

    public void setTagIds(Long[] tagIds) {
        this.tagIds = tagIds;
    }

    public String getQueryHBaseTagIdStr() {
        return queryHBaseTagIdStr;
    }

    public void setQueryHBaseTagIdStr(String queryHBaseTagIdStr) {
        this.queryHBaseTagIdStr = queryHBaseTagIdStr;
    }

    public String[] getQueryHBaseTagIds() {
        return queryHBaseTagIds;
    }

    public void setQueryHBaseTagIds(String[] queryHBaseTagIds) {
        this.queryHBaseTagIds = queryHBaseTagIds;
    }

    public List<SysHbaseTag> getSysHbaseTagList() {
        return sysHbaseTagList;
    }

    public void setSysHbaseTagList(List<SysHbaseTag> sysHbaseTagList) {
        this.sysHbaseTagList = sysHbaseTagList;
    }

    public String getDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(String disableFlag) {
        this.disableFlag = disableFlag;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SysHbaseTable{" +
                "tableId=" + tableId +
                ", namespaceName='" + namespaceName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", disableFlag='" + disableFlag + '\'' +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", tagIds=" + Arrays.toString(tagIds) +
                ", queryHBaseTagIdStr='" + queryHBaseTagIdStr + '\'' +
                ", queryHBaseTagIds=" + Arrays.toString(queryHBaseTagIds) +
                ", sysHbaseTagList=" + sysHbaseTagList +
                '}';
    }
}
