package com.leo.hbase.manager.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.leo.hbase.manager.common.annotation.Excel;
import com.leo.hbase.manager.common.core.domain.BaseEntity;

/**
 * 用户和HBase的关联对象 sys_user_hbase_table
 * 
 * @author leojie
 * @date 2021-02-06
 */
public class SysUserHbaseTable extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    /** 集群标识符号 */
    private String clusterAlias;

    /** 表名加密后的ID */
    private String tableId;

    /** 表名 */
    @Excel(name = "表名")
    private String tableName;

    /** 命名空间 */
    @Excel(name = "命名空间")
    private String namespaceName;

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setClusterAlias(String clusterAlias) 
    {
        this.clusterAlias = clusterAlias;
    }

    public String getClusterAlias() 
    {
        return clusterAlias;
    }
    public void setTableId(String tableId) 
    {
        this.tableId = tableId;
    }

    public String getTableId() 
    {
        return tableId;
    }
    public void setTableName(String tableName) 
    {
        this.tableName = tableName;
    }

    public String getTableName() 
    {
        return tableName;
    }
    public void setNamespaceName(String namespaceName) 
    {
        this.namespaceName = namespaceName;
    }

    public String getNamespaceName() 
    {
        return namespaceName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("clusterAlias", getClusterAlias())
            .append("tableId", getTableId())
            .append("tableName", getTableName())
            .append("namespaceName", getNamespaceName())
            .toString();
    }
}
