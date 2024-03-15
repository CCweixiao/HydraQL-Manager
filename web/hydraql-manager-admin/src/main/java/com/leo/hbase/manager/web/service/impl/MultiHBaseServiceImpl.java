//package com.leo.hbase.manager.web.service.impl;
//
//import com.leo.hbase.manager.web.hds.HBaseClusterDSConfig;
//import com.leo.hbase.manager.web.service.IMultiHBaseService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author leojie 2020/9/24 9:46 下午
// */
//@Service
//public class MultiHBaseServiceImpl implements IMultiHBaseService {
//    @Autowired
//    private HBaseClusterDSConfig hBaseClusterDSConfig;
//
//    @Override
//    public HBaseRowData get(String clusterCode, String tableName, String rowKey, String familyName) {
//        BaseHBaseTableTemplate hBaseTemplate = hBaseClusterDSConfig.getHBaseTemplate(clusterCode);
//        GetRowParam getRowParam = GetRowParam.of(rowKey).family(familyName).build();
//        return hBaseTemplate.getRow(tableName, getRowParam);
//    }
//
//    @Override
//    public HBaseRowData get(String clusterCode, String tableName, String rowKey, String familyName, String qualifier) {
//        BaseHBaseTableTemplate hBaseTemplate = hBaseClusterDSConfig.getHBaseTemplate(clusterCode);
//        GetRowParam getRowParam = GetRowParam.of(rowKey).family(familyName).qualifiers(Collections.singletonList(qualifier)).build();
//        return hBaseTemplate.getRow(tableName, getRowParam);
//    }
//
//    @Override
//    public List<HBaseRowData> find(String clusterCode, String tableName, String familyName, String startKey, Integer limit) {
//        BaseHBaseTableTemplate hBaseTemplate = hBaseClusterDSConfig.getHBaseTemplate(clusterCode);
//        ScanParams scanQueryParamsBuilder = ScanParams.of()
//                .familyName(familyName)
//                .stopRow(startKey)
//                .limit(limit)
//                .build();
//        return hBaseTemplate.scan(tableName, scanQueryParamsBuilder);
//    }
//
//    @Override
//    public void delete(String clusterCode, String tableName, String rowKey, String familyName, String qualifier) {
//        BaseHBaseTableTemplate hBaseTemplate = hBaseClusterDSConfig.getHBaseTemplate(clusterCode);
//        hBaseTemplate.delete(tableName, rowKey, familyName, qualifier);
//    }
//
//    @Override
//    public void saveOrUpdate(String clusterCode, String tableName, String rowKey, String familyAndQualifierName, String value) {
//        BaseHBaseTableTemplate hBaseTemplate = hBaseClusterDSConfig.getHBaseTemplate(clusterCode);
//        Map<String, Object> data = new HashMap<>(1);
//        data.put(familyAndQualifierName, value);
//        hBaseTemplate.save(tableName, rowKey, data);
//    }
//
//    @Override
//    public HBaseDataSet select(String clusterCode, String hql) {
//        BaseHBaseSqlTemplate hBaseSqlTemplate = hBaseClusterDSConfig.getHBaseSqlTemplate(clusterCode);
//        return hBaseSqlTemplate.select(hql);
//    }
//
//    @Override
//    public void insert(String clusterCode, String hql) {
//        BaseHBaseSqlTemplate hBaseSqlTemplate = hBaseClusterDSConfig.getHBaseSqlTemplate(clusterCode);
//        hBaseSqlTemplate.insert(hql);
//    }
//
//    @Override
//    public void delete(String clusterCode, String hql) {
//        BaseHBaseSqlTemplate hBaseSqlTemplate = hBaseClusterDSConfig.getHBaseSqlTemplate(clusterCode);
//        hBaseSqlTemplate.delete(hql);
//    }
//
//    @Override
//    public String getTableNameFromHql(String clusterCode, String hql) {
//        BaseHBaseSqlTemplate hBaseSqlTemplate = hBaseClusterDSConfig.getHBaseSqlTemplate(clusterCode);
//        return hBaseSqlTemplate.parseTableNameFromHql(hql);
//    }
//
//    @Override
//    public HQLType getHqlTypeFromHql(String clusterCode, String hql) {
//        BaseHBaseSqlTemplate hBaseSqlTemplate = hBaseClusterDSConfig.getHBaseSqlTemplate(clusterCode);
//        return hBaseSqlTemplate.parseHQLType(hql);
//    }
//
//}
