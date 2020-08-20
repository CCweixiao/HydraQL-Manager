package com.leo.hbase.manager.common.enums;

/**
 * HBase 禁用状态
 *
 * @author leojie 2020/8/20 8:33 下午
 */
public enum HBaseDisabledFlag {
    /**
     * 禁用表的标机状态
     */
    ENABLED("0", "启用表"),
    DISABLED("2", "禁用表");
    private final String code;
    private final String info;

    HBaseDisabledFlag(String code, String info) {
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
