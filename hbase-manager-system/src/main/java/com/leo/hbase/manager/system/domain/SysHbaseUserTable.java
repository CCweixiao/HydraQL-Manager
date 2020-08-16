package com.leo.hbase.manager.system.domain;

import com.leo.hbase.manager.common.annotation.Excel;
import com.leo.hbase.manager.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * HBase所属用户对象 sys_hbase_user_table
 * 
 * @author leojie
 * @date 2020-08-16
 */
public class SysHbaseUserTable extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    /** HBase 表编号 */
    private Long tableId;

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setTableId(Long tableId) 
    {
        this.tableId = tableId;
    }

    public Long getTableId() 
    {
        return tableId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("tableId", getTableId())
            .toString();
    }
}
