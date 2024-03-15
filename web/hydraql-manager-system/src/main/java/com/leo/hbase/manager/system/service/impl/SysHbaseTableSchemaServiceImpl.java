package com.leo.hbase.manager.system.service.impl;

import com.leo.hbase.manager.system.domain.SysHbaseTableSchema;
import com.leo.hbase.manager.system.mapper.SysHbaseTableSchemaMapper;
import com.leo.hbase.manager.system.service.ISysHbaseTableSchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leojie 2023/7/23 20:43
 */
@Service
public class SysHbaseTableSchemaServiceImpl implements ISysHbaseTableSchemaService {
    @Autowired
    private SysHbaseTableSchemaMapper schemaMapper;

    @Override
    public int insertSysHbaseTableSchema(SysHbaseTableSchema sysHbaseTableSchema) {
        return schemaMapper.insertSysHbaseTableSchema(sysHbaseTableSchema);
    }

    @Override
    public int updateSysHbaseTableSchema(SysHbaseTableSchema sysHbaseTableSchema) {
        return schemaMapper.updateSysHbaseTableSchema(sysHbaseTableSchema);
    }

    @Override
    public int deleteSysHbaseTableSchemaById(Integer schemaId) {
        return schemaMapper.deleteSysHbaseTableSchemaById(schemaId);
    }

    @Override
    public SysHbaseTableSchema selectSysHbaseTableSchemaById(Integer schemaId) {
        return schemaMapper.selectSysHbaseTableSchemaById(schemaId);
    }

    @Override
    public SysHbaseTableSchema selectSysHbaseTableSchemaByTableName(String clusterId, String tableName) {
        return schemaMapper.selectSysHbaseTableSchemaByTableName(clusterId, tableName);
    }

    @Override
    public List<SysHbaseTableSchema> selectSysHbaseTableSchemaList(String clusterId, String tableName) {
        return schemaMapper.selectSysHbaseTableSchemaList(clusterId, tableName);
    }
}
