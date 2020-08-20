package com.leo.hbase.manager.adaptor.service;

import java.util.List;

/**
 * @author leojie 2020/8/19 11:03 下午
 */
public interface IHBaseAdminService {
    /**
     * 获取所有的命名空间
     *
     * @return 所有的命名空间
     */
    List<String> listAllNamespace();

    /**
     * 创建命名空间
     *
     * @param namespace 命名空间名称
     * @return 是否创建成功
     */
    boolean createNamespace(String namespace);

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
    boolean tableIsDisabled(String tableName);

    /**
     * 删除表
     *
     * @param tableName 表名
     * @return 删除结果
     */
    boolean deleteTable(String tableName);
}
