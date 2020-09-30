package com.leo.hbase.manager.system.dto;

import com.github.CCweixiao.constant.HMHBaseConstant;
import com.github.CCweixiao.model.FamilyDesc;
import com.google.common.base.Converter;
import com.leo.hbase.manager.common.annotation.Excel;
import com.leo.hbase.manager.system.valid.*;

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

    @Excel(name = "列簇压缩类型")
    private String compressionType;

    @Excel(name = "replication标志", readConverterExp = "0, 1")
    private Integer replicationScope;

    public FamilyDesc convertTo() {
        FamilyDescDto.FamilyDescDtoConvert convert = new FamilyDescDto.FamilyDescDtoConvert();
        return convert.convert(this);
    }

    public FamilyDescDto convertFor(FamilyDesc familyDesc) {
        FamilyDescDto.FamilyDescDtoConvert convert = new FamilyDescDto.FamilyDescDtoConvert();
        return convert.reverse().convert(familyDesc);
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

    @Min(value = 1, message = "最大版本数不能小于1", groups = {Second.class})
    @Max(value = 999999, message = "最大版本数不能大于999999", groups = {Third.class})
    public Integer getMaxVersions() {
        if (maxVersions == null) {
            maxVersions = 1;
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
            timeToLive = HMHBaseConstant.DEFAULT_TTL;
        }
        return timeToLive;
    }

    public void setTimeToLive(Integer timeToLive) {
        this.timeToLive = timeToLive;
    }

    public String getCompressionType() {
        return compressionType;
    }

    public void setCompressionType(String compressionType) {
        this.compressionType = compressionType;
    }

    public Integer getReplicationScope() {
        return replicationScope;
    }

    public void setReplicationScope(Integer replicationScope) {
        this.replicationScope = replicationScope;
    }

    public static class FamilyDescDtoConvert extends Converter<FamilyDescDto, FamilyDesc> {

        @Override
        protected FamilyDesc doForward(FamilyDescDto familyDescDto) {
            return new FamilyDesc.Builder()
                    .familyName(familyDescDto.getFamilyName())
                    .maxVersions(familyDescDto.getMaxVersions())
                    .timeToLive(familyDescDto.getTimeToLive())
                    .compressionType(familyDescDto.getCompressionType())
                    .replicationScope(familyDescDto.getReplicationScope())
                    .build();
        }

        @Override
        protected FamilyDescDto doBackward(FamilyDesc familyDesc) {
            FamilyDescDto familyDescDto = new FamilyDescDto();
            familyDescDto.setFamilyId(familyDesc.getFamilyName());
            familyDescDto.setFamilyName(familyDesc.getFamilyName());
            familyDescDto.setMaxVersions(familyDesc.getMaxVersions());
            familyDescDto.setCompressionType(familyDesc.getCompressionType());
            familyDescDto.setReplicationScope(familyDesc.getReplicationScope());
            familyDescDto.setTimeToLive(familyDesc.getTimeToLive());
            return familyDescDto;
        }
    }

    @Override
    public String toString() {
        return "FamilyDescDto{" +
                "tableName='" + tableName + '\'' +
                "familyId='" + familyId + '\'' +
                ", familyName='" + familyName + '\'' +
                ", maxVersions=" + maxVersions +
                ", timeToLive=" + timeToLive +
                ", compressionType='" + compressionType + '\'' +
                ", replicationScope=" + replicationScope +
                '}';
    }
}
