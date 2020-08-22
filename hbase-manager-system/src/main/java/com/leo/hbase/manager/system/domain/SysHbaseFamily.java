package com.leo.hbase.manager.system.domain;

import com.leo.hbase.manager.common.annotation.Excel;
import com.leo.hbase.manager.common.core.domain.BaseEntity;
import com.leo.hbase.manager.system.valid.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.GroupSequence;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * HBase Family对象 sys_hbase_family
 *
 * @author leojie
 * @date 2020-08-22
 */
@GroupSequence(value = {First.class, Second.class, Third.class, Fourth.class, Five.class, SysHbaseFamily.class})
public class SysHbaseFamily extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * family的编号
     */
    private Long familyId;

    /**
     * HBase表编号
     */
    @Excel(name = "HBase表编号")
    private Long tableId;

    /**
     * family名称
     */
    @Excel(name = "family名称")
    private String familyName;

    /**
     * 最大版本号
     */
    @Excel(name = "最大版本号")
    private Integer maxVersions;

    /**
     * ttl
     */
    @Excel(name = "ttl")
    private Integer ttl;

    /**
     * 列簇数据的压缩类型
     */
    @Excel(name = "列簇数据的压缩类型")
    private String compressionType;

    @Excel(name = "replication标志", readConverterExp = "0, 1")
    private String replicationScope;

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public Long getFamilyId() {
        return familyId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @Size(min = 1, max = 200, message = "列簇名称必须在1~200个字符之间", groups = {First.class})
    public String getFamilyName() {
        return familyName;
    }

    public void setMaxVersions(Integer maxVersions) {
        this.maxVersions = maxVersions;
    }

    @Min(value = 1, message = "最大版本数不能小于1", groups = {Second.class})
    @Max(value = 999999, message = "最大版本数不能大于999999", groups = {Third.class})
    public Integer getMaxVersions() {
        if (maxVersions == null) {
            maxVersions = 1;
        }
        return maxVersions;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    @Min(value = 1, message = "TTL不能小于1", groups = {Fourth.class})
    @Max(value = 2147483647, message = "TTL不能超过2147483647", groups = {Five.class})
    public Integer getTtl() {
        if (ttl == null) {
            ttl = 2147483647;
        }
        return ttl;
    }

    public void setCompressionType(String compressionType) {
        this.compressionType = compressionType;
    }

    public String getCompressionType() {
        return compressionType;
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
                .append("familyId", getFamilyId())
                .append("tableId", getTableId())
                .append("familyName", getFamilyName())
                .append("maxVersions", getMaxVersions())
                .append("ttl", getTtl())
                .append("compressionType", getCompressionType())
                .append("replicationScope", getReplicationScope())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
