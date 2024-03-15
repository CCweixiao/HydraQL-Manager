package com.leo.hbase.manager.system.domain;

import com.leo.hbase.manager.common.annotation.Excel;
import com.leo.hbase.manager.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * @author leojie 2023/7/13 23:18
 */
public class SysHbaseTable extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long tableId;
    @Excel(name = "集群ID")
    private String clusterId;
    private String namespaceName;
    @Excel(name = "表名称")
    private String tableName;
    @Excel(name = "是否禁用", readConverterExp = "0=启用,2=禁用")
    private String disableFlag;
    @Excel(name = "状态", readConverterExp = "0=运行中,1=待创建,2=禁用中,3=已回收")
    private String status;

    private List<SysHbaseTag> tags;
    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
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

    public List<SysHbaseTag> getTags() {
        return tags;
    }

    public void setTags(List<SysHbaseTag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("cluster", getClusterId())
                .append("tableName", getTableName())
                .append("disable", getDisableFlag())
                .append("status", getStatus())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .toString();
    }
}
