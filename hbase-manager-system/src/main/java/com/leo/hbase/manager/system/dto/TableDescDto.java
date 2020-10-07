package com.leo.hbase.manager.system.dto;

import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.model.FamilyDesc;
import com.github.CCweixiao.model.TableDesc;
import com.google.common.base.Converter;
import com.leo.hbase.manager.common.annotation.Excel;
import com.leo.hbase.manager.common.constant.HBasePropertyConstants;
import com.leo.hbase.manager.common.core.text.Convert;
import com.leo.hbase.manager.common.enums.HBaseDisabledFlag;
import com.leo.hbase.manager.common.enums.HBaseMetaTableFlag;
import com.leo.hbase.manager.common.enums.HBaseTableStatus;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.common.utils.security.StrEnDeUtils;
import com.leo.hbase.manager.system.domain.SysHbaseTag;
import com.leo.hbase.manager.system.valid.*;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author leojie 2020/9/10 10:27 下午
 */
@GroupSequence(value = {First.class, Second.class, Third.class, Fourth.class, Five.class, TableDescDto.class})
public class TableDescDto {

    @Excel(name = "命名空间名称")
    @NotBlank(message = "HBase命名空间不能为空", groups = {First.class})
    @Size(min = 1, max = 128, message = "HBase命名空间长度不能超过128个字符", groups = {Second.class})
    private String namespaceId;

    private String namespaceName;

    private String tableId;

    @Excel(name = "HBase表名称")
    @NotBlank(message = "HBase表名称不能为空", groups = {Third.class})
    @Size(min = 1, max = 200, message = "HBase表名称必须在1~200个字符之间", groups = {Fourth.class})
    private String tableName;

    @Excel(name = "禁用标志", readConverterExp = "0=代表启用表,2=代表禁用表")
    private String disableFlag;
    /**
     * 状态（0线上表 1测试表 2启用表）
     */
    @Excel(name = "状态", readConverterExp = "0=线上表,1=待上线表,2=测试表,3=弃用表")
    private String status;

    private Integer[] tagIds;

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
     * HBase表的创建时间
     */
    private Long createTimestamp;

    /**
     * 最新修改人
     */
    private String lastUpdateBy;

    /**
     * 最新修改时间戳
     */
    private Long lastUpdateTimestamp;

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
            // 设置命名空间和表名
            tableDesc.setNamespaceName(tableDescDto.getNamespaceId());
            tableDesc.setTableName(tableDescDto.getTableName());
            final String disableFlag = tableDescDto.getDisableFlag();
            if (HBaseDisabledFlag.DISABLED.getCode().equals(disableFlag)) {
                tableDesc.setDisabled(true);
            } else if (HBaseDisabledFlag.ENABLED.getCode().equals(disableFlag)) {
                tableDesc.setDisabled(false);
            } else {
                throw new HBaseOperationsException("HBase表的禁用状态，0启用，2禁用");
            }
            List<Property> propertyList = tableDescDto.getPropertyList();

            propertyList.add(new Property(HBasePropertyConstants.STATUS, tableDescDto.getStatus()));
            propertyList.add(new Property(HBasePropertyConstants.REMARK, tableDescDto.getRemark()));
            String tagIdStr = "";
            final Integer[] tagIds = tableDescDto.getTagIds();
            if (tagIds != null && tagIds.length > 0) {
                tagIdStr = StringUtils.join(tagIds, HBasePropertyConstants.HBASE_TABLE_PROP_SPLIT);
            }
            propertyList.add(new Property(HBasePropertyConstants.TAG_IDS, tagIdStr));
            propertyList.add(new Property(HBasePropertyConstants.CREATE_BY, tableDescDto.getCreateBy()));
            propertyList.add(new Property(HBasePropertyConstants.CREATE_TIMESTAMP, tableDescDto.getCreateTimestamp().toString()));
            propertyList.add(new Property(HBasePropertyConstants.LAST_UPDATE_BY, tableDescDto.getLastUpdateBy()));
            propertyList.add(new Property(HBasePropertyConstants.LAST_UPDATE_TIMESTAMP, tableDescDto.getLastUpdateTimestamp().toString()));
            // 遍历添加属性
            propertyList.forEach(property -> tableDesc.addProp(property.getKey(), property.getValue()));

            final List<FamilyDescDto> families = tableDescDto.getFamilies();
            if (families != null && !families.isEmpty()) {
                final List<FamilyDesc> familyDescList = families.stream().map(FamilyDescDto::convertTo).collect(Collectors.toList());
                tableDesc.setFamilyDescList(familyDescList);
            }

            return tableDesc;
        }

        @Override
        protected TableDescDto doBackward(TableDesc tableDesc) {
            TableDescDto tableDescDto = new TableDescDto();
            tableDescDto.setNamespaceId(tableDesc.getNamespaceName());
            tableDescDto.setNamespaceName(tableDesc.getNamespaceName());
            tableDescDto.setTableName(tableDesc.getTableName());
            tableDescDto.setTableId(StrEnDeUtils.encrypt(tableDesc.getTableName()));

            String metaTable = tableDesc.isMetaTable() ? HBaseMetaTableFlag.META_TABLE.getCode() : HBaseMetaTableFlag.USER_TABLE.getCode();
            tableDescDto.setMetaTable(metaTable);
            String disableStatus = tableDesc.isDisabled() ? HBaseDisabledFlag.DISABLED.getCode() : HBaseDisabledFlag.ENABLED.getCode();
            tableDescDto.setDisableFlag(disableStatus);
            String desc = StringUtils.getStringByEnter(110, tableDesc.getTableDesc());
            tableDescDto.setTableDesc(desc);

            final Map<String, String> tableProps = tableDesc.getTableProps();
            if (tableProps == null || tableProps.isEmpty()) {
                // 初始化配置
                tableDescDto.setStatus(HBaseTableStatus.ONLINE.getCode());
                tableDescDto.setRemark(HBasePropertyConstants.HBASE_TABLE_DEFAULT_REMARK);
                tableDescDto.setTagIds(new Integer[]{});
                tableDescDto.setSysHbaseTagList(new ArrayList<>());
                tableDescDto.setCreateBy(HBasePropertyConstants.HBASE_TABLE_DEFAULT_CREATED);
                tableDescDto.setCreateTimestamp(0L);
                tableDescDto.setLastUpdateBy(HBasePropertyConstants.HBASE_TABLE_DEFAULT_CREATED);
                tableDescDto.setLastUpdateTimestamp(0L);

            } else {
                tableDescDto.setStatus(tableProps.getOrDefault(HBasePropertyConstants.STATUS, HBaseTableStatus.ONLINE.getCode()));
                tableDescDto.setRemark(tableProps.getOrDefault(HBasePropertyConstants.REMARK, HBasePropertyConstants.HBASE_TABLE_DEFAULT_REMARK));
                String tagIds = tableProps.getOrDefault(HBasePropertyConstants.TAG_IDS, "");

                if (StringUtils.isBlank(tagIds)) {
                    tableDescDto.setTagIds(new Integer[]{});
                } else {
                    Integer[] tagIdLongs = Convert.toIntArray(tagIds);
                    tableDescDto.setTagIds(tagIdLongs);
                }
                tableDescDto.setCreateBy(tableProps.getOrDefault(HBasePropertyConstants.CREATE_BY, HBasePropertyConstants.HBASE_TABLE_DEFAULT_CREATED));
                tableDescDto.setCreateTimestamp(Long.parseLong(tableProps.getOrDefault(HBasePropertyConstants.CREATE_TIMESTAMP, "0")));
                tableDescDto.setLastUpdateBy(tableProps.getOrDefault(HBasePropertyConstants.LAST_UPDATE_BY, HBasePropertyConstants.HBASE_TABLE_DEFAULT_CREATED));
                tableDescDto.setLastUpdateTimestamp(Long.parseLong(tableProps.getOrDefault(HBasePropertyConstants.LAST_UPDATE_TIMESTAMP, "0")));
            }

            return tableDescDto;
        }
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
    }

    public String getNamespaceName() {
        return namespaceName;
    }

    public void setNamespaceName(String namespaceName) {
        this.namespaceName = namespaceName;
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
        // 获取不到status，默认就是线上表
        if (StringUtils.isBlank(this.status)) {
            this.status = HBaseTableStatus.ONLINE.getCode();
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer[] getTagIds() {
        return tagIds;
    }

    public void setTagIds(Integer[] tagIds) {
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

    public Long getCreateTimestamp() {
        if (createTimestamp == null) {
            return 0L;
        }
        return createTimestamp;
    }

    public void setCreateTimestamp(Long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getLastUpdateBy() {
        if (StringUtils.isBlank(lastUpdateBy)) {
            lastUpdateBy = HBasePropertyConstants.HBASE_TABLE_DEFAULT_CREATED;
        }
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Long getLastUpdateTimestamp() {
        if (this.lastUpdateTimestamp == null) {
            return 0L;
        }
        return this.lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(Long lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
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
                "namespaceId='" + getNamespaceId() + '\'' +
                ", namespaceName='" + getNamespaceName() + '\'' +
                ", tableId='" + getTableId() + '\'' +
                ", tableName='" + getTableName() + '\'' +
                ", disableFlag='" + getDisableFlag() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", tagIds=" + Arrays.toString(tagIds) +
                ", queryHBaseTagIdStr='" + getQueryHBaseTagIdStr() + '\'' +
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
                ", splitWay='" + splitWay + '\'' +
                ", startKey='" + startKey + '\'' +
                ", endKey='" + endKey + '\'' +
                ", preSplitRegions=" + preSplitRegions +
                ", preSplitKeys='" + preSplitKeys + '\'' +
                ", splitGo='" + splitGo + '\'' +
                ", numRegions=" + numRegions +
                ", createBy='" + createBy + '\'' +
                ", createTimestamp=" + createTimestamp +
                ", lastUpdateBy='" + lastUpdateBy + '\'' +
                ", lastUpdateTimestamp=" + lastUpdateTimestamp +
                '}';
    }
}
