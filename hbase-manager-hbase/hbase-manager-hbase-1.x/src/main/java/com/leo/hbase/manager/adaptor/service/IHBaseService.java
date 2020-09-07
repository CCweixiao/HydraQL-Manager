package com.leo.hbase.manager.adaptor.service;

import java.util.List;
import java.util.Map;

/**
 * @author leojie 2020/9/7 8:28 下午
 */
public interface IHBaseService {
    /**
     * 根据row key查找数据
     *
     * @param tableName 表名
     * @param rowKey    row key
     * @return 查询数据
     */
    List<Map<String, Object>> get(String tableName, String rowKey);


    /**
     * 查找数据
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param startKey   开始的key
     * @param limit      查询限制条数
     * @return 数据
     */
    List<List<Map<String, Object>>> find(String tableName, String familyName, String startKey, Integer limit);

    /**
     * 删除数据
     *
     * @param tableName  表名
     * @param rowKey     row key
     * @param familyName 列簇名
     * @param qualifier  字段名
     */
    void delete(String tableName, String rowKey, String familyName, String qualifier);

}
