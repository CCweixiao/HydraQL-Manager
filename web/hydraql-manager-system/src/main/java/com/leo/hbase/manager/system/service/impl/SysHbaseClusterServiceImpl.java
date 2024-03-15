package com.leo.hbase.manager.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.leo.hbase.manager.common.enums.HBaseClusterStatus;
import com.leo.hbase.manager.system.domain.SysHbaseCluster;
import com.leo.hbase.manager.system.dto.PropertyDto;
import com.leo.hbase.manager.system.mapper.SysHbaseClusterMapper;
import com.leo.hbase.manager.system.service.ISysHbaseClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leojie 2023/7/9 18:12
 */
@Service
public class SysHbaseClusterServiceImpl implements ISysHbaseClusterService {
    @Autowired
    private SysHbaseClusterMapper clusterMapper;


    @Override
    public SysHbaseCluster selectSysHbaseClusterById(Integer clusterId) {
        return clusterMapper.selectSysHbaseClusterById(clusterId);
    }

    @Override
    public SysHbaseCluster selectSysHbaseClusterById(String clusterId) {
        return clusterMapper.selectSysHbaseClusterByClusterId(clusterId);
    }

    @Override
    public List<SysHbaseCluster> selectSysHbaseClusterList(String state) {
        return clusterMapper.selectSysHbaseClusterList(state);
    }

    @Override
    public List<SysHbaseCluster> selectAllSysHbaseClusterList() {
        return clusterMapper.selectSysHbaseClusterList("");
    }

    @Override
    public List<SysHbaseCluster> selectAllOnlineSysHbaseClusterList() {
        return clusterMapper.selectSysHbaseClusterList(HBaseClusterStatus.ONLINE.getState());
    }

    @Override
    public int insertSysHbaseCluster(SysHbaseCluster sysHbaseCluster) {
        sysHbaseCluster.setState(HBaseClusterStatus.ONLINE.getState());
        return clusterMapper.insertSysHbaseCluster(sysHbaseCluster);
    }

    @Override
    public int updateSysHbaseCluster(SysHbaseCluster sysHbaseCluster) {
        return clusterMapper.updateSysHbaseCluster(sysHbaseCluster);
    }

    @Override
    public int updateSysHbaseClusterState(int id, String newState) {
        SysHbaseCluster cluster = new SysHbaseCluster();
        cluster.setId(id);
        cluster.setState(newState);
        return updateSysHbaseCluster(cluster);
    }

    @Override
    public int deleteSysHbaseClusterById(Integer clusterId) {
        return clusterMapper.deleteSysHbaseClusterById(clusterId);
    }

    @Override
    public List<String> getAllOnlineClusterIds() {
        List<SysHbaseCluster> sysHbaseClusters = clusterMapper.selectSysHbaseClusterList("");
        return sysHbaseClusters.stream().map(SysHbaseCluster::getClusterId).collect(Collectors.toList());
    }

    @Override
    public String getFilterNamespacePrefix(String clusterId) {
        SysHbaseCluster sysHbaseCluster = selectSysHbaseClusterById(clusterId);
        if (sysHbaseCluster == null || sysHbaseCluster.getId() == null || sysHbaseCluster.getId() < 1) {
            return "";
        }
        String clusterConfig = sysHbaseCluster.getClusterConfig();
        try {
            JSONArray configArr = JSON.parseArray(clusterConfig);
            for (int i = 0; i < configArr.size(); i++) {
                PropertyDto propertyDto = configArr.getObject(i, PropertyDto.class);
                // todo fix
                if ("filter.namespace.prefix".equals(propertyDto.getPropertyName())) {
                    return propertyDto.getPropertyValue();
                }
            }
        }catch (Exception e) {
            return "";
        }

        return "";
    }

    @Override
    public String getFilterTableNamePrefix(String clusterId) {
        SysHbaseCluster sysHbaseCluster = selectSysHbaseClusterById(clusterId);
        if (sysHbaseCluster == null || sysHbaseCluster.getId() == null || sysHbaseCluster.getId() < 1) {
            return "";
        }
        String clusterConfig = sysHbaseCluster.getClusterConfig();
        try {
            JSONArray configArr = JSON.parseArray(clusterConfig);
            for (int i = 0; i < configArr.size(); i++) {
                PropertyDto propertyDto = configArr.getObject(i, PropertyDto.class);
                // todo fix
                if ("filter.table.name.prefix".equals(propertyDto.getPropertyName())) {
                    return propertyDto.getPropertyValue();
                }
            }
        } catch (Exception e) {
            return "";
        }

        return "";
    }
}
