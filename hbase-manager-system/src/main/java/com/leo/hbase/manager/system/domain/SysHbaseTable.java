package com.leo.hbase.manager.system.domain;

import com.leo.hbase.manager.common.annotation.Excel;
import com.leo.hbase.manager.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
     * HBase表namespace编号
     */
    @Excel(name = "HBase表namespace编号")
    private Long namespaceId;

    private SysHbaseNamespace sysHbaseNamespace;

    /**
     * HBase表名称
     */
    @Excel(name = "HBase表名称")
    private String tableName;

    /**
     * 在线region数
     */
    @Excel(name = "在线region数")
    private Long onlineRegions;

    /**
     * 下线region数
     */
    @Excel(name = "下线region数")
    private Long offlineRegions;

    /**
     * 失败的region数
     */
    @Excel(name = "失败的region数")
    private Long failedRegions;

    /**
     * 在分裂的region数
     */
    @Excel(name = "在分裂的region数")
    private Long splitRegions;

    /**
     * 其他状态的region数
     */
    @Excel(name = "其他状态的region数")
    private Long otherRegions;

    /**
     * 表描述信息
     */
    @Excel(name = "表描述信息")
    private String tableDesc;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 禁用标志（0代表启用表 2代表禁用表）
     */
    @Excel(name = "禁用标志", readConverterExp = "0=代表启用表,2=代表禁用表")
    private String disableFlag;

    /**
     * 状态（0线上表 1测试表 2启用表）
     */
    @Excel(name = "状态", readConverterExp = "0=线上表,1=测试表,2=启用表")
    private String status;

    private Long[] tagIds;

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setNamespaceId(Long namespaceId) {
        this.namespaceId = namespaceId;
    }

    public Long getNamespaceId() {
        return namespaceId;
    }

    public SysHbaseNamespace getSysHbaseNamespace() {
        return sysHbaseNamespace;
    }

    public void setSysHbaseNamespace(SysHbaseNamespace sysHbaseNamespace) {
        this.sysHbaseNamespace = sysHbaseNamespace;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setOnlineRegions(Long onlineRegions) {
        this.onlineRegions = onlineRegions;
    }

    public Long getOnlineRegions() {
        return onlineRegions;
    }

    public void setOfflineRegions(Long offlineRegions) {
        this.offlineRegions = offlineRegions;
    }

    public Long getOfflineRegions() {
        return offlineRegions;
    }

    public void setFailedRegions(Long failedRegions) {
        this.failedRegions = failedRegions;
    }

    public Long getFailedRegions() {
        return failedRegions;
    }

    public void setSplitRegions(Long splitRegions) {
        this.splitRegions = splitRegions;
    }

    public Long getSplitRegions() {
        return splitRegions;
    }

    public void setOtherRegions(Long otherRegions) {
        this.otherRegions = otherRegions;
    }

    public Long getOtherRegions() {
        return otherRegions;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDisableFlag(String disableFlag) {
        this.disableFlag = disableFlag;
    }

    public String getDisableFlag() {
        return disableFlag;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public Long[] getTagIds() {
        return tagIds;
    }

    public void setTagIds(Long[] tagIds) {
        this.tagIds = tagIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("tableId", getTableId())
                .append("namespaceId", getNamespaceId())
                .append("namespace", getSysHbaseNamespace().getNamespaceName())
                .append("tableName", getTableName())
                .append("onlineRegions", getOnlineRegions())
                .append("offlineRegions", getOfflineRegions())
                .append("failedRegions", getFailedRegions())
                .append("splitRegions", getSplitRegions())
                .append("otherRegions", getOtherRegions())
                .append("tableDesc", getTableDesc())
                .append("delFlag", getDelFlag())
                .append("disableFlag", getDisableFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("status", getStatus())
                .append("remark", getRemark())
                .toString();
    }
}
