package com.leo.hbase.manager.common.enums;

/**
 * @author leojie 2020/8/20 8:30 下午
 */
public enum HBaseClusterStatus {
    /**
     * 标记表的业务状态
     */
    ONLINE("online"),
    OFFLINE("offline");

    private final String state;

    HBaseClusterStatus(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
