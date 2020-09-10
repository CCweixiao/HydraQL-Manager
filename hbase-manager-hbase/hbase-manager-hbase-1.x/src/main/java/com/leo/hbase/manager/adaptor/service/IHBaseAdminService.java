package com.leo.hbase.manager.adaptor.service;


import com.leo.hbase.manager.adaptor.model.FamilyDesc;
import com.leo.hbase.manager.adaptor.model.NamespaceDesc;
import com.leo.hbase.manager.adaptor.model.TableDesc;
import org.apache.hadoop.hbase.HTableDescriptor;

import java.util.List;

/**
 * @author leojie 2020/8/19 11:03 下午
 */
public interface IHBaseAdminService {

    /**
     * get the descriptor of namespace.
     *
     * @param namespaceName the name of descriptor.
     * @return the descriptor of namespace
     */
    NamespaceDesc getNamespaceDesc(String namespaceName);

    /**
     * get all namespace descriptor.
     *
     * @return all namespace descriptor
     */
    List<NamespaceDesc> listAllNamespaceDesc();

    /**
     * 获取所有的命名空间
     *
     * @return 所有的命名空间
     */
    List<String> listAllNamespaceName();

    /**
     * create a namespace
     *
     * @param namespace the descriptor of namespace.
     * @return created the namespace successfully or not.
     */
    boolean createNamespace(NamespaceDesc namespace);

    /**
     * 删除命名空间
     *
     * @param namespace 命名空间
     * @return 是否删除成功
     */
    boolean deleteNamespace(String namespace);

    /**
     * 获取所有的表名
     *
     * @return 所有表名
     */
    List<String> listAllTableName();

    /**
     * 创建表
     *
     * @param tableDescriptor 表描述
     * @param startKey        预分区开始key
     * @param endKey          预分区结束key
     * @param numRegions      预分区数
     * @return 创建表是否成功
     */
    boolean createTable(HTableDescriptor tableDescriptor, String startKey, String endKey, int numRegions);

    /**
     * 创建表
     *
     * @param tableDescriptor 表描述
     * @param splitKeys       分区keys
     * @return 创建表是否成功
     */
    boolean createTable(HTableDescriptor tableDescriptor, String[] splitKeys);

    /**
     * 创建表
     *
     * @param tableDescriptor 表描述
     * @return 创建表是否成功
     */
    boolean createTable(HTableDescriptor tableDescriptor);

    /**
     * 启用表
     *
     * @param tableName 表名
     * @return 是否启用用成功
     */
    boolean enableTable(String tableName);

    /**
     * 禁用表
     *
     * @param tableName 表名
     * @return 是否禁用成功
     */
    boolean disableTable(String tableName);

    /**
     * 判断表是否被禁用
     *
     * @param tableName 表名
     * @return 是否禁用
     */
    boolean isTableDisabled(String tableName);

    /**
     * 判断HBase表是否存在
     *
     * @param tableName 表名
     * @return 是否存在
     */
    boolean tableIsExists(String tableName);

    /**
     * 删除表
     *
     * @param tableName 表名
     * @return 删除结果
     */
    boolean deleteTable(String tableName);

    /**
     * get table descriptor.
     *
     * @param tableName table name.
     * @return table descriptor
     */
    TableDesc getTableDesc(String tableName);

    /**
     * 获取表的描述信息
     *
     * @param tableName 表名
     * @return 表的描述信息
     */
    String getTableDescToString(String tableName);

    /**
     * get all families by HBase table name.
     *
     * @param tableName the name of HBase table.
     * @return families
     */
    List<FamilyDesc> getFamilyDesc(String tableName);

    /**
     * 获取HBase表简介
     *
     * @param tableName 表名
     * @return 表的描述信息
     */
    HTableDescriptor getTableDescriptor(String tableName);

    /**
     * 启用replication
     *
     * @param tableName 表名
     * @param families  列簇
     * @return 结果
     */
    boolean enableReplication(String tableName, List<String> families);

    /**
     * 禁用replication
     *
     * @param tableName 表名
     * @param families  列簇
     * @return 结果
     */
    boolean disableReplication(String tableName, List<String> families);

}
