package com.leo.hbase.manager.system.dto;

import com.google.common.base.Converter;
import com.hydraql.manager.core.hbase.schema.HTableDesc;
import com.hydraql.manager.core.util.HConstants;
import com.leo.hbase.manager.common.annotation.Excel;
import com.leo.hbase.manager.common.constant.HBasePropertyConstants;
import com.leo.hbase.manager.common.enums.HBaseDisabledFlag;
import com.leo.hbase.manager.common.enums.HBaseTableStatus;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.system.domain.SysHbaseTable;
import com.leo.hbase.manager.system.valid.*;

import javax.annotation.Nullable;
import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author leojie 2020/9/10 10:27 下午
 */
@GroupSequence(value = {First.class, Second.class, Third.class, Fourth.class, Five.class, HTableDescDto.class})
public class HTableDescDto {

    private Long tableId;
    @Excel(name = "命名空间名称")
    @NotBlank(message = "HBase命名空间不能为空", groups = {First.class})
    @Size(min = 1, max = 128, message = "HBase命名空间长度不能超过128个字符", groups = {Second.class})
    private String namespaceName;
    private String clusterId;

    @Excel(name = "HBase表名称")
    @NotBlank(message = "HBase表名称不能为空", groups = {Third.class})
    @Size(min = 1, max = 200, message = "HBase表名称必须在1~200个字符之间", groups = {Fourth.class})
    private String tableName;

    @Excel(name = "禁用标志", readConverterExp = "0=启用,2=禁用")
    private String disableFlag;
    /**
     * 状态（0线上表 1测试表 2启用表）
     */
    @Excel(name = "状态", readConverterExp = "0=线上表,1=待上线表,2=测试表,3=弃用表")
    private String status;

    private Long[] tagIds;

    /**
     * 表描述信息
     */
    private String tableDesc;

    /**
     * 表的备注信息
     */
    private String remark;

    private List<Property> propertyList;

    @NotEmpty(message = "请为表至少指定一个列簇", groups = {Five.class})
    private List<@NotNull @Valid FamilyDescDto> families;

    /**
     * 预分区方式
     */
    private String splitWay;

    /**
     * 预分区开始的key
     */
    private String startKey;
    /**
     * 预分区结束的key
     */
    private String endKey;
    /**
     * 分区数 对应预分区1
     */
    private Integer preSplitRegions;
    /**
     * 以指定分区key的方式分区，对应预分区2
     */
    private String preSplitKeys;

    /**
     * 选择指定分区方式来进行预分区，对应预分区3
     */
    private String splitGo;

    /**
     * 预分区数
     */
    private Integer numRegions;

    /**
     * HBase表创建人
     */
    private String createBy;

    /**
     * 最新修改人
     */
    private String updateBy;

    public HTableDesc convertTo() {
        HTableDescDto.TableDescDtoConvert convert = new HTableDescDto.TableDescDtoConvert();
        return convert.convert(this);
    }

    public SysHbaseTable convertToSysHbaseTable() {
        HTableDescDto.SysHbaseTableConvert convert = new HTableDescDto.SysHbaseTableConvert();
        return convert.convert(this);
    }

    public HTableDescDto convertFor(SysHbaseTable sysHbaseTable) {
        HTableDescDto.SysHbaseTableConvert convert = new HTableDescDto.SysHbaseTableConvert();
        return convert.reverse().convert(sysHbaseTable);
    }

    public static class SysHbaseTableConvert extends Converter<HTableDescDto, SysHbaseTable> {

        @Override
        protected SysHbaseTable doForward(HTableDescDto tableDescDto) {
            SysHbaseTable sysHbaseTable = new SysHbaseTable();
            sysHbaseTable.setTableId(tableDescDto.getTableId());
            sysHbaseTable.setClusterId(tableDescDto.getClusterId());
            sysHbaseTable.setNamespaceName(tableDescDto.getNamespaceName());
            String fullTableName = HConstants.getFullTableName(tableDescDto.getNamespaceName(),
                    tableDescDto.getTableName());
            sysHbaseTable.setTableName(fullTableName);
            sysHbaseTable.setCreateBy(tableDescDto.getCreateBy());
            sysHbaseTable.setUpdateBy(tableDescDto.getUpdateBy());
            sysHbaseTable.setDisableFlag(HBaseDisabledFlag.ENABLED.getCode());
            sysHbaseTable.setStatus(tableDescDto.getStatus());
            sysHbaseTable.setRemark(tableDescDto.getRemark());
            return sysHbaseTable;
        }

        @Override
        protected HTableDescDto doBackward(SysHbaseTable sysHbaseTable) {
            HTableDescDto tableDescDto = new HTableDescDto();
            tableDescDto.setTableId(sysHbaseTable.getTableId());
            tableDescDto.setClusterId(sysHbaseTable.getClusterId());
            String tableName = HConstants.getTableName(sysHbaseTable.getTableName());
            tableDescDto.setNamespaceName(sysHbaseTable.getNamespaceName());
            tableDescDto.setTableName(tableName);
            tableDescDto.setCreateBy(sysHbaseTable.getCreateBy());
            tableDescDto.setUpdateBy(sysHbaseTable.getUpdateBy());
            tableDescDto.setDisableFlag(sysHbaseTable.getDisableFlag());
            tableDescDto.setStatus(sysHbaseTable.getStatus());
            tableDescDto.setRemark(sysHbaseTable.getRemark());
            return tableDescDto;
        }
    }

    public static class TableDescDtoConvert extends Converter<HTableDescDto, HTableDesc> {
        @Override
        protected HTableDesc doForward(HTableDescDto tableDescDto) {
            String tableName = HConstants.getFullTableName(tableDescDto.getNamespaceName(), tableDescDto.getTableName());
            final List<FamilyDescDto> families = tableDescDto.getFamilies();
            HTableDesc.Builder tableDescBuilder = HTableDesc.of(tableName);
            if (families != null && !families.isEmpty()) {
                families.stream().map(FamilyDescDto::convertTo).forEach(tableDescBuilder::addFamilyDesc);
            }
            return tableDescBuilder.build();
        }

        @Override
        protected HTableDescDto doBackward(@Nullable HTableDesc tableDesc) {
            throw new AssertionError("不支持逆向转化方法!");
        }
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

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
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
        // 获取不到status，默认就是线上表
        if (StringUtils.isBlank(this.status)) {
            this.status = HBaseTableStatus.ONLINE.getCode();
        }
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

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public String getRemark() {
        if (StringUtils.isBlank(remark)) {
            remark = HBasePropertyConstants.HBASE_TABLE_DEFAULT_REMARK;
        }
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Property> getPropertyList() {
        if (propertyList == null) {
            propertyList = new ArrayList<>();
        }
        return propertyList;
    }

    public void setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }

    public List<FamilyDescDto> getFamilies() {
        return families;
    }

    public void setFamilies(List<FamilyDescDto> families) {
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
        if (null == preSplitRegions) {
            return 0;
        }
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

    public String getCreateBy() {
        if (StringUtils.isBlank(createBy)) {
            createBy = HBasePropertyConstants.HBASE_TABLE_DEFAULT_CREATED;
        }
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getSplitGo() {
        return splitGo;
    }

    public void setSplitGo(String splitGo) {
        this.splitGo = splitGo;
    }

    public Integer getNumRegions() {
        if (numRegions == null) {
            return 0;
        }
        return numRegions;
    }

    public void setNumRegions(Integer numRegions) {
        this.numRegions = numRegions;
    }

    public String getSplitWay() {
        return splitWay;
    }

    public void setSplitWay(String splitWay) {
        this.splitWay = splitWay;
    }

    @Override
    public String toString() {
        return "TableDescDto{" +
                ", tableId='" + getTableId() + '\'' +
                ", namespaceName='" + getNamespaceName() + '\'' +
                ", tableName='" + getTableName() + '\'' +
                ", disableFlag='" + getDisableFlag() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", tagIds=" + Arrays.toString(tagIds) +
                ", tableDesc='" + tableDesc + '\'' +
                ", remark='" + remark + '\'' +
                ", propertyList=" + propertyList +
                ", families=" + families +
                ", splitWay='" + splitWay + '\'' +
                ", startKey='" + startKey + '\'' +
                ", endKey='" + endKey + '\'' +
                ", preSplitRegions=" + preSplitRegions +
                ", preSplitKeys='" + preSplitKeys + '\'' +
                ", splitGo='" + splitGo + '\'' +
                ", numRegions=" + numRegions +
                ", createBy='" + createBy + '\'' +
                '}';
    }
}
