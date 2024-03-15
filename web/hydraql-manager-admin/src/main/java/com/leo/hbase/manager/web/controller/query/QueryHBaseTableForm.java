package com.leo.hbase.manager.web.controller.query;

import com.leo.hbase.manager.common.core.text.Convert;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.system.domain.SysHbaseTable;
import com.leo.hbase.manager.system.domain.SysHbaseTag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 表信息列表查询的条件表单数据模型
 *
 * @author leojie 2020/9/26 7:47 下午
 */
public class QueryHBaseTableForm implements Serializable {
    private static final long serialVersionUID = 1L;

    private String clusterId;
    /**
     * 表的命名空间
     */
    private String namespaceName;

    /**
     * HBase表名称
     */
    private String tableName;

    /**
     * 表的禁用状态
     */
    private String disableFlag;

    /**
     * 状态（0线上表 1测试表 2启用表）
     */
    private String status;

    /**
     * 表标签筛选
     */
    private String queryHBaseTagIdStr;

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

    public String getQueryHBaseTagIdStr() {
        return queryHBaseTagIdStr;
    }

    public void setQueryHBaseTagIdStr(String queryHBaseTagIdStr) {
        this.queryHBaseTagIdStr = queryHBaseTagIdStr;
    }

    public SysHbaseTable getQuerySysHbaseTableParams(String clusterId) {
        SysHbaseTable sysHbaseTable = new SysHbaseTable();
        sysHbaseTable.setClusterId(clusterId);
        sysHbaseTable.setNamespaceName(this.getNamespaceName());
        sysHbaseTable.setTableName(this.getTableName());
        sysHbaseTable.setDisableFlag(this.getDisableFlag());
        sysHbaseTable.setStatus(this.getStatus());
        String tagIdStr = this.getQueryHBaseTagIdStr();
        if (StringUtils.isNotBlank(tagIdStr)) {
            Long[] tagIds = Convert.toLongArray(tagIdStr);
            List<SysHbaseTag> tags = new ArrayList<>(tagIds.length);
            for (Long tagId : tagIds) {
                SysHbaseTag tag = new SysHbaseTag();
                tag.setTagId(tagId);
                tags.add(tag);
            }
            sysHbaseTable.setTags(tags);
        }
        return sysHbaseTable;
    }

    @Override
    public String toString() {
        return "QueryHBaseTableForm{" +
                ", clusterId='" + clusterId + '\'' +
                ", namespaceName='" + namespaceName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", disableFlag='" + disableFlag + '\'' +
                ", status='" + status + '\'' +
                ", queryHBaseTagIdStr='" + queryHBaseTagIdStr + '\'' +
                '}';
    }
}
