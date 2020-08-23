package com.leo.hbase.manager.framework.runner;

import com.leo.hbase.manager.adaptor.service.IHBaseAdminService;
import com.leo.hbase.manager.common.enums.HBaseDisabledFlag;
import com.leo.hbase.manager.common.utils.DateUtils;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.system.domain.SysHbaseNamespace;
import com.leo.hbase.manager.system.domain.SysHbaseTable;
import com.leo.hbase.manager.system.service.ISysHbaseNamespaceService;
import com.leo.hbase.manager.system.service.ISysHbaseTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author leojie 2020/8/19 11:21 下午
 */
@Component
@Order(2)
public class SysHBaseTableInit implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(SysHBaseTableInit.class);

    @Autowired
    private IHBaseAdminService ihBaseAdminService;

    @Autowired
    private ISysHbaseNamespaceService sysHbaseNamespaceService;

    @Autowired
    private ISysHbaseTableService sysHbaseTableService;

    @Override
    public void run(String... args) throws Exception {
        log.info("系统开始同步HBase中的表数据......");

        //TODO 完善初始化HBase表数据
        //initHBaseTable();

        log.info("系统成功完成同步HBase中的表数据......");
    }

    private void initHBaseTable() {
        List<String> allTables = ihBaseAdminService.listAllTableName();
        for (String tableName : allTables) {
            String namespaceName = StringUtils.substringNamespace(tableName);
            String shortTableName = StringUtils.substringTableName(tableName);

            SysHbaseNamespace hbaseNamespace = sysHbaseNamespaceService.selectSysHbaseNamespaceByName(namespaceName);
            if (hbaseNamespace != null && hbaseNamespace.getNamespaceId() > 0) {
                Long namespaceId = hbaseNamespace.getNamespaceId();
                SysHbaseTable hbaseTable = sysHbaseTableService.selectSysHbaseTableByNamespaceAndTableName(namespaceId, shortTableName);
                if (hbaseTable == null) {
                    hbaseTable = new SysHbaseTable();
                    hbaseTable.setNamespaceId(namespaceId);
                    hbaseTable.setTableName(shortTableName);
                    if (ihBaseAdminService.tableIsDisabled(tableName)) {
                        hbaseTable.setDisableFlag(HBaseDisabledFlag.DISABLED.getCode());
                    } else {
                        hbaseTable.setDisableFlag(HBaseDisabledFlag.ENABLED.getCode());
                    }
                    hbaseTable.setCreateTime(DateUtils.getNowDate());
                    hbaseTable.setRemark("由系统初始化时迁移而来");
                    sysHbaseTableService.insertSysHbaseTable(hbaseTable);
                }

            }
        }
    }
}
