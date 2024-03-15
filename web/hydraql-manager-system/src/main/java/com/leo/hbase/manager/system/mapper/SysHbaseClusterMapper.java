package com.leo.hbase.manager.system.mapper;

import com.leo.hbase.manager.system.domain.SysHbaseCluster;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * HBaseClusterMapper接口
 *
 * @author leojie
 * @date 2020-08-16
 */
public interface SysHbaseClusterMapper {
    SysHbaseCluster selectSysHbaseClusterById(@Param("clusterId") Integer clusterId);
    SysHbaseCluster selectSysHbaseClusterByClusterId(@Param("clusterId") String clusterId);

    List<SysHbaseCluster> selectSysHbaseClusterList(@Param("state") String state);

    int insertSysHbaseCluster(SysHbaseCluster sysHbaseCluster);

    int updateSysHbaseCluster(SysHbaseCluster sysHbaseCluster);

    int deleteSysHbaseClusterById(@Param("clusterId") Integer clusterId);
}
