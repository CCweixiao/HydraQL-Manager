package com.hydraql.manager.core.util;

/**
 * @author leojie 2024/2/23 22:45
 */
public class HConstants {
    public static final String META_TABLE_NAME = "hbase:meta";
    public static final String DEFAULT_SYS_TABLE_NAMESPACE = "hbase";
    public static final String TABLE_NAME_SPLIT_CHAR = ":";
    public static final String FAMILY_QUALIFIER_SEPARATOR = ":";
    public static final String DEFAULT_NAMESPACE_NAME = "default";
    public static final Integer DEFAULT_TTL = 2147483647;
    public static final String DEFAULT_COMPRESSION_TYPE = "NONE";
    public static final Integer DEFAULT_MAX_VERSIONS = 1;
    public static final Integer DEFAULT_REPLICATION_SCOPE = 0;

    public static final Integer DISABLE_REPLICATION_SCOPE = 0;
    public static final Integer ENABLE_REPLICATION_SCOPE = 1;
    public static final String SIMPLE_AUTH = "simple";
    public static final String KERBEROS_AUTH = "kerberos";
    public static final String ENABLED = "ENABLED";
    public static final String DISABLED = "DISABLED";
    public static final String HBASE_CLIENT_RETRIES_NUMBER = "hbase.client.retries.number";


    public static String getFullTableName(String tableName) {
        if (StringUtil.isBlank(tableName)) {
            throw new IllegalArgumentException("The table name is not empty.");
        }

        if (tableName.contains(TABLE_NAME_SPLIT_CHAR)) {
            return tableName;
        } else {
            return DEFAULT_NAMESPACE_NAME.concat(TABLE_NAME_SPLIT_CHAR).concat(tableName);
        }
    }

    public static String getNamespaceName(String tableName) {
        if (tableName.contains(TABLE_NAME_SPLIT_CHAR)) {
            String[] ns = tableName.split(TABLE_NAME_SPLIT_CHAR);
            if (ns.length == 2) {
                return tableName.split(TABLE_NAME_SPLIT_CHAR)[0];
            } else {
                throw new IllegalArgumentException(
                        String.format("The table name format of %s does not conform to specification.", tableName));
            }
        } else {
            return DEFAULT_NAMESPACE_NAME;
        }
    }

    public static String getTableName(String tableName) {
        if (tableName.contains(TABLE_NAME_SPLIT_CHAR)) {
            return tableName.split(TABLE_NAME_SPLIT_CHAR)[1];
        } else {
            return tableName;
        }
    }

    public static String getFullTableName(String namespaceName, String tableName) {
        if (StringUtil.isBlank(tableName)) {
            throw new IllegalArgumentException("The table name is not empty.");
        }
        if (tableName.contains(TABLE_NAME_SPLIT_CHAR)) {
            return tableName;
        } else {
            if (StringUtil.isBlank(namespaceName)) {
                return DEFAULT_NAMESPACE_NAME + TABLE_NAME_SPLIT_CHAR + tableName;
            }
            return namespaceName + TABLE_NAME_SPLIT_CHAR + tableName;
        }
    }

    private HConstants() {

    }
}
