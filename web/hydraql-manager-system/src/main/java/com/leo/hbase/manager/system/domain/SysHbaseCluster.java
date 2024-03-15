package com.leo.hbase.manager.system.domain;

import java.io.Serializable;

/**
 * @author leojie 2023/7/9 17:52
 */
public class SysHbaseCluster implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String clusterId;
    private String clusterVersion;
    private String clusterName;
    private String clusterDesc;
    private String clusterConfig;
    private String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getClusterVersion() {
        return clusterVersion;
    }

    public void setClusterVersion(String clusterVersion) {
        this.clusterVersion = clusterVersion;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getClusterDesc() {
        return clusterDesc;
    }

    public void setClusterDesc(String clusterDesc) {
        this.clusterDesc = clusterDesc;
    }

    public String getClusterConfig() {
        return clusterConfig;
    }

    public void setClusterConfig(String clusterConfig) {
        this.clusterConfig = clusterConfig;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "SysHbaseCluster{" +
                "clusterId='" + clusterId + '\'' +
                ", clusterName='" + clusterName + '\'' +
                ", clusterVersion='" + clusterVersion + '\'' +
                ", clusterDesc='" + clusterDesc + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
