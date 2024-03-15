package com.leo.hbase.manager.system.service;

import java.util.List;

import com.leo.hbase.manager.system.domain.SysHbaseUserTable;

/**
 * 用户和HBase的关联Service接口
 *
 * @author leojie
 * @date 2021-02-06
 */
public interface ISysHbaseUserTableService {
    int insertSysHbaseUserTable(SysHbaseUserTable sysHbaseUserTable);

    int deleteSysHbaseUserTable(SysHbaseUserTable sysHbaseUserTable);

    int deleteSysHbaseUserTableByUserId(Long userId);

    int deleteSysHbaseUserTableByTableId(Long tableId);

    int deleteSysHbaseUserTableByUserIds(Long[] userIds);

    int batchAddSysHbaseUserTable(List<SysHbaseUserTable> sysHbaseUserTableList);
}
