package com.leo.hbase.manager.web.hds;

import com.github.CCweixiao.HBaseAdminTemplate;
import com.github.CCweixiao.HBaseTemplate;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.leo.hbase.manager.common.utils.HBaseConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * HBase动态数据源管理器
 *
 * @author leojie 2020/9/17 10:34 下午
 */
public class HBaseClusterDSHolder {
    private static final Logger LOG = LoggerFactory.getLogger(HBaseClusterDSHolder.class);

    private final Map<String, HBaseTemplate> hBaseTemplateMap = new HashMap<>();
    private final Map<String, HBaseAdminTemplate> hBaseAdminTemplateMap = new HashMap<>();

    private HBaseClusterDSHolder() {

    }

    public static HBaseClusterDSHolder instance() {
        return HBaseClusterDSHolderBuilder.INSTANCE;
    }

    /**
     * cluster1.hbase.quorum=node1,node2,node3
     * cluster1.hbase.zk.client.port=2181
     * cluster1.hbase.node.parent=/hbase
     */
    private synchronized void initAllHBaseTemplate() {
        List<String> allClusters = HBaseConfigUtils.getPropertyArrayList("hbase.manager.zk.cluster.alias", ",");
        if (allClusters.isEmpty()) {
            throw new RuntimeException("请在hbase-manager.properties中配置需要管理的集群！");
        }
        for (String cluster : allClusters) {
            LOG.info("开始解析HBase集群:{}的配置......", cluster);
            String quorum = HBaseConfigUtils.getProperty(cluster + ".hbase.quorum", "localhost");
            String zkClientPort = HBaseConfigUtils.getProperty(cluster + ".hbase.zk.client.port", "2181");
            String nodeParent = HBaseConfigUtils.getProperty(cluster + ".hbase.node.parent", "/hbase");
            Properties properties = new Properties();
            properties.setProperty("hbase.zookeeper.quorum", quorum);
            properties.setProperty("hbase.zookeeper.property.clientPort", zkClientPort);
            properties.setProperty("zookeeper.znode.parent", nodeParent);
            HBaseTemplate hBaseTemplate = new HBaseTemplate(properties);
            hBaseTemplateMap.put(cluster, hBaseTemplate);
        }
    }

    private synchronized void initAllHBaseAdminTemplate() {
        List<String> allClusters = HBaseConfigUtils.getPropertyArrayList("hbase.manager.zk.cluster.alias", ",");
        if (allClusters.isEmpty()) {
            throw new RuntimeException("请在hbase-manager.properties中配置需要管理的集群！");
        }
        for (String cluster : allClusters) {
            LOG.info("开始解析HBase集群:{}的配置......", cluster);
            String quorum = HBaseConfigUtils.getProperty(cluster + ".hbase.quorum", "localhost");
            String zkClientPort = HBaseConfigUtils.getProperty(cluster + ".hbase.zk.client.port", "2181");
            String nodeParent = HBaseConfigUtils.getProperty(cluster + ".hbase.node.parent", "/hbase");
            Properties properties = new Properties();
            properties.setProperty("hbase.zookeeper.quorum", quorum);
            properties.setProperty("hbase.zookeeper.property.clientPort", zkClientPort);
            properties.setProperty("zookeeper.znode.parent", nodeParent);
            HBaseAdminTemplate hBaseAdminTemplate = new HBaseAdminTemplate(properties);
            hBaseAdminTemplateMap.put(cluster, hBaseAdminTemplate);
        }
    }

    public synchronized HBaseTemplate getHBaseTemplate(String clusterCode) {
        LOG.info("当前获取集群:{}的HBaseTemplate对象！", clusterCode);
        if (hBaseTemplateMap.isEmpty()) {
            initAllHBaseTemplate();
        }
        if (hBaseTemplateMap.containsKey(clusterCode)) {
            return hBaseTemplateMap.get(clusterCode);
        }
        throw new HBaseOperationsException("获取集群[" + clusterCode + "]的连接对象失败，请检查配置！");
    }

    public synchronized HBaseAdminTemplate getHBaseAdminTemplate(String clusterCode) {
        LOG.info("当前获取集群:{}的HBaseAdminTemplate对象！", clusterCode);
        if (hBaseAdminTemplateMap.isEmpty()) {
            initAllHBaseAdminTemplate();
        }
        if (hBaseAdminTemplateMap.containsKey(clusterCode)) {
            return hBaseAdminTemplateMap.get(clusterCode);
        }
        throw new HBaseOperationsException("获取集群[" + clusterCode + "]的连接对象失败，请检查配置！");
    }

    private static class HBaseClusterDSHolderBuilder {
        private static final HBaseClusterDSHolder INSTANCE = new HBaseClusterDSHolder();
    }
}
