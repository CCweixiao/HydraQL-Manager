package com.leo.hbase.manager.system.dto;

import com.google.common.base.Converter;
import com.hydraql.manager.core.hbase.schema.ColumnFamilyDesc;
import com.hydraql.manager.core.util.HConstants;
import com.leo.hbase.manager.common.annotation.Excel;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.system.valid.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.GroupSequence;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * @author leojie 2020/9/10 10:28 下午
 */
@GroupSequence(value = {First.class, Second.class, Third.class, Fourth.class, Five.class, FamilyDescDto.class})
public class FamilyDescDto {

    private String familyId;

    @Excel(name = "HBase表名")
    private String tableName;

    @Excel(name = "family名称")
    private String familyName;

    @Excel(name = "最大版本号")
    private Integer maxVersions;

    @Excel(name = "ttl")
    private Integer timeToLive;

    private String ttl;

    @Excel(name = "列簇压缩类型")
    private String compressionType;

    @Excel(name = "replication标志")
    private Integer replicationScope;

    public ColumnFamilyDesc convertTo() {
        FamilyDescDto.ColumnFamilyDescDtoConvert convert = new FamilyDescDto.ColumnFamilyDescDtoConvert();
        return convert.convert(this);
    }

    public FamilyDescDto convertFor(ColumnFamilyDesc columnFamilyDesc) {
        FamilyDescDto.ColumnFamilyDescDtoConvert convert = new FamilyDescDto.ColumnFamilyDescDtoConvert();
        return convert.reverse().convert(columnFamilyDesc);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    @Size(min = 1, max = 200, message = "列簇名称必须在1~200个字符之间", groups = {First.class})
    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @Min(value = 1, message = "最小版本数不能小于1", groups = {Second.class})
    @Max(value = 999999, message = "最小版本数不能大于999999", groups = {Third.class})
    public Integer getMaxVersions() {
        if (maxVersions == null) {
            maxVersions = HConstants.DEFAULT_MAX_VERSIONS;
        }
        return maxVersions;
    }

    public void setMaxVersions(Integer maxVersions) {
        this.maxVersions = maxVersions;
    }

    @Min(value = 1, message = "TTL不能小于1", groups = {Fourth.class})
    @Max(value = 2147483647, message = "TTL不能超过2147483647", groups = {Five.class})
    public Integer getTimeToLive() {
        if (timeToLive == null || timeToLive == 0) {
            timeToLive = HConstants.DEFAULT_TTL;
        }
        return timeToLive;
    }

    public void setTimeToLive(Integer timeToLive) {
        this.timeToLive = timeToLive;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getCompressionType() {
        if(StringUtils.isBlank(compressionType)){
            return HConstants.DEFAULT_COMPRESSION_TYPE;
        }
        return compressionType;
    }

    public void setCompressionType(String compressionType) {
        this.compressionType = compressionType;
    }

    public Integer getReplicationScope() {
        if(replicationScope == null){
            return HConstants.DEFAULT_REPLICATION_SCOPE;
        }
        return replicationScope;
    }

    public void setReplicationScope(Integer replicationScope) {
        this.replicationScope = replicationScope;
    }

    public static class ColumnFamilyDescDtoConvert extends Converter<FamilyDescDto, ColumnFamilyDesc> {

        @Override
        protected ColumnFamilyDesc doForward(FamilyDescDto columnFamilyDescDto) {
            return ColumnFamilyDesc.of(columnFamilyDescDto.getFamilyName())
                    .maxVersions(columnFamilyDescDto.getMaxVersions())
                    .timeToLive(columnFamilyDescDto.getTimeToLive())
                    .compressionType(columnFamilyDescDto.getCompressionType())
                    .replicationScope(columnFamilyDescDto.getReplicationScope())
                    .build();
        }

        @Override
        protected FamilyDescDto doBackward(ColumnFamilyDesc columnFamilyDesc) {
            FamilyDescDto columnFamilyDescDto = new FamilyDescDto();
            columnFamilyDescDto.setFamilyId(columnFamilyDesc.getName());
            columnFamilyDescDto.setFamilyName(columnFamilyDesc.getName());
            columnFamilyDescDto.setMaxVersions(columnFamilyDesc.getMaxVersions());
            columnFamilyDescDto.setCompressionType(columnFamilyDesc.getCompressionType());
            columnFamilyDescDto.setReplicationScope(columnFamilyDesc.getReplicationScope());
            columnFamilyDescDto.setTimeToLive(columnFamilyDesc.getTimeToLive());
            columnFamilyDescDto.setTtl(columnFamilyDesc.humanReadableTTL(columnFamilyDesc.getTimeToLive()));
            return columnFamilyDescDto;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("tableName", getTableName())
                .append("familyId", getFamilyId())
                .append("familyName", getFamilyName())
                .append("maxVersions", getMaxVersions())
                .append("timeToLive", getTimeToLive())
                .append("ttl", getTtl())
                .append("compressionType", getCompressionType())
                .append("replicationScope", getReplicationScope())
                .toString();
    }
}
