package com.leo.hbase.manager.system.mapper;

import com.leo.hbase.manager.system.domain.SysHbaseTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author leojie 2023/7/13 23:36
 */
public interface SysHbaseTableMapper {
    SysHbaseTable selectSysHbaseTableById(Long tableId);

    List<SysHbaseTable> selectSysHbaseTableList(SysHbaseTable sysHbaseTable);

    List<SysHbaseTable> selectSysHbaseTableListByUserId(@Param(value = "userId") Long userId);


    int selectSysHbaseTableCountByNameInOneCluster(SysHbaseTable sysHbaseTable);
    int insertSysHbaseTable(SysHbaseTable sysHbaseTable);

    int batchSaveSysHbaseTableList(@Param(value = "sysHbaseTableList") List<SysHbaseTable> sysHbaseTableList);

    int updateSysHbaseTable(SysHbaseTable sysHbaseTable);

    int deleteSysHbaseTableById(Long tableId);
}
