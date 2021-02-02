package com.leo.hbase.manager.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.CCweixiao.hql.config.DefaultHBaseTableConfig;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.model.TableDesc;
import com.github.CCweixiao.util.StrUtil;
import com.github.CCwexiao.dsl.config.HBaseColumnSchema;
import com.github.CCwexiao.dsl.config.HBaseTableConfig;
import com.github.CCwexiao.dsl.config.HBaseTableSchema;
import com.leo.hbase.manager.common.core.controller.BaseController;
import com.leo.hbase.manager.common.core.text.Convert;
import com.leo.hbase.manager.common.exception.BusinessException;
import com.leo.hbase.manager.common.utils.ArrUtils;
import com.leo.hbase.manager.common.utils.HBaseConfigUtils;
import com.leo.hbase.manager.common.utils.JSONUtil;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.common.utils.security.StrEnDeUtils;
import com.leo.hbase.manager.common.utils.spring.SpringUtils;
import com.leo.hbase.manager.framework.shiro.session.OnlineSession;
import com.leo.hbase.manager.framework.shiro.session.OnlineSessionDAO;
import com.leo.hbase.manager.system.domain.SysHbaseTag;
import com.leo.hbase.manager.system.dto.TableDescDto;
import com.leo.hbase.manager.system.service.ISysHbaseTagService;
import com.leo.hbase.manager.web.controller.query.QueryHBaseTableForm;
import org.apache.commons.lang.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leojie 2020/9/26 4:19 下午
 */
public class SysHbaseBaseController extends BaseController {
    @Autowired
    public ISysHbaseTagService sysHbaseTagService;

    private static final Logger LOG = LoggerFactory.getLogger(SysHbaseBaseController.class);

    public String parseTableNameFromTableId(String tableId) {
        final String tableName = StrEnDeUtils.decrypt(tableId);
        if (StringUtils.isBlank(tableName)) {
            throw new HBaseOperationsException("从加密的tableId[" + tableId + "]中无法解析出表名称");
        }
        return tableName;
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
        if (StrUtil.isBlank(cluster)) {
            return HBaseConfigUtils.getAllClusterAlias().get(0);
        }
        LOG.info("当前用户{},拥有的sessionID是{},操作的集群是{}", onlineSession.getLoginName(), sessionId, cluster);
        return cluster;
    }

    public List<TableDescDto> filterFamilyDescList(QueryHBaseTableForm queryHBaseTableForm, List<TableDesc> tableDescList) {
        return tableDescList.stream().map(tableDesc -> {
            final TableDescDto tableDescDto = new TableDescDto().convertFor(tableDesc);
            final Integer[] tagIds = tableDescDto.getTagIds();
            tableDescDto.setSysHbaseTagList(getSysHbaseTagByLongIds(tagIds));
            return tableDescDto;
        }).filter(tableDescDto -> {
            if (StringUtils.isNotBlank(queryHBaseTableForm.getNamespaceName())) {
                return tableDescDto.getNamespaceName().equals(queryHBaseTableForm.getNamespaceName());
            }
            return true;
        }).filter(tableDescDto -> {
            if (StringUtils.isNotBlank(queryHBaseTableForm.getTableName())) {
                return tableDescDto.getTableName().toLowerCase().contains(queryHBaseTableForm.getTableName().toLowerCase());
            }
            return true;
        }).filter(tableDescDto -> {
            if (StringUtils.isNotBlank(queryHBaseTableForm.getDisableFlag())) {
                return tableDescDto.getDisableFlag().equals(queryHBaseTableForm.getDisableFlag());
            }
            return true;
        }).filter(tableDescDto -> {
            if (StringUtils.isNotBlank(queryHBaseTableForm.getStatus())) {
                return tableDescDto.getStatus().equals(queryHBaseTableForm.getStatus());
            }
            return true;
        }).filter(tableDescDto -> {
            if (StringUtils.isNotBlank(queryHBaseTableForm.getQueryHBaseTagIdStr())) {
                final String tagIdStr = queryHBaseTableForm.getQueryHBaseTagIdStr();
                Integer[] queryTagIds = Convert.toIntArray(tagIdStr);

                if (tableDescDto.getTagIds() != null && tableDescDto.getTagIds().length > 0) {
                    final int[] same = ArrUtils.intersection2(queryTagIds, tableDescDto.getTagIds());
                    return same.length > 0;
                } else {
                    return false;
                }
            }
            return true;
        }).sorted(Comparator.comparing(TableDescDto::getLastUpdateTimestamp).reversed()).collect(Collectors.toList());

    }

    public List<SysHbaseTag> getSysHbaseTagByLongIds(Integer[] tagIds) {
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

    public HBaseTableConfig parseHBaseConfigFromTableSchema(String tableSchema) {
        final JSONObject schemaJson = JSON.parseObject(tableSchema);
        if (schemaJson == null) {
            throw new HBaseOperationsException("HBaseTableSchemaJson解析失败");
        }
        String tableName = JSONUtil.getStrV(schemaJson, "tableName");
        String defaultFamily = JSONUtil.getStrVOr(schemaJson, "defaultFamily", "");
        HBaseTableSchema hBaseTableSchema = new HBaseTableSchema();
        hBaseTableSchema.setTableName(tableName);
        hBaseTableSchema.setDefaultFamily(defaultFamily);
        final JSONArray columnSchemaArr = JSONUtil.getJsonArr(schemaJson, "columnSchema");
        List<HBaseColumnSchema> columnSchemaList = new ArrayList<>(columnSchemaArr.size());

        for (int i = 0; i < columnSchemaArr.size(); i++) {
            HBaseColumnSchema columnSchema = new HBaseColumnSchema();
            final JSONObject jsonObject = columnSchemaArr.getJSONObject(i);
            columnSchema.setFamily(jsonObject.getString("family"));
            columnSchema.setQualifier(jsonObject.getString("qualifier"));
            columnSchema.setTypeName(jsonObject.getString("typeName"));
            columnSchemaList.add(columnSchema);
        }

        return new DefaultHBaseTableConfig(hBaseTableSchema, columnSchemaList);
    }
}
