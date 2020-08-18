package com.leo.hbase.manager.system.domain;

import com.leo.hbase.manager.common.annotation.Excel;
import com.leo.hbase.manager.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * HBaseNamespace对象 sys_hbase_namespace
 *
 * @author leojie
 * @date 2020-08-16
 */
public class SysHbaseNamespace extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long namespaceId;

    /**
     * HBase所属namespace的名称
     */
    @Excel(name = "namespace")
    private String namespaceName;

    public void setNamespaceId(Long namespaceId) {
        this.namespaceId = namespaceId;
    }

    public Long getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceName(String namespaceName) {
        this.namespaceName = namespaceName;
    }

    @NotBlank(message = "HBase命名空间不能为空")
    @Size(min = 1, max = 128, message = "HBase命名空间长度不能超过128个字符")
    public String getNamespaceName() {
        return namespaceName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("namespaceId", getNamespaceId())
                .append("namespaceName", getNamespaceName())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
