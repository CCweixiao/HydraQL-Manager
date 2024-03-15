package com.leo.hbase.manager.common.enums;

/**
 * HBase meta table
 *
 * @author leojie 2020/8/20 8:33 下午
 */
public enum HBaseMetaTableFlag {
    /**
     * HBase meta table.
     */
    META_TABLE("0", "元数据表"),
    USER_TABLE("2", "用户表");
    private final String code;
    private final String info;

    HBaseMetaTableFlag(String code, String info) {
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
