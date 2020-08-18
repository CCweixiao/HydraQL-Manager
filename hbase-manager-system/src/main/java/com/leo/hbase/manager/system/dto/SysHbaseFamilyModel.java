package com.leo.hbase.manager.system.dto;

import com.leo.hbase.manager.system.valid.*;

import javax.validation.GroupSequence;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author leojie 2020/8/17 10:11 下午
 */
@GroupSequence(value = {First.class, Second.class, Third.class, Fourth.class, Five.class, SysHbaseFamilyModel.class})
public class SysHbaseFamilyModel implements Serializable {
    private static final long serialVersionUID = 1L;

    //@NotBlank(message = "列簇名称不能为空", groups = {First.class})
    @Size(min = 1, max = 200, message = "列簇名称必须在1~200个字符之间", groups ={First.class})
    private String name;

    @Min(value = 1, message = "最大版本数不能小于1", groups = {Second.class})
    @Max(value = 999999, message = "最大版本数不能大于999999", groups = {Third.class})
    private Integer maxVersions = 1;

    @Min(value = 1, message = "TTL不能小于1", groups = {Fourth.class})
    @Max(value = 2147483647, message = "TTL不能超过2147483647", groups = {Five.class})
    private Integer timeToLive = 2147483647;

    private String compressionType;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxVersions() {
        return maxVersions;
    }

    public void setMaxVersions(Integer maxVersions) {
        this.maxVersions = maxVersions;
    }

    public Integer getTimeToLive() {
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

    @Override
    public String toString() {
        return "SysHbaseFamilyModel{" +
                "name='" + name + '\'' +
                ", maxVersions=" + maxVersions +
                ", timeToLive=" + timeToLive +
                ", compressionType='" + compressionType + '\'' +
                '}';
    }
}
