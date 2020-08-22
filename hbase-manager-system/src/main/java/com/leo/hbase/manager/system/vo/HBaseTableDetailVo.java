package com.leo.hbase.manager.system.vo;

/**
 * HBase详情展示的数据模型
 *
 * @author leojie 2020/8/22 9:59 上午
 */
public class HBaseTableDetailVo {
    private String tableName;
    private String disabledStatus;
    /**
     * 在线region数
     */
    private Long onlineRegions;

    /**
     * 下线region数
     */
    private Long offlineRegions;

    /**
     * 失败的region数
     */
    private Long failedRegions;

    /**
     * 在分裂的region数
     */
    private Long splitRegions;

    /**
     * 其他状态的region数
     */
    private Long otherRegions;

    /**
     * 表描述信息
     */
    private String tableDesc;

    /**
     * 表的备注信息
     */
    private String remark;


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

    public Long getOnlineRegions() {
        return onlineRegions;
    }

    public void setOnlineRegions(Long onlineRegions) {
        this.onlineRegions = onlineRegions;
    }

    public Long getOfflineRegions() {
        return offlineRegions;
    }

    public void setOfflineRegions(Long offlineRegions) {
        this.offlineRegions = offlineRegions;
    }

    public Long getFailedRegions() {
        return failedRegions;
    }

    public void setFailedRegions(Long failedRegions) {
        this.failedRegions = failedRegions;
    }

    public Long getSplitRegions() {
        return splitRegions;
    }

    public void setSplitRegions(Long splitRegions) {
        this.splitRegions = splitRegions;
    }

    public Long getOtherRegions() {
        return otherRegions;
    }

    public void setOtherRegions(Long otherRegions) {
        this.otherRegions = otherRegions;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "HBaseTableDetailVo{" +
                "tableName='" + tableName + '\'' +
                ", disabledStatus='" + disabledStatus + '\'' +
                ", onlineRegions=" + onlineRegions +
                ", offlineRegions=" + offlineRegions +
                ", failedRegions=" + failedRegions +
                ", splitRegions=" + splitRegions +
                ", otherRegions=" + otherRegions +
                ", remark=" + remark +
                ", tableDesc='" + tableDesc + '\'' +
                '}';
    }
}
