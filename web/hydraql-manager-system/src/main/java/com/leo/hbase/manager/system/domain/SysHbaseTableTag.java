package com.leo.hbase.manager.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.io.Serializable;

/**
 * HBaseTag对象 sys_hbase_tag
 *
 * @author leojie
 * @date 2020-08-16
 */
public class SysHbaseTableTag implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long tableId;
    private Long tagId;

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("tableId", getTableId())
                .append("tagId", getTagId())
                .toString();
    }
}
