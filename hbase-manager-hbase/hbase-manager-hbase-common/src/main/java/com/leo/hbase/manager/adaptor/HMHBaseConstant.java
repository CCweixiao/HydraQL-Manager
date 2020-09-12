package com.leo.hbase.manager.adaptor;

/**
 * @author leojie 2020/9/12 4:57 下午
 */
public class HMHBaseConstant {

    public static final String DEFAULT_SYS_TABLE_NAMESPACE = "hbase";
    public static final String TABLE_NAME_SPLIT_CHAR = ":";
    public static final String DEFAULT_NAMESPACE_NAME = "default";


    public static String getFullTableName(String tableName) {
        if (tableName.contains(":")) {
            return tableName;
        } else {
            return "default" + ":" + tableName;
        }
    }
}
