package com.leo.hbase.manager.system.dto;

import com.github.CCweixiao.model.FamilyDesc;
import com.github.CCweixiao.model.TableDesc;
import com.google.common.base.Converter;
import com.leo.hbase.manager.common.annotation.Excel;
import com.leo.hbase.manager.common.enums.HBaseDisabledFlag;
import com.leo.hbase.manager.common.enums.HBaseMetaTableFlag;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.system.domain.SysHbaseTag;
import com.leo.hbase.manager.system.valid.First;
import com.leo.hbase.manager.system.valid.Fourth;
import com.leo.hbase.manager.system.valid.Second;
import com.leo.hbase.manager.system.valid.Third;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author leojie 2020/9/10 10:27 下午
 */
@GroupSequence(value = {First.class, Second.class, Third.class, Fourth.class, TableDescDto.class})
public class TableDescDto {

    @Excel(name = "命名空间名称")
    @NotBlank(message = "HBase命名空间不能为空", groups = {First.class})
    @Size(min = 1, max = 128, message = "HBase命名空间长度不能超过128个字符", groups = {Second.class})
    private String namespaceId;

    private String tableId;

    @Excel(name = "HBase表名称")
    @Size(min = 1, max = 200, message = "HBase表名称必须在1~200个字符之间", groups = {Third.class})
    private String tableName;

    @Excel(name = "禁用标志", readConverterExp = "0=代表启用表,2=代表禁用表")
    private String disableFlag;
    /**
     * 状态（0线上表 1测试表 2启用表）
     */
    @Excel(name = "状态", readConverterExp = "0=线上表,1=待上线表,2=测试表,3=弃用表")
    private String status;

    private Long[] tagIds;

    private String queryHBaseTagIdStr;

    private String[] queryHBaseTagIds;

    private List<SysHbaseTag> sysHbaseTagList;

    /**
     * 元数据表（0元数据表 2用户表）
     */
    private String metaTable;

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

    private List<Property> propertyList;

    @NotEmpty(message = "请为表至少指定一个列簇", groups = {Fourth.class})
    private List<@NotNull @Valid FamilyDescDto> families;

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

    public TableDesc convertTo() {
        TableDescDto.TableDescDtoConvert convert = new TableDescDto.TableDescDtoConvert();
        return convert.convert(this);
    }

    public TableDescDto convertFor(TableDesc tableDesc) {
        TableDescDto.TableDescDtoConvert convert = new TableDescDto.TableDescDtoConvert();
        return convert.reverse().convert(tableDesc);
    }


    public static class TableDescDtoConvert extends Converter<TableDescDto, TableDesc> {

        @Override
        protected TableDesc doForward(TableDescDto tableDescDto) {
            TableDesc tableDesc = new TableDesc();
            tableDesc.setNamespaceName(tableDescDto.getNamespaceId());
            tableDesc.setTableName(tableDescDto.getTableName());
            tableDesc.setStartKey(tableDescDto.getStartKey());
            tableDesc.setEndKey(tableDescDto.getEndKey());
            tableDesc.setPreSplitRegions(tableDescDto.getPreSplitRegions());
            tableDesc.setPreSplitKeys(tableDescDto.getPreSplitKeys());
            final List<Property> propertyList = tableDescDto.getPropertyList();
            if ( propertyList!= null && !propertyList.isEmpty()) {
                Map<String, String> props = new HashMap<>(propertyList.size());
                propertyList.forEach(property -> props.put(property.getKey(), property.getValue()));
                tableDesc.setTableProps(props);
            }
            final List<FamilyDesc> familyDescList = tableDescDto.getFamilies().stream().map(FamilyDescDto::convertTo).collect(Collectors.toList());
            tableDesc.setFamilyDescList(familyDescList);
            return tableDesc;
        }

        @Override
        protected TableDescDto doBackward(TableDesc tableDesc) {
            TableDescDto tableDescDto = new TableDescDto();
            tableDescDto.setNamespaceId(tableDesc.getNamespaceName());
            tableDescDto.setTableName(tableDesc.getTableName());
            tableDescDto.setTableId(tableDesc.getTableName());
            String metaTable = tableDesc.isMetaTable() ? HBaseMetaTableFlag.META_TABLE.getCode() : HBaseMetaTableFlag.USER_TABLE.getCode();
            tableDescDto.setMetaTable(metaTable);
            String disableStatus = tableDesc.isDisabled() ? HBaseDisabledFlag.DISABLED.getCode() : HBaseDisabledFlag.ENABLED.getCode();
            tableDescDto.setDisableFlag(disableStatus);
            String desc = StringUtils.getStringByEnter(110, tableDesc.getTableDesc());
            tableDescDto.setTableDesc(desc);
            return tableDescDto;
        }
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
    }


    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
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

    public String getQueryHBaseTagIdStr() {
        return queryHBaseTagIdStr;
    }

    public void setQueryHBaseTagIdStr(String queryHBaseTagIdStr) {
        this.queryHBaseTagIdStr = queryHBaseTagIdStr;
    }

    public String[] getQueryHBaseTagIds() {
        return queryHBaseTagIds;
    }

    public void setQueryHBaseTagIds(String[] queryHBaseTagIds) {
        this.queryHBaseTagIds = queryHBaseTagIds;
    }

    public List<SysHbaseTag> getSysHbaseTagList() {
        return sysHbaseTagList;
    }

    public void setSysHbaseTagList(List<SysHbaseTag> sysHbaseTagList) {
        this.sysHbaseTagList = sysHbaseTagList;
    }

    public String getMetaTable() {
        return metaTable;
    }

    public void setMetaTable(String metaTable) {
        this.metaTable = metaTable;
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

    public List<Property> getPropertyList() {
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

    @Override
    public String toString() {
        return "TableDescDto{" +
                "namespaceId='" + namespaceId + '\'' +
                ", tableId='" + tableId + '\'' +
                ", tableName='" + tableName + '\'' +
                ", disableFlag='" + disableFlag + '\'' +
                ", status='" + status + '\'' +
                ", tagIds=" + Arrays.toString(tagIds) +
                ", queryHBaseTagIdStr='" + queryHBaseTagIdStr + '\'' +
                ", queryHBaseTagIds=" + Arrays.toString(queryHBaseTagIds) +
                ", sysHbaseTagList=" + sysHbaseTagList +
                ", metaTable='" + metaTable + '\'' +
                ", onlineRegions=" + onlineRegions +
                ", offlineRegions=" + offlineRegions +
                ", failedRegions=" + failedRegions +
                ", splitRegions=" + splitRegions +
                ", otherRegions=" + otherRegions +
                ", tableDesc='" + tableDesc + '\'' +
                ", remark='" + remark + '\'' +
                ", propertyList=" + propertyList +
                ", families=" + families +
                ", startKey='" + startKey + '\'' +
                ", endKey='" + endKey + '\'' +
                ", preSplitRegions=" + preSplitRegions +
                ", preSplitKeys='" + preSplitKeys + '\'' +
                '}';
    }
}
