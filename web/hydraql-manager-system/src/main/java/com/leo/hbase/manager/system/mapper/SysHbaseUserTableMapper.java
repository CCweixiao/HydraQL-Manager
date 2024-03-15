package com.leo.hbase.manager.system.mapper;

import java.util.List;

import com.leo.hbase.manager.system.domain.SysHbaseUserTable;
import org.apache.ibatis.annotations.Param;

/**
 * 用户和HBase的关联Mapper接口
 *
 * @author leojie
 * @date 2021-02-06
 */
public interface SysHbaseUserTableMapper {

    int insertSysHbaseUserTable(SysHbaseUserTable sysHbaseUserTable);

    int deleteSysHbaseUserTable(SysHbaseUserTable sysHbaseUserTable);

    int deleteSysHbaseUserTableByUserId(@Param(value = "userId") Long userId);

    int deleteSysHbaseUserTableByTableId(@Param(value = "tableId") Long tableId);

    int deleteSysHbaseUserTableByUserIds(Long[] userIds);

    int batchAddSysHbaseUserTable(List<SysHbaseUserTable> sysHbaseUserTableList);
}
