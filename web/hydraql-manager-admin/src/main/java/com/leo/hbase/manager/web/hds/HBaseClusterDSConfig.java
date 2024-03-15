package com.leo.hbase.manager.web.hds;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.hydraql.manager.core.conf.HydraqlHBaseConfiguration;
import com.hydraql.manager.core.conf.PropertyKey;
import com.hydraql.manager.core.template.HydraqlTemplate;
import com.leo.hbase.manager.common.exception.BusinessException;
import com.leo.hbase.manager.common.utils.SocketUtil;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.system.domain.SysHbaseCluster;
import com.leo.hbase.manager.system.dto.PropertyDto;
import com.leo.hbase.manager.system.dto.SysHbaseClusterDto;
import com.leo.hbase.manager.system.service.ISysHbaseClusterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * HBase动态数据源管理器
 *
 * @author leojie 2020/9/17 10:34 下午
 */
@Component
public class HBaseClusterDSConfig {
    private static final Logger LOG = LoggerFactory.getLogger(HBaseClusterDSConfig.class);

    @Autowired
    private ISysHbaseClusterService hbaseClusterService;

    public HydraqlTemplate getHydraqlTemplate(String clusterCode) {
        LOG.info("当前获取集群:{}的HydraqlTemplate对象！", clusterCode);
        Map<String, String> properties = getHBaseProperties(clusterCode);
        HydraqlHBaseConfiguration conf = new HydraqlHBaseConfiguration();
        properties.forEach((k, v) -> conf.set(PropertyKey.fromString(k), v));
        return HydraqlTemplate.Factory.create(conf);
    }

    public HydraqlTemplate getHydraqlTemplate(SysHbaseClusterDto sysHbaseClusterDto) {
        return getHydraqlTemplate(sysHbaseClusterDto.getClusterId());
    }

    private Map<String, String> getHBaseProperties(String clusterId) {
        LOG.info("开始解析HBase集群:{}的配置......", clusterId);
        SysHbaseCluster sysHbaseCluster = hbaseClusterService.selectSysHbaseClusterById(clusterId);
        if (sysHbaseCluster == null || sysHbaseCluster.getId() < 0) {
            throw new IllegalStateException(String.format("The cluster %s is not exists.", clusterId));
        }
        SysHbaseClusterDto sysHbaseClusterDto = new SysHbaseClusterDto().convertFor(sysHbaseCluster);
        return getHBaseProperties(sysHbaseClusterDto);
    }

    private Map<String, String> getHBaseProperties(SysHbaseClusterDto sysHbaseClusterDto) {
        SysHbaseCluster sysHbaseCluster = sysHbaseClusterDto.convertTo();
        String clusterConfig = sysHbaseCluster.getClusterConfig();
        if (StringUtils.isNotBlank(clusterConfig) && !"null".equals(clusterConfig)) {
            JSONArray propArr = JSON.parseArray(clusterConfig);
            Map<String, String> properties = new HashMap<>();
            for (int i = 0; i < propArr.size(); i++) {
                PropertyDto propObj = propArr.getObject(i, PropertyDto.class);
                properties.put(propObj.getPropertyName(), propObj.getPropertyValue());
            }
            properties.put(PropertyKey.HYDRAQL_HBASE_VERSION.getName(), sysHbaseCluster.getClusterVersion());
            String zkHost = properties.get(PropertyKey.HYDRAQL_HBASE_ZOOKEEPER_QUORUM.getName());
            if (StringUtils.isBlank(zkHost)) {
                zkHost = "localhost";
            }
            String zkPort = properties.get(PropertyKey.HYDRAQL_HBASE_ZOOKEEPER_CLIENT_PORT.getName());
            if (StringUtils.isNotBlank(zkPort)) {
                zkPort = "2181";
            }
            String[] zkHostArr = zkHost.split(",");
            for (String zk : zkHostArr) {
                boolean telnet = SocketUtil.telnet(zk, Integer.parseInt(zkPort), 10 * 1000);
                if (!telnet) {
                    throw new RuntimeException(String.format("zkHost:[%s],zkPort:[%s]访问不通", zk, zkPort));
                }
            }

            return properties;
        } else {
            throw new BusinessException("解析HBase集群配置异常");
        }
    }
}
