package com.leo.hbase.manager.system.dto;

import com.github.CCweixiao.constant.HMHBaseConstant;
import com.github.CCweixiao.model.SnapshotDesc;
import com.google.common.base.Converter;
import com.leo.hbase.manager.common.annotation.Excel;
import com.leo.hbase.manager.common.utils.DateUtils;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.common.utils.security.StrEnDeUtils;
import com.leo.hbase.manager.system.valid.*;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author leojie 2020/11/14 7:39 下午
 */
@GroupSequence(value = {First.class, Second.class, Third.class, Fourth.class, SnapshotDescDto.class})
public class SnapshotDescDto {

    @Excel(name = "HBase表名")
    @NotBlank(message = "HBase表名不能为空", groups = {First.class})
    @Size(min = 1, max = 1024, message = "HBase表名长度不能超过1024个字符", groups = {Second.class})
    private String tableName;

    private String snapshotId;

    @Excel(name = "HBase快照名")
    @Size(max = 1024, message = "HBase快照名称的长度不能超过1024个字符", groups = {Third.class})
    @Pattern(regexp = "[a-zA-Z0-9_]+", message = "HBase快照名称只能是数字、字母和下划线的组合", groups = {Fourth.class})
    private String snapshotName;

    @Excel(name = "HBase快照创建时间")
    private long createTime;

    public SnapshotDesc convertTo() {
        SnapshotDescDto.SnapshotDescDtoConvert convert = new SnapshotDescDto.SnapshotDescDtoConvert();
        return convert.convert(this);
    }

    public SnapshotDescDto convertFor(SnapshotDesc snapshotDesc) {
        SnapshotDescDto.SnapshotDescDtoConvert convert = new SnapshotDescDto.SnapshotDescDtoConvert();
        return convert.reverse().convert(snapshotDesc);
    }

    public static class SnapshotDescDtoConvert extends Converter<SnapshotDescDto, SnapshotDesc> {

        @Override
        protected SnapshotDesc doForward(SnapshotDescDto snapshotDescDto) {
            SnapshotDesc snapshotDesc = new SnapshotDesc();

            snapshotDesc.setTableName(snapshotDescDto.getTableName());
            snapshotDesc.setSnapshotName(snapshotDescDto.getSnapshotName());
            return snapshotDesc;
        }

        @Override
        protected SnapshotDescDto doBackward(SnapshotDesc snapshotDesc) {
            SnapshotDescDto snapshotDescDto = new SnapshotDescDto();
            snapshotDescDto.setTableName(snapshotDesc.getTableName());
            snapshotDescDto.setSnapshotName(snapshotDesc.getSnapshotName());
            snapshotDescDto.setSnapshotId(StrEnDeUtils.encrypt(snapshotDesc.getSnapshotName()));
            snapshotDescDto.setCreateTime(snapshotDesc.getCreateTime());
            return snapshotDescDto;
        }
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }

    public String getSnapshotName() {
        if(StringUtils.isBlank(snapshotName)){
            return tableName.replaceAll(HMHBaseConstant.TABLE_NAME_SPLIT_CHAR,"_")+"_"+ DateUtils.dateTimeNow();
        }
        return snapshotName;
    }

    public void setSnapshotName(String snapshotName) {
        this.snapshotName = snapshotName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SnapshotDescDto{" +
                ", tableName='" + tableName + '\'' +
                ", snapshotId='" + snapshotId + '\'' +
                ", snapshotName='" + snapshotName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
