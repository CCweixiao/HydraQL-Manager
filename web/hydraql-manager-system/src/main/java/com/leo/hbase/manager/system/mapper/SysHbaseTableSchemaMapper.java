package com.leo.hbase.manager.system.mapper;

import com.leo.hbase.manager.system.domain.SysHbaseTableSchema;
import com.leo.hbase.manager.system.domain.SysHbaseTableTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SysHbaseTableSchemaMapper接口
 * 
 * @author leojie
 * @date 2020-08-16
 */
public interface SysHbaseTableSchemaMapper {

    int insertSysHbaseTableSchema(SysHbaseTableSchema sysHbaseTableSchema);

    int updateSysHbaseTableSchema(SysHbaseTableSchema sysHbaseTableSchema);

    int deleteSysHbaseTableSchemaById(@Param(value = "schemaId") Integer schemaId);

    SysHbaseTableSchema selectSysHbaseTableSchemaById(@Param(value = "schemaId") Integer schemaId);

    SysHbaseTableSchema selectSysHbaseTableSchemaByTableName(@Param(value = "clusterId") String clusterId,
                                                             @Param(value = "tableName") String tableName);

    List<SysHbaseTableSchema> selectSysHbaseTableSchemaList(@Param(value = "clusterId") String clusterId,
                                                            @Param(value = "tableName") String tableName);
}
