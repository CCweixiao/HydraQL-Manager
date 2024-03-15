package com.leo.hbase.manager.common.enums;

/**
 * HBase Replication Scope
 *
 * @author leojie 2020/8/20 8:33 下午
 */
public enum HBaseReplicationScopeFlag {
    /**
     * Replication Scope的标记状态
     */
    CLOSE("0", "CLOSE"),
    OPEN("1", "OPEN");
    private final String code;
    private final String info;

    HBaseReplicationScopeFlag(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
