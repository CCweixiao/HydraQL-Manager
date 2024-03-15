package com.leo.hbase.manager.web.controller.system;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.leo.hbase.manager.common.core.controller.BaseController;
import com.leo.hbase.manager.common.exception.BusinessException;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.common.utils.security.StrEnDeUtils;
import com.leo.hbase.manager.common.utils.spring.SpringUtils;
import com.leo.hbase.manager.framework.shiro.session.OnlineSession;
import com.leo.hbase.manager.framework.shiro.session.OnlineSessionDAO;
import com.leo.hbase.manager.system.domain.SysHbaseTable;
import com.leo.hbase.manager.system.domain.schema.HTableColumn;
import com.leo.hbase.manager.system.domain.SysHbaseTableSchema;
import com.leo.hbase.manager.system.domain.SysHbaseTag;
import com.leo.hbase.manager.system.domain.schema.HTableSchema;
import com.leo.hbase.manager.system.service.ISysHbaseClusterService;
import com.leo.hbase.manager.system.service.ISysHbaseTagService;
import org.apache.commons.lang.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author leojie 2020/9/26 4:19 下午
 */
public class SysHbaseBaseController extends BaseController {
    @Autowired
    public ISysHbaseTagService sysHbaseTagService;

    @Autowired
    public ISysHbaseClusterService sysHbaseClusterService;

    private static final Logger LOG = LoggerFactory.getLogger(SysHbaseBaseController.class);

    public String getTableIdByName(String tableName) {
        return StrEnDeUtils.encrypt(tableName);
    }

    public String parseTableNameFromTableId(String tableId) {
        final String tableName = StrEnDeUtils.decrypt(tableId);
        if (StringUtils.isBlank(tableName)) {
            throw new BusinessException("从加密的tableId[" + tableId + "]中无法解析出表名称");
        }
        return tableName;
    }

    public String getSessionId() {
        HttpSession session = getSession();
        if (session == null) {
            throw new BusinessException("当前会话已经过期，请登录重试");
        }
        return session.getId();
    }

    public String clusterCodeOfCurrentSession() {
        HttpSession session = getSession();
        if (session == null) {
            throw new BusinessException("当前会话已经过期，请登录重试");
        }
        String sessionId = session.getId();
        OnlineSessionDAO onlineSessionDAO = SpringUtils.getBean(OnlineSessionDAO.class);
        OnlineSession onlineSession = (OnlineSession) onlineSessionDAO.readSession(sessionId);
        if (onlineSession == null) {
            throw new BusinessException("当前会话已经过期，请登录重试");
        }
        final String cluster = onlineSession.getCluster();
        if (StringUtils.isBlank(cluster)) {
            return sysHbaseClusterService.getAllOnlineClusterIds().get(0);
        }
        LOG.info("当前用户{},拥有的sessionID是{},操作的集群是{}", onlineSession.getLoginName(), sessionId, cluster);
        return cluster;
    }

    /*public List<HTableDescDto> filterFamilyDescList(QueryHBaseTableForm queryHBaseTableForm, List<HTableDesc> tableDescList) {
        return tableDescList.stream().map(tableDesc -> {
            final HTableDescDto HTableDescDto = new HTableDescDto().convertFor(tableDesc);
            final Integer[] tagIds = HTableDescDto.getTagIds();
            HTableDescDto.setSysHbaseTagList(getSysHbaseTagByLongIds(tagIds));
            return HTableDescDto;
        }).filter(HTableDescDto -> {
            if (StringUtils.isNotBlank(queryHBaseTableForm.getNamespaceName())) {
                return HTableDescDto.getNamespaceName().equals(queryHBaseTableForm.getNamespaceName());
            }
            return true;
        }).filter(HTableDescDto -> {
            if (StringUtils.isNotBlank(queryHBaseTableForm.getTableName())) {
                return HTableDescDto.getTableName().toLowerCase().contains(queryHBaseTableForm.getTableName().toLowerCase());
            }
            return true;
        }).filter(HTableDescDto -> {
            if (StringUtils.isNotBlank(queryHBaseTableForm.getDisableFlag())) {
                return HTableDescDto.getDisableFlag().equals(queryHBaseTableForm.getDisableFlag());
            }
            return true;
        }).filter(HTableDescDto -> {
            if (StringUtils.isNotBlank(queryHBaseTableForm.getStatus())) {
                return HTableDescDto.getStatus().equals(queryHBaseTableForm.getStatus());
            }
            return true;
        }).filter(HTableDescDto -> {
            if (StringUtils.isNotBlank(queryHBaseTableForm.getQueryHBaseTagIdStr())) {
                final String tagIdStr = queryHBaseTableForm.getQueryHBaseTagIdStr();
                Integer[] queryTagIds = Convert.toIntArray(tagIdStr);

                if (HTableDescDto.getTagIds() != null && HTableDescDto.getTagIds().length > 0) {
                    final int[] same = ArrUtils.intersection2(queryTagIds, HTableDescDto.getTagIds());
                    return same.length > 0;
                } else {
                    return false;
                }
            }
            return true;
        }).sorted(Comparator.comparing(HTableDescDto::getLastUpdateTimestamp).reversed()).collect(Collectors.toList());

    }*/

    public List<SysHbaseTag> getSysHbaseTagByLongIds(Long[] tagIds) {
        if (tagIds == null || tagIds.length < 1) {
            return new ArrayList<>();
        }
        final String tagIdStr = StringUtils.join(tagIds, ",");
        final List<SysHbaseTag> sysHbaseTags = sysHbaseTagService.selectSysHbaseTagListByIds(tagIdStr);
        if (sysHbaseTags == null) {
            return new ArrayList<>();
        } else {
            return sysHbaseTags;
        }
    }

    public String[] parseFamilyAndQualifierName(String familyName) {
        if (StringUtils.isBlank(familyName)) {
            return null;
        }
        final String[] families = familyName.split(":");
        if (families.length != 2) {
            return null;
        }
        return families;
    }


    public String[] parseTableFamilyRowFromStrId(String tableAndFamilyAndRow) {
        if (StringUtils.isBlank(tableAndFamilyAndRow)) {
            return null;
        }
        final String[] tableAndFamilyAndRows = tableAndFamilyAndRow.split(":");
        String tableName = "";
        String familyName = "";
        String qualifier = "";
        String rowName = "";

        final int tableAndFamilyAndRowsLength = tableAndFamilyAndRows.length;

        if (tableAndFamilyAndRowsLength < 5) {
            return null;
        } else if (tableAndFamilyAndRowsLength == 5) {
            tableName = tableAndFamilyAndRows[0] + ":" + tableAndFamilyAndRows[1];
            familyName = tableAndFamilyAndRows[2];
            qualifier = tableAndFamilyAndRows[3];
            rowName = tableAndFamilyAndRows[4];
        } else {
            tableName = tableAndFamilyAndRows[0] + ":" + tableAndFamilyAndRows[1];
            familyName = tableAndFamilyAndRows[2];
            qualifier = tableAndFamilyAndRows[3];
            StrBuilder sb = new StrBuilder(rowName);
            for (int i = 4; i < tableAndFamilyAndRowsLength; i++) {
                sb.append(tableAndFamilyAndRows[i]);
                if (i < tableAndFamilyAndRowsLength - 1) {
                    sb.append(":");
                }
            }
            rowName = sb.toString();
        }

        return new String[]{tableName, rowName, familyName, qualifier};
    }

    /*public HBaseTableSchema parseHBaseTableSchema(String tableSchemaJson) {
        if (StringUtils.isBlank(tableSchemaJson)) {
            throw new HBaseOperationsException("定义HBase表schema的json字符串不能为空！");
        }
        HTableSchema tableSchema;
        try {
            tableSchema = JSON.parseObject(tableSchemaJson, HTableSchema.class);
        } catch (JSONException e) {
            throw new HBaseOperationsException(e);
        }

        if (tableSchema == null) {
            throw new HBaseOperationsException("定义HBase表schema的json字符串解析失败，请检查格式是否符合系统定义的格式。\n" + defaultTableSchemaJsonFormat());
        }

        HBaseTableSchema.Builder tableSchemaBuilder = HBaseTableSchema.of(tableSchema.getTableName())
                *//*.scanBatch(100)
                .scanCaching(1000)
                .deleteBatch(100)
                .scanCacheBlocks(false)*//*;

        List<HTableColumn> columnList = tableSchema.getColumnList();
        if (columnList == null || columnList.isEmpty()) {
            throw new HBaseOperationsException("需要在定义HBase表schema的json字符串中指定至少一个列！");
        }
        String defaultFamily = tableSchema.getDefaultFamily();
        int rowNum = 0;
        for (HTableColumn tableColumn : columnList) {
            String cf = tableColumn.getFamilyName();
            String colName = tableColumn.getColumnName();
            if (StringUtils.isBlank(defaultFamily) && StringUtils.isBlank(cf)) {
                throw new HBaseOperationsException("如果默认列簇名为空，请为列指定列簇名！");
            }
            if (StringUtils.isBlank(colName)) {
                throw new HBaseOperationsException("列名不能为空！");
            }
            if (StringUtils.isNotBlank(cf)) {
                defaultFamily = cf;
            }
            String columnType = tableColumn.getColumnType();
            if (StringUtils.isBlank(columnType)) {
                columnType = "string";
            }
            ColumnType columnTypeEnum = getColumnType(columnType);
            if (tableColumn.isColumnIsRow()) {
                rowNum += 1;
                tableSchemaBuilder.addRow(colName, columnTypeEnum);
            } else {
                tableSchemaBuilder.addColumn(defaultFamily, colName, columnTypeEnum);
            }
        }

        if (rowNum > 1) {
            throw new HBaseOperationsException("row key的数量不能大于1！");
        }
        return tableSchemaBuilder.build();
    }*/

    private String defaultTableSchemaJsonFormat() {
        return "{\n" +
                "\t\"tableName\": \"test_table\",\n" +
                "\t\"defaultFamily\": \"cf\",\n" +
                "\t\"columnList\": [{\n" +
                "\t    \"familyName\": \"\",\n" +
                "\t    \"columnName\": \"rowKey\",\n" +
                "\t    \"columnType\": \"string\",\n" +
                "\t    \"columnIsRow\": \"true\"\n" +
                "\t},{\n" +
                "\t    \"familyName\": \"cf\",\n" +
                "\t    \"columnName\": \"name\",\n" +
                "\t    \"columnType\": \"string\",\n" +
                "\t    \"columnIsRow\": \"true\"\n" +
                "\t},]\n" +
                "}";
    }

/*    private ColumnType getColumnType(String columnType) {
        for (ColumnType value : ColumnType.values()) {
            if (value.getTypeName().equalsIgnoreCase(columnType)) {
                return value;
            }
        }
        throw new HBaseOperationsException(String.format("未支持的列类型[%s]！", columnType));
    }*/

    protected void checkSysHbaseTableExists(SysHbaseTable sysHbaseTable) {
        if (sysHbaseTable == null || sysHbaseTable.getTableId() == null || sysHbaseTable.getTableId() < 1) {
            throw new BusinessException("待操作HBase表元数据不存在");
        }
    }
}
