package com.leo.hbase.manager.system.service.impl;

import com.leo.hbase.manager.system.domain.SysHbaseTable;
import com.leo.hbase.manager.system.mapper.SysHbaseTableMapper;
import com.leo.hbase.manager.system.service.ISysHbaseTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leojie 2023/7/14 00:15
 */
@Service
public class SysHbaseTableServiceImpl implements ISysHbaseTableService {
    @Autowired
    private SysHbaseTableMapper sysHbaseTableMapper;

    @Override
    public SysHbaseTable selectSysHbaseTableById(Long tableId) {
        return sysHbaseTableMapper.selectSysHbaseTableById(tableId);
    }

    @Override
    public List<SysHbaseTable> selectSysHbaseTableList(SysHbaseTable sysHbaseTable) {
        return sysHbaseTableMapper.selectSysHbaseTableList(sysHbaseTable);
    }

    @Override
    public List<SysHbaseTable> selectSysHbaseTableListByUserId(Long userId) {
        return sysHbaseTableMapper.selectSysHbaseTableListByUserId(userId);
    }

    @Override
    public int selectSysHbaseTableCountByNameInOneCluster(SysHbaseTable sysHbaseTable) {
        return sysHbaseTableMapper.selectSysHbaseTableCountByNameInOneCluster(sysHbaseTable);
    }

    @Override
    public List<SysHbaseTable> selectSysHbaseTableListByClusterId(String clusterId) {
        SysHbaseTable sysHbaseTable = new SysHbaseTable();
        sysHbaseTable.setClusterId(clusterId);
        return sysHbaseTableMapper.selectSysHbaseTableList(sysHbaseTable);
    }

    @Override
    public int insertSysHbaseTable(SysHbaseTable sysHbaseTable) {
        return sysHbaseTableMapper.insertSysHbaseTable(sysHbaseTable);
    }

    @Override
    public int batchSaveSysHbaseTableList(List<SysHbaseTable> sysHbaseTableList) {
        return sysHbaseTableMapper.batchSaveSysHbaseTableList(sysHbaseTableList);
    }

    @Override
    public int updateSysHbaseTable(SysHbaseTable sysHbaseTable) {
        return sysHbaseTableMapper.updateSysHbaseTable(sysHbaseTable);
    }

    @Override
    public int deleteSysHbaseTableById(Long tableId) {
        return sysHbaseTableMapper.deleteSysHbaseTableById(tableId);
    }
}
