package com.leo.hbase.manager.adaptor.model;

import java.util.Map;

/**
 * @author leojie 2020/9/9 9:48 下午
 */
public class NamespaceDesc {
    private String namespaceId;
    private String namespaceName;
    private Map<String, String> namespaceProps;


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

    public Map<String, String> getNamespaceProps() {
        return namespaceProps;
    }

    public void setNamespaceProps(Map<String, String> namespaceProps) {
        this.namespaceProps = namespaceProps;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append('{');
        s.append("ID");
        s.append(" => '");
        s.append(this.namespaceId);
        s.append(", ");
        s.append("NAME");
        s.append(" => '");
        s.append(this.namespaceName);
        s.append("'");

        this.namespaceProps.forEach((key, value) -> {
            if (key != null) {
                s.append(", ");
                s.append(key);
                s.append(" => '");
                s.append(value);
                s.append("'");
            }
        });

        s.append('}');
        return s.toString();
    }

}
