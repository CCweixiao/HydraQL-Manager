package com.leo.hbase.manager.system.dto;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.google.common.base.Converter;
import com.hydraql.manager.core.conf.PropertyKey;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.system.domain.SysHbaseCluster;
import com.leo.hbase.manager.system.valid.*;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * @author leojie 2023/7/9 21:14
 */
@GroupSequence(value = {First.class, Second.class, Third.class, Fourth.class, Five.class, Six.class, SysHbaseClusterDto.class})
public class SysHbaseClusterDto {
    private Integer id;
    @NotBlank(message = "集群ID不能为空", groups = {First.class})
    @Size(min = 1, max = 50, message = "集群ID长度不能超过50个字符", groups = {Second.class})
    @Pattern(regexp = "^\\w+$", message = "集群ID只能是数字、字母、下划线三种类型字符的组合", groups = {Third.class})
    private String clusterId;
    private String clusterVersion;
    @NotBlank(message = "集群名称不能为空", groups = {Fourth.class})
    @Size(min = 1, max = 200, message = "集群名称必须在1~100个字符之间", groups = {Five.class})
    private String clusterName;
    private String clusterDesc;
    private List<PropertyDto> properties;
    private String filterNamespacePrefix;
    private String filterTableNamePrefix;

    @NotBlank(message = "连接集群的ZK地址必须指定", groups = {Six.class})
    private String zkQuorum;
    private String zkclientPort = "2181";
    private String authenticationType;
    private String kerberosPrincipal;
    private String keytabFile;
    private String regionServerKerberosPrincipal;
    private String masterKerberosPrincipal;
    private String krb5Conf;
    private String krb5Realm;
    private String krb5Kdc;
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

    public String getFilterNamespacePrefix() {
        return filterNamespacePrefix;
    }

    public void setFilterNamespacePrefix(String filterNamespacePrefix) {
        this.filterNamespacePrefix = filterNamespacePrefix;
    }

    public String getFilterTableNamePrefix() {
        return filterTableNamePrefix;
    }

    public void setFilterTableNamePrefix(String filterTableNamePrefix) {
        this.filterTableNamePrefix = filterTableNamePrefix;
    }

    public String getZkQuorum() {
        return zkQuorum;
    }

    public void setZkQuorum(String zkQuorum) {
        this.zkQuorum = zkQuorum;
    }

    public String getZkclientPort() {
        return zkclientPort;
    }

    public void setZkclientPort(String zkclientPort) {
        this.zkclientPort = zkclientPort;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
    }

    public String getKerberosPrincipal() {
        return kerberosPrincipal;
    }

    public void setKerberosPrincipal(String kerberosPrincipal) {
        this.kerberosPrincipal = kerberosPrincipal;
    }

    public String getKeytabFile() {
        return keytabFile;
    }

    public void setKeytabFile(String keytabFile) {
        this.keytabFile = keytabFile;
    }

    public String getRegionServerKerberosPrincipal() {
        return regionServerKerberosPrincipal;
    }

    public void setRegionServerKerberosPrincipal(String regionServerKerberosPrincipal) {
        this.regionServerKerberosPrincipal = regionServerKerberosPrincipal;
    }

    public String getMasterKerberosPrincipal() {
        return masterKerberosPrincipal;
    }

    public void setMasterKerberosPrincipal(String masterKerberosPrincipal) {
        this.masterKerberosPrincipal = masterKerberosPrincipal;
    }

    public String getKrb5Conf() {
        return krb5Conf;
    }

    public void setKrb5Conf(String krb5Conf) {
        this.krb5Conf = krb5Conf;
    }

    public String getKrb5Realm() {
        return krb5Realm;
    }

    public void setKrb5Realm(String krb5Realm) {
        this.krb5Realm = krb5Realm;
    }

    public String getKrb5Kdc() {
        return krb5Kdc;
    }

    public void setKrb5Kdc(String krb5Kdc) {
        this.krb5Kdc = krb5Kdc;
    }

    public List<PropertyDto> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyDto> properties) {
        this.properties = properties;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private List<PropertyDto> getPropertyDtoList() {
        List<PropertyDto> propertyDtoList = new ArrayList<>(12);
        if (StringUtils.isNotBlank(this.getZkQuorum())) {
            propertyDtoList.add(new PropertyDto(PropertyKey.Name.HBASE_ZOOKEEPER_QUORUM, this.getZkQuorum()));
        }
        if (StringUtils.isNotBlank(this.getZkclientPort())) {
            propertyDtoList.add(new PropertyDto(PropertyKey.Name.HBASE_ZOOKEEPER_CLIENT_PORT, this.getZkclientPort()));
        }
        if (StringUtils.isNotBlank(this.getAuthenticationType())) {
            propertyDtoList.add(new PropertyDto(PropertyKey.Name.HBASE_SECURITY_AUTH, this.getAuthenticationType()));
        }
        if (StringUtils.isNotBlank(this.getKerberosPrincipal())) {
            propertyDtoList.add(new PropertyDto(PropertyKey.Name.HBASE_KERBEROS_PRINCIPAL, this.getKerberosPrincipal()));
        }
        if (StringUtils.isNotBlank(this.getKeytabFile())) {
            propertyDtoList.add(new PropertyDto(PropertyKey.Name.HBASE_KERBEROS_KEYTAB_FILE, this.getKeytabFile()));
        }
        if (StringUtils.isNotBlank(this.getRegionServerKerberosPrincipal())) {
            propertyDtoList.add(new PropertyDto(PropertyKey.Name.HBASE_REGION_SERVER_KERBEROS_PRINCIPAL, this.getRegionServerKerberosPrincipal()));
        }
        if (StringUtils.isNotBlank(this.getMasterKerberosPrincipal())) {
            propertyDtoList.add(new PropertyDto(PropertyKey.Name.HBASE_MASTER_KERBEROS_PRINCIPAL, this.getMasterKerberosPrincipal()));
        }
        if (StringUtils.isNotBlank(this.getKrb5Conf())) {
            propertyDtoList.add(new PropertyDto(PropertyKey.Name.KRB5_CONF_PATH, this.getKrb5Conf()));
        }
        if (StringUtils.isNotBlank(this.getKrb5Realm())) {
            propertyDtoList.add(new PropertyDto(PropertyKey.Name.KRB5_REALM, this.getKrb5Realm()));
        }
        if (StringUtils.isNotBlank(this.getKrb5Kdc())) {
            propertyDtoList.add(new PropertyDto(PropertyKey.Name.KRB5_KDC_SERVER_ADDR, this.getKrb5Kdc()));
        }
        if (StringUtils.isNotBlank(this.getFilterNamespacePrefix())) {
            propertyDtoList.add(new PropertyDto(PropertyKey.Name.FILTER_NAMESPACE_PREFIX, this.getFilterNamespacePrefix()));
        }
        if (StringUtils.isNotBlank(this.getFilterTableNamePrefix())) {
            propertyDtoList.add(new PropertyDto(PropertyKey.Name.FILTER_TABLE_NAME_PREFIX, this.getFilterTableNamePrefix()));
        }
        return propertyDtoList;
    }

    public SysHbaseCluster convertTo() {
        SysHbaseClusterDto.SysHbaseClusterDtoConvert convert = new SysHbaseClusterDto.SysHbaseClusterDtoConvert();
        return convert.convert(this);
    }

    public SysHbaseClusterDto convertFor(SysHbaseCluster sysHbaseCluster) {
        SysHbaseClusterDto.SysHbaseClusterDtoConvert convert = new SysHbaseClusterDto.SysHbaseClusterDtoConvert();
        return convert.reverse().convert(sysHbaseCluster);
    }

    public static class SysHbaseClusterDtoConvert extends Converter<SysHbaseClusterDto, SysHbaseCluster> {

        @Override
        protected SysHbaseCluster doForward(SysHbaseClusterDto sysHbaseClusterDto) {
            SysHbaseCluster sysHbaseCluster = new SysHbaseCluster();
            sysHbaseCluster.setId(sysHbaseClusterDto.getId());
            sysHbaseCluster.setClusterId(sysHbaseClusterDto.getClusterId());
            sysHbaseCluster.setClusterVersion(sysHbaseClusterDto.getClusterVersion());
            sysHbaseCluster.setClusterName(sysHbaseClusterDto.getClusterName());
            sysHbaseCluster.setClusterDesc(sysHbaseClusterDto.getClusterDesc());
            sysHbaseCluster.setState(sysHbaseClusterDto.getState());
            // 单项配置的优先级最高
            List<PropertyDto> propertyDtoList = sysHbaseClusterDto.getPropertyDtoList();

            List<PropertyDto> properties = sysHbaseClusterDto.getProperties();
            if (properties != null && !properties.isEmpty()) {
                for (PropertyDto property : properties) {
                    if (!propertyDtoList.contains(property)) {
                        propertyDtoList.add(property);
                    }
                }
            }
            sysHbaseCluster.setClusterConfig(JSON.toJSONString(propertyDtoList));
            return sysHbaseCluster;
        }

        @Override
        protected SysHbaseClusterDto doBackward(SysHbaseCluster sysHbaseCluster) {
            SysHbaseClusterDto sysHbaseClusterDto = new SysHbaseClusterDto();
            sysHbaseClusterDto.setId(sysHbaseCluster.getId());
            sysHbaseClusterDto.setClusterId(sysHbaseCluster.getClusterId());
            sysHbaseClusterDto.setClusterVersion(sysHbaseCluster.getClusterVersion());
            sysHbaseClusterDto.setClusterName(sysHbaseCluster.getClusterName());
            sysHbaseClusterDto.setClusterDesc(sysHbaseCluster.getClusterDesc());
            sysHbaseClusterDto.setState(sysHbaseCluster.getState());
            String clusterConfig = sysHbaseCluster.getClusterConfig();
            if (StringUtils.isNotBlank(clusterConfig) && !"null".equals(clusterConfig)) {
                JSONArray configs = JSON.parseArray(clusterConfig);
                List<PropertyDto> properties = new ArrayList<>(configs.size());
                for (int i = 0; i < configs.size(); i++) {
                    PropertyDto property = configs.getObject(i, PropertyDto.class);
                    if (PropertyKey.Name.HBASE_ZOOKEEPER_QUORUM.equals(property.getPropertyName())) {
                        sysHbaseClusterDto.setZkQuorum(property.getPropertyValue());
                    } else if (PropertyKey.Name.HBASE_ZOOKEEPER_CLIENT_PORT.equals(property.getPropertyName())) {
                        sysHbaseClusterDto.setZkclientPort(property.getPropertyValue());
                    } else if (PropertyKey.Name.HBASE_SECURITY_AUTH.equals(property.getPropertyName())) {
                        sysHbaseClusterDto.setAuthenticationType(property.getPropertyValue());
                    } else if (PropertyKey.Name.HBASE_KERBEROS_PRINCIPAL.equals(property.getPropertyName())) {
                        sysHbaseClusterDto.setKerberosPrincipal(property.getPropertyValue());
                    } else if (PropertyKey.Name.HBASE_KERBEROS_KEYTAB_FILE.equals(property.getPropertyName())) {
                        sysHbaseClusterDto.setKeytabFile(property.getPropertyValue());
                    } else if (PropertyKey.Name.HBASE_REGION_SERVER_KERBEROS_PRINCIPAL.equals(property.getPropertyName())) {
                        sysHbaseClusterDto.setRegionServerKerberosPrincipal(property.getPropertyValue());
                    } else if (PropertyKey.Name.HBASE_MASTER_KERBEROS_PRINCIPAL.equals(property.getPropertyName())) {
                        sysHbaseClusterDto.setMasterKerberosPrincipal(property.getPropertyValue());
                    } else if (PropertyKey.Name.KRB5_CONF_PATH.equals(property.getPropertyName())) {
                        sysHbaseClusterDto.setKrb5Conf(property.getPropertyValue());
                    } else if (PropertyKey.Name.KRB5_REALM.equals(property.getPropertyName())) {
                        sysHbaseClusterDto.setKrb5Realm(property.getPropertyValue());
                    } else if (PropertyKey.Name.KRB5_KDC_SERVER_ADDR.equals(property.getPropertyName())) {
                        sysHbaseClusterDto.setKrb5Kdc(property.getPropertyValue());
                    } else if (PropertyKey.Name.FILTER_NAMESPACE_PREFIX.equals(property.getPropertyName())) {
                        sysHbaseClusterDto.setFilterNamespacePrefix(property.getPropertyValue());
                    } else if (PropertyKey.Name.FILTER_TABLE_NAME_PREFIX.equals(property.getPropertyName())) {
                        sysHbaseClusterDto.setFilterTableNamePrefix(property.getPropertyValue());
                    } else {
                        properties.add(property);
                    }
                }
                sysHbaseClusterDto.setProperties(properties);
            } else {
                sysHbaseClusterDto.setProperties(new ArrayList<>(0));
            }

            return sysHbaseClusterDto;
        }
    }

}
