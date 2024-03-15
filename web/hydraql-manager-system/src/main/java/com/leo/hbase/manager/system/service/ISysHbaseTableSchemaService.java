package com.leo.hbase.manager.system.service;

import com.leo.hbase.manager.system.domain.SysHbaseTableSchema;

import java.util.List;

/**
 * SysHbaseTableSchemaMapper接口
 * 
 * @author leojie
 * @date 2020-08-16
 */
public interface ISysHbaseTableSchemaService {

    int insertSysHbaseTableSchema(SysHbaseTableSchema sysHbaseTableSchema);

    int updateSysHbaseTableSchema(SysHbaseTableSchema sysHbaseTableSchema);

    int deleteSysHbaseTableSchemaById(Integer schemaId);

    SysHbaseTableSchema selectSysHbaseTableSchemaById(Integer schemaId);

    SysHbaseTableSchema selectSysHbaseTableSchemaByTableName(String clusterId, String tableName);

    List<SysHbaseTableSchema> selectSysHbaseTableSchemaList(String clusterId, String tableName);
}
