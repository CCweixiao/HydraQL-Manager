package com.leo.hbase.manager.system.dto;

import com.google.common.base.Converter;
import com.leo.hbase.manager.common.core.domain.BaseEntity;
import com.leo.hbase.manager.common.utils.DateUtils;
import com.leo.hbase.manager.system.domain.SysHbaseFamily;
import com.leo.hbase.manager.system.domain.SysHbaseTable;
import com.leo.hbase.manager.system.valid.First;
import com.leo.hbase.manager.system.valid.Second;
import com.leo.hbase.manager.system.valid.Third;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * HBase table DTO数据模型
 *
 * @author leojie 2020/8/17 9:46 下午
 */
@GroupSequence(value = {First.class, Second.class, Third.class, SysHbaseTableDto.class})
public class SysHbaseTableDto extends BaseEntity {
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
    @Size(min = 1, max = 200, message = "HBase表名称必须在1~200个字符之间", groups = {Second.class})
    private String tableName;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 禁用标志（0代表启用表 2代表禁用表）
     */
    private String disableFlag;

    /**
     * replication scope 0 1
     */
    private String replicationScope;

    /**
     * 状态（0线上表 1测试表 2弃用表）
     */
    private String status;

    private Long[] tagIds;

    @NotEmpty(message = "请为表至少指定一个列簇", groups = {Third.class})
    private List<@NotNull @Valid SysHbaseFamily> families;

    /**
     * 预分区开始的key
     */
    private String startKey;
    /**
     * 预分区结束的key
     */
    private String endKey;
    /**
     * 分区数
     */
    private Integer preSplitRegions;
    /**
     * 以指定分区key的方式分区
     */
    private String preSplitKeys;

    public SysHbaseTable convertTo() {
        SysHbaseTableConvert convert = new SysHbaseTableConvert();
        return convert.convert(this);
    }

    private static class SysHbaseTableConvert extends Converter<SysHbaseTableDto, SysHbaseTable> {

        @Override
        protected SysHbaseTable doForward(SysHbaseTableDto sysHbaseTableDto) {
            sysHbaseTableDto.setCreateTime(DateUtils.getNowDate());
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

    public List<SysHbaseFamily> getFamilies() {
        return families;
    }

    public void setFamilies(List<SysHbaseFamily> families) {
        this.families = families;
    }

    public String getStartKey() {
        return startKey;
    }

    public void setStartKey(String startKey) {
        this.startKey = startKey;
    }

    public String getEndKey() {
        return endKey;
    }

    public void setEndKey(String endKey) {
        this.endKey = endKey;
    }

    public Integer getPreSplitRegions() {
        return preSplitRegions;
    }

    public void setPreSplitRegions(Integer preSplitRegions) {
        this.preSplitRegions = preSplitRegions;
    }

    public String getPreSplitKeys() {
        return preSplitKeys;
    }

    public void setPreSplitKeys(String preSplitKeys) {
        this.preSplitKeys = preSplitKeys;
    }

    public String getReplicationScope() {
        return replicationScope;
    }

    public void setReplicationScope(String replicationScope) {
        this.replicationScope = replicationScope;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("tableId", getTableId())
                .append("namespaceId", getNamespaceId())
                .append("tableName", getTableName())
                .append("delFlag", getDelFlag())
                .append("disableFlag", getDisableFlag())
                .append("replicationScope", getReplicationScope())
                .append("startKey", getStartKey())
                .append("endKey", getEndKey())
                .append("preSplitRegions", getPreSplitRegions())
                .append("preSplitKeys", getPreSplitKeys())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("status", getStatus())
                .append("remark", getRemark())
                .toString();
    }
}
