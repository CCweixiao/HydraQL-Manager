package com.leo.hbase.manager.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.leo.hbase.manager.system.mapper.SysHbaseUserTableMapper;
import com.leo.hbase.manager.system.domain.SysHbaseUserTable;
import com.leo.hbase.manager.system.service.ISysHbaseUserTableService;

/**
 * 用户和HBase的关联Service业务层处理
 *
 * @author leojie
 * @date 2021-02-06
 */
@Service
public class SysHbaseUserTableServiceImpl implements ISysHbaseUserTableService {
    @Autowired
    private SysHbaseUserTableMapper sysHbaseUserTableMapper;


    @Override
    public int insertSysHbaseUserTable(SysHbaseUserTable sysHbaseUserTable) {
        return sysHbaseUserTableMapper.insertSysHbaseUserTable(sysHbaseUserTable);
    }

    @Override
    public int deleteSysHbaseUserTable(SysHbaseUserTable sysHbaseUserTable) {
        return sysHbaseUserTableMapper.deleteSysHbaseUserTable(sysHbaseUserTable);
    }

    @Override
    public int deleteSysHbaseUserTableByUserId(Long userId) {
        return sysHbaseUserTableMapper.deleteSysHbaseUserTableByUserId(userId);
    }

    @Override
    public int deleteSysHbaseUserTableByTableId(Long tableId) {
        return sysHbaseUserTableMapper.deleteSysHbaseUserTableByTableId(tableId);
    }

    @Override
    public int deleteSysHbaseUserTableByUserIds(Long[] userIds) {
        return sysHbaseUserTableMapper.deleteSysHbaseUserTableByUserIds(userIds);
    }

    @Override
    public int batchAddSysHbaseUserTable(List<SysHbaseUserTable> sysHbaseUserTableList) {
        if (sysHbaseUserTableList == null || sysHbaseUserTableList.isEmpty()) {
            return 0;
        }
        return sysHbaseUserTableMapper.batchAddSysHbaseUserTable(sysHbaseUserTableList);
    }
}
