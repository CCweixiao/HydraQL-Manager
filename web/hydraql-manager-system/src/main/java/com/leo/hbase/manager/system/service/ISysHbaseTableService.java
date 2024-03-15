package com.leo.hbase.manager.system.service;

import com.leo.hbase.manager.system.domain.SysHbaseTable;

import java.util.List;

/**
 * @author leojie 2023/7/14 00:15
 */
public interface ISysHbaseTableService {
    SysHbaseTable selectSysHbaseTableById(Long tableId);

    List<SysHbaseTable> selectSysHbaseTableList(SysHbaseTable sysHbaseTable);

    List<SysHbaseTable> selectSysHbaseTableListByUserId(Long userId);

    int selectSysHbaseTableCountByNameInOneCluster(SysHbaseTable sysHbaseTable);

    List<SysHbaseTable> selectSysHbaseTableListByClusterId(String clusterId);

    int insertSysHbaseTable(SysHbaseTable sysHbaseTable);

    int batchSaveSysHbaseTableList(List<SysHbaseTable> sysHbaseTableList);

    int updateSysHbaseTable(SysHbaseTable sysHbaseTable);

    int deleteSysHbaseTableById(Long tableId);
}
