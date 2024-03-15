package com.leo.hbase.manager.system.service;

import com.leo.hbase.manager.system.domain.SysHbaseCluster;
import java.util.List;

/**
 * HBaseClusterService接口
 *
 * @author leojie
 * @date 2020-08-16
 */
public interface ISysHbaseClusterService {
    SysHbaseCluster selectSysHbaseClusterById(Integer clusterId);
    SysHbaseCluster selectSysHbaseClusterById(String clusterId);

    List<SysHbaseCluster> selectSysHbaseClusterList(String state);

    List<SysHbaseCluster> selectAllSysHbaseClusterList();
    List<SysHbaseCluster> selectAllOnlineSysHbaseClusterList();

    int insertSysHbaseCluster(SysHbaseCluster sysHbaseCluster);

    int updateSysHbaseCluster(SysHbaseCluster sysHbaseCluster);

    int updateSysHbaseClusterState(int id, String newState);

    int deleteSysHbaseClusterById(Integer clusterId);

    List<String> getAllOnlineClusterIds();

    String getFilterNamespacePrefix(String clusterId);

    String getFilterTableNamePrefix(String clusterId);
}
