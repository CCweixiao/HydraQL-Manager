package com.leo.hbase.manager.system.domain;

import com.leo.hbase.manager.common.annotation.Excel;
import com.leo.hbase.manager.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * HBase所属Tag对象 sys_hbase_table_tag
 * 
 * @author leojie
 * @date 2020-08-16
 */
public class SysHbaseTableTag extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** HBase 表编号 */
    private Long tableId;

    /** HBase tag 编号 */
    private Long tagId;

    public void setTableId(Long tableId) 
    {
        this.tableId = tableId;
    }

    public Long getTableId() 
    {
        return tableId;
    }
    public void setTagId(Long tagId) 
    {
        this.tagId = tagId;
    }

    public Long getTagId() 
    {
        return tagId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("tableId", getTableId())
            .append("tagId", getTagId())
            .toString();
    }
}
