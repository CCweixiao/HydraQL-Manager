package com.leo.hbase.manager.web.service;

import com.github.CCwexiao.dsl.client.HBaseCellResult;

import java.util.List;
import java.util.Map;

/**
 * @author leojie 2020/9/24 9:44 下午
 */
public interface IMultiHBaseService {
    /**
     * 根据row key查找数据
     *
     * @param clusterCode 集群code
     * @param tableName   表名
     * @param rowKey      row key
     * @param familyName  列簇名
     * @return 查询数据
     */
    List<Map<String, Object>> get(String clusterCode, String tableName, String rowKey, String familyName);

    /**
     * 根据row key查找数据
     *
     * @param clusterCode 集群code
     * @param tableName   表名
     * @param rowKey      row key
     * @param familyName  列簇名
     * @param qualifier   字段名
     * @return 查询数据
     */
    Map<String, Object> get(String clusterCode, String tableName, String rowKey, String familyName, String qualifier);


    /**
     * 查找数据
     *
     * @param clusterCode 集群code
     * @param tableName   表名
     * @param familyName  列簇名
     * @param startKey    开始的key
     * @param limit       查询限制条数
     * @return 数据
     */
    List<List<Map<String, Object>>> find(String clusterCode, String tableName, String familyName, String startKey, Integer limit);

    /**
     * 删除数据
     *
     * @param clusterCode 集群code
     * @param tableName   表名
     * @param rowKey      row key
     * @param familyName  列簇名
     * @param qualifier   字段名
     */
    void delete(String clusterCode, String tableName, String rowKey, String familyName, String qualifier);

    /**
     * 保存数据
     *
     * @param clusterCode            集群code
     * @param tableName              表名
     * @param rowKey                 rowKey
     * @param familyAndQualifierName 列簇名
     * @param value                  值
     */
    void saveOrUpdate(String clusterCode, String tableName, String rowKey, String familyAndQualifierName, String value);

    /**
     * select
     *
     * @param clusterCode 集群编号
     * @param hql         hql
     * @return select 结果集
     */
    List<List<HBaseCellResult>> select(String clusterCode, String hql);

    /**
     * 数据插入
     *
     * @param clusterCode 集群编号
     * @param hql         hql
     */
    void insert(String clusterCode, String hql);

    /**
     * 数据删除
     *
     * @param clusterCode 集群编号
     * @param hql         hql
     */
    void delete(String clusterCode, String hql);
}
