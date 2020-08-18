package com.leo.hbase.manager.system.dto;

import com.google.common.base.Converter;
import com.leo.hbase.manager.system.domain.SysHbaseTable;
import com.leo.hbase.manager.system.valid.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * HBase table DTO数据模型
 *
 * @author leojie 2020/8/17 9:46 下午
 */
@GroupSequence(value = {First.class, Second.class, Third.class, Fourth.class, SysHbaseTableDto.class})
public class SysHbaseTableDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * HBase表编号
     */
    private Long tableId;

    /**
     * HBase表namespace编号
     */
    @NotNull
    @Min(value = 1, message = "HBase表所属的namespace不能我空", groups = {First.class})
    private Long namespaceId;

    /**
     * HBase表名称
     */
    @NotBlank(message = "HBase表名称不能为空", groups = {Second.class})
    @Size(min = 1, max = 200, message = "HBase表名称长度不能超过200个字符", groups = {Third.class})
    private String tableName;

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
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 禁用标志（0代表启用表 2代表禁用表）
     */
    private String disableFlag;

    /**
     * 状态（0线上表 1测试表 2弃用表）
     */
    private String status;

    private Long[] tagIds;

    @NotEmpty(message = "请为表至少指定一个列簇",groups = {Fourth.class})
    private List<@NotNull @Valid SysHbaseFamilyModel> families;

    public SysHbaseTable convertTo() {
        SysHbaseTableConvert convert = new SysHbaseTableConvert();
        return convert.convert(this);
    }

    private static class SysHbaseTableConvert extends Converter<SysHbaseTableDto, SysHbaseTable> {

        @Override
        protected SysHbaseTable doForward(SysHbaseTableDto sysHbaseTableDto) {
            //sysHbaseTableDto.setCreateTime(DateUtils.getNowDate());
            SysHbaseTable sysHbaseTable = new SysHbaseTable();
            BeanUtils.copyProperties(sysHbaseTableDto, sysHbaseTable);
            return sysHbaseTable;
        }

        @Override
        protected SysHbaseTableDto doBackward(SysHbaseTable sysHbaseTable) {
            throw new AssertionError("暂不支持逆向转化方法!");
        }
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(Long namespaceId) {
        this.namespaceId = namespaceId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
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

    public Long[] getTagIds() {
        return tagIds;
    }

    public void setTagIds(Long[] tagIds) {
        this.tagIds = tagIds;
    }

    public List<SysHbaseFamilyModel> getFamilies() {
        return families;
    }

    public void setFamilies(List<SysHbaseFamilyModel> families) {
        this.families = families;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("tableId", getTableId())
                .append("namespaceId", getNamespaceId())
                .append("tableName", getTableName())
                .append("onlineRegions", getOnlineRegions())
                .append("offlineRegions", getOfflineRegions())
                .append("failedRegions", getFailedRegions())
                .append("splitRegions", getSplitRegions())
                .append("otherRegions", getOtherRegions())
                .append("tableDesc", getTableDesc())
                .append("delFlag", getDelFlag())
                .append("disableFlag", getDisableFlag())
                //.append("createBy", getCreateBy())
                //.append("createTime", getCreateTime())
                //.append("updateBy", getUpdateBy())
                //.append("updateTime", getUpdateTime())
                .append("status", getStatus())
                // .append("remark", getRemark())
                .toString();
    }
}
