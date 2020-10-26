package com.leo.hbase.manager.web.service.impl;

import com.github.CCweixiao.HBaseAdminTemplate;
import com.github.CCweixiao.constant.HMHBaseConstant;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.model.FamilyDesc;
import com.github.CCweixiao.model.NamespaceDesc;
import com.github.CCweixiao.model.TableDesc;
import com.github.CCweixiao.util.SplitGoEnum;
import com.github.CCweixiao.util.StrUtil;
import com.leo.hbase.manager.common.constant.HBaseManagerConstants;
import com.leo.hbase.manager.common.constant.HBasePropertyConstants;
import com.leo.hbase.manager.common.exception.BusinessException;
import com.leo.hbase.manager.common.utils.HBaseConfigUtils;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.framework.util.ShiroUtils;
import com.leo.hbase.manager.web.hds.HBaseClusterDSHolder;
import com.leo.hbase.manager.web.service.IMultiHBaseAdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leojie 2020/9/24 9:47 下午
 */
@Service
public class MultiHBaseAdminServiceImpl implements IMultiHBaseAdminService {
    @Override
    public NamespaceDesc getNamespaceDesc(String clusterCode, String namespaceName) {
        final String filterNamespacePrefix = HBaseConfigUtils.getFilterNamespacePrefix(clusterCode);
        if (StringUtils.isNotBlank(filterNamespacePrefix)) {
            if (namespaceName.startsWith(filterNamespacePrefix)) {
                throw new BusinessException("已配置过滤该命名空间的前缀");
            }
        }
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.getNamespaceDesc(namespaceName);
    }

    @Override
    public List<NamespaceDesc> listAllNamespaceDesc(String clusterCode) {
        final String filterNamespacePrefix = HBaseConfigUtils.getFilterNamespacePrefix(clusterCode);
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        final List<NamespaceDesc> namespaceDescList = hBaseTemplate.listNamespaceDesc();
        if (StringUtils.isNotBlank(filterNamespacePrefix)) {
            return namespaceDescList.stream().filter(namespaceDesc -> !namespaceDesc.getNamespaceName().startsWith(filterNamespacePrefix)).collect(Collectors.toList());
        }
        return namespaceDescList;
    }

    @Override
    public List<String> listAllNamespaceName(String clusterCode) {
        final String filterNamespacePrefix = HBaseConfigUtils.getFilterNamespacePrefix(clusterCode);
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        final List<String> namespaces = hBaseTemplate.listNamespaces();

        if (StringUtils.isNotBlank(filterNamespacePrefix)) {
            return namespaces.stream().filter(namespace -> !namespace.startsWith(filterNamespacePrefix)).collect(Collectors.toList());
        }
        return namespaces;
    }

    @Override
    public List<String> listAllTableNamesByNamespaceName(String clusterCode, String namespaceName) {
        final String filterNamespacePrefix = HBaseConfigUtils.getFilterNamespacePrefix(clusterCode);
        if (StringUtils.isNotBlank(filterNamespacePrefix)) {
            if (namespaceName.startsWith(filterNamespacePrefix)) {
                throw new BusinessException("已配置过滤该命名空间的前缀");
            }
        }
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.listTableNamesByNamespace(namespaceName);
    }

    @Override
    public boolean createNamespace(String clusterCode, NamespaceDesc namespace) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.createNamespace(namespace);
    }

    @Override
    public boolean deleteNamespace(String clusterCode, String namespace) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.deleteNamespace(namespace);
    }

    @Override
    public List<String> listAllTableName(String clusterCode) {
        final String filterNamespacePrefix = HBaseConfigUtils.getFilterNamespacePrefix(clusterCode);
        final String filterTableNamePrefix = HBaseConfigUtils.getFilterTableNamePrefix(clusterCode);

        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        final List<String> tableNames = hBaseTemplate.listTableNames();

        if (StringUtils.isNotBlank(filterNamespacePrefix) && StringUtils.isNotBlank(filterTableNamePrefix)) {
            return tableNames.stream().filter(tableName -> {
                final String namespaceName = HMHBaseConstant.getNamespaceName(tableName);
                return !namespaceName.startsWith(filterNamespacePrefix);
            }).filter(tableName -> {
                String shortTableName = HMHBaseConstant.getFullTableName(tableName).split(HMHBaseConstant.TABLE_NAME_SPLIT_CHAR)[1];
                return !shortTableName.startsWith(filterTableNamePrefix);
            }).collect(Collectors.toList());

        } else if (StringUtils.isNotBlank(filterNamespacePrefix) && StringUtils.isBlank(filterTableNamePrefix)) {
            return tableNames.stream().filter(tableName -> {
                final String namespaceName = HMHBaseConstant.getNamespaceName(tableName);
                return !namespaceName.startsWith(filterNamespacePrefix);
            }).collect(Collectors.toList());

        } else if (StringUtils.isBlank(filterNamespacePrefix) && StringUtils.isNotBlank(filterTableNamePrefix)) {
            return tableNames.stream().filter(tableName -> {
                String shortTableName = HMHBaseConstant.getFullTableName(tableName).split(HMHBaseConstant.TABLE_NAME_SPLIT_CHAR)[1];
                return !shortTableName.startsWith(filterTableNamePrefix);
            }).collect(Collectors.toList());
        }
        return tableNames;
    }

    @Override
    public List<TableDesc> listAllTableDesc(String clusterCode) {
        final String filterNamespacePrefix = HBaseConfigUtils.getFilterNamespacePrefix(clusterCode);
        final String filterTableNamePrefix = HBaseConfigUtils.getFilterTableNamePrefix(clusterCode);

        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        final List<TableDesc> tableDescList = hBaseTemplate.listTableDesc();

        if (StringUtils.isNotBlank(filterNamespacePrefix) && StringUtils.isNotBlank(filterTableNamePrefix)) {
            return tableDescList.stream().filter(tableDesc -> !tableDesc.getNamespaceName().startsWith(filterNamespacePrefix))
                    .filter(tableDesc -> {
                        String shortTableName = HMHBaseConstant.getFullTableName(tableDesc.getTableName()).split(HMHBaseConstant.TABLE_NAME_SPLIT_CHAR)[1];
                        return !shortTableName.startsWith(filterTableNamePrefix);
                    }).collect(Collectors.toList());

        } else if (StringUtils.isNotBlank(filterNamespacePrefix) && StringUtils.isBlank(filterTableNamePrefix)) {
            return tableDescList.stream().filter(tableDesc -> !tableDesc.getNamespaceName().startsWith(filterNamespacePrefix)).collect(Collectors.toList());

        } else if (StringUtils.isBlank(filterNamespacePrefix) && StringUtils.isNotBlank(filterTableNamePrefix)) {
            return tableDescList.stream().filter(tableDesc -> {
                String shortTableName = HMHBaseConstant.getFullTableName(tableDesc.getTableName()).split(HMHBaseConstant.TABLE_NAME_SPLIT_CHAR)[1];
                return !shortTableName.startsWith(filterTableNamePrefix);
            }).collect(Collectors.toList());
        }
        return tableDescList;
    }

    @Override
    public boolean createTable(String clusterCode, TableDesc tableDesc) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.createTable(tableDesc);
    }

    @Override
    public boolean createTable(String clusterCode, TableDesc tableDesc, String startKey, String endKey, int numRegions, boolean isAsync) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.createTable(tableDesc, startKey, endKey, numRegions, isAsync);
    }

    @Override
    public boolean createTable(String clusterCode, TableDesc tableDesc, String[] splitKeys, boolean isAsync) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.createTable(tableDesc, splitKeys, isAsync);
    }

    @Override
    public boolean createTable(String clusterCode, TableDesc tableDesc, SplitGoEnum splitGoEnum, int numRegions, boolean isAsync) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.createTable(tableDesc, splitGoEnum, numRegions, isAsync);
    }

    @Override
    public boolean enableTable(String clusterCode, String tableName) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.enableTable(tableName, true);
    }

    @Override
    public boolean disableTable(String clusterCode, String tableName) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.disableTable(tableName, true);
    }

    @Override
    public boolean isTableDisabled(String clusterCode, String tableName) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.isTableDisabled(tableName);
    }

    @Override
    public boolean tableIsExists(String clusterCode, String tableName) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.tableExists(tableName);
    }

    @Override
    public boolean deleteTable(String clusterCode, String tableName) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.deleteTable(tableName);
    }

    @Override
    public boolean truncatePreserve(String clusterCode, String tableName) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.truncateTable(tableName, true);
    }

    @Override
    public TableDesc getTableDesc(String clusterCode, String tableName) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.getTableDesc(tableName);
    }

    @Override
    public List<FamilyDesc> getFamilyDesc(String clusterCode, String tableName) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.listFamilyDesc(tableName);
    }

    @Override
    public boolean enableReplication(String clusterCode, String tableName, List<String> families) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.enableReplicationScope(tableName, families);
    }

    @Override
    public boolean disableReplication(String clusterCode, String tableName, List<String> families) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.disableReplicationScope(tableName, families);
    }

    @Override
    public boolean modifyTable(String clusterCode, TableDesc tableDesc) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        tableDesc.addProp(HBasePropertyConstants.LAST_UPDATE_BY, ShiroUtils.getLoginName());
        tableDesc.addProp(HBasePropertyConstants.LAST_UPDATE_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        return hBaseTemplate.modifyTableProps(tableDesc);
    }
}
