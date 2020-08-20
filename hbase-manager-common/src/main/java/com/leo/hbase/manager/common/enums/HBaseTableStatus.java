package com.leo.hbase.manager.common.enums;

/**
 * @author leojie 2020/8/20 8:30 下午
 */
public enum HBaseTableStatus {
    /**
     * 标记表的业务状态
     */
    ONLINE("0", "线上表"),
    WAIT_ONLINE("1", "待上线表"),
    TEST("2", "测试表"),
    DEPRECATED("3", "弃用表");

    private final String code;
    private final String info;

    HBaseTableStatus(String code, String info) {
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
