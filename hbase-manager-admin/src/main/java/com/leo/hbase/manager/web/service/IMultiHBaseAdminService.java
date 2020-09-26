package com.leo.hbase.manager.web.service;

import com.github.CCweixiao.model.FamilyDesc;
import com.github.CCweixiao.model.NamespaceDesc;
import com.github.CCweixiao.model.TableDesc;

import java.util.List;

/**
 * @author leojie 2020/9/24 9:46 下午
 */

public interface IMultiHBaseAdminService {
    /**
     * get the descriptor of namespace.
     *
     * @param clusterCode   集群code
     * @param namespaceName the name of descriptor.
     * @return the descriptor of namespace
     */
    NamespaceDesc getNamespaceDesc(String clusterCode, String namespaceName);

    /**
     * get all namespace descriptor.
     *
     * @param clusterCode 集群code
     * @return all namespace descriptor
     */
    List<NamespaceDesc> listAllNamespaceDesc(String clusterCode);

    /**
     * 获取所有的命名空间
     *
     * @param clusterCode 集群code
     * @return 所有的命名空间
     */
    List<String> listAllNamespaceName(String clusterCode);

    /**
     * get table name list by namespace name.
     *
     * @param clusterCode   集群code
     * @param namespaceName namespace name
     * @return all tables
     */
    List<String> listAllTableNamesByNamespaceName(String clusterCode, String namespaceName);

    /**
     * create a namespace
     *
     * @param clusterCode 集群code
     * @param namespace   the descriptor of namespace.
     * @return created the namespace successfully or not.
     */
    boolean createNamespace(String clusterCode, NamespaceDesc namespace);

    /**
     * 删除命名空间
     *
     * @param clusterCode 集群code
     * @param namespace   命名空间
     * @return 是否删除成功
     */
    boolean deleteNamespace(String clusterCode, String namespace);


    /**
     * 获取所有的表名
     *
     * @param clusterCode 集群code
     * @return 所有表名
     */
    List<String> listAllTableName(String clusterCode);

    /**
     * 获取所有的表信息
     *
     * @param clusterCode 集群ID
     * @return 所有表信息
     */
    List<TableDesc> listAllTableDesc(String clusterCode);


    /**
     * 创建表
     *
     * @param clusterCode 集群code
     * @param tableDesc   表描述信息
     * @return 创建表是否成功
     */
    boolean createTable(String clusterCode, TableDesc tableDesc);

    /**
     * 启用表
     *
     * @param clusterCode 集群code
     * @param tableName   表名
     * @return 是否启用用成功
     */
    boolean enableTable(String clusterCode, String tableName);


    /**
     * 禁用表
     *
     * @param clusterCode 集群code
     * @param tableName   表名
     * @return 是否禁用成功
     */
    boolean disableTable(String clusterCode, String tableName);

    /**
     * 判断表是否被禁用
     *
     * @param clusterCode 集群code
     * @param tableName   表名
     * @return 是否禁用
     */
    boolean isTableDisabled(String clusterCode, String tableName);

    /**
     * 判断HBase表是否存在
     *
     * @param clusterCode 集群code
     * @param tableName   表名
     * @return 是否存在
     */
    boolean tableIsExists(String clusterCode, String tableName);

    /**
     * 删除表
     *
     * @param clusterCode 集群code
     * @param tableName   表名
     * @return 删除结果
     */
    boolean deleteTable(String clusterCode, String tableName);

    /**
     * get table descriptor.
     *
     * @param clusterCode 集群code
     * @param tableName   table name.
     * @return table descriptor
     */
    TableDesc getTableDesc(String clusterCode, String tableName);

    /**
     * get all families by HBase table name.
     *
     * @param clusterCode 集群code
     * @param tableName   the name of HBase table.
     * @return families
     */
    List<FamilyDesc> getFamilyDesc(String clusterCode, String tableName);

    /**
     * 启用replication
     *
     * @param clusterCode 集群code
     * @param tableName   表名
     * @param families    列簇
     * @return 结果
     */
    boolean enableReplication(String clusterCode, String tableName, List<String> families);

    /**
     * 禁用replication
     *
     * @param clusterCode 集群code
     * @param tableName   表名
     * @param families    列簇
     * @return 结果
     */
    boolean disableReplication(String clusterCode, String tableName, List<String> families);


}
