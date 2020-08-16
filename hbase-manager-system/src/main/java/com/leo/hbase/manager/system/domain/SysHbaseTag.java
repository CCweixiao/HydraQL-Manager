package com.leo.hbase.manager.system.domain;

import com.leo.hbase.manager.common.annotation.Excel;
import com.leo.hbase.manager.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * HBaseTag对象 sys_hbase_tag
 *
 * @author leojie
 * @date 2020-08-16
 */
public class SysHbaseTag extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * tag的编号
     */
    private Long tagId;

    /**
     * HBase表Tag名称
     */
    @Excel(name = "HBase表Tag名称")
    private String tagName;

    /**HBase是否存在标签，默认不存在**/
    private boolean flag = false;

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("tagId", getTagId())
                .append("tagName", getTagName())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
