package com.leo.hbase.manager.web.hds;

/**
 * HBase多集群管理标识
 *
 * @author leojie 2020/9/17 10:30 下午
 */
public class HBaseClusterIdentifier {
    private static final ThreadLocal<String> CLUSTER_CODE = new ThreadLocal<>();

    public static String getClusterCode() {
        return CLUSTER_CODE.get();
    }

    public static void setClusterCode(String code) {
        CLUSTER_CODE.set(code);
    }

    public static void clearClusterCode() {
        CLUSTER_CODE.remove();
    }
}
