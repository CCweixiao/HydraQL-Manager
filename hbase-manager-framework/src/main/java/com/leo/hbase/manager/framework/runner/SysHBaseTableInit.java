package com.leo.hbase.manager.framework.runner;

import com.leo.hbase.manager.adaptor.service.IHBaseAdminService;
import com.leo.hbase.manager.common.enums.HBaseDisabledFlag;
import com.leo.hbase.manager.common.enums.HBaseTableStatus;
import com.leo.hbase.manager.common.utils.DateUtils;
import com.leo.hbase.manager.system.domain.SysHbaseTable;
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
    private ISysHbaseTableService sysHbaseTableService;

    @Override
    public void run(String... args) throws Exception {
        log.info("系统开始同步HBase中的表数据......");
        initHBaseTable();
        log.info("系统成功完成同步HBase中的表数据......");
    }

    private void initHBaseTable() {
        List<String> allTables = ihBaseAdminService.listAllTableName();
        for (String tableName : allTables) {

            String namespaceName = "default";
            String fullTableName = tableName;
            if (tableName.contains(":")) {
                namespaceName = tableName.split(":")[0];
            } else {
                fullTableName = "default" + ":" + tableName;
            }
            SysHbaseTable hbaseTable = sysHbaseTableService.selectSysHbaseTableByName(fullTableName);
            if (hbaseTable == null) {
                hbaseTable = new SysHbaseTable();
                hbaseTable.setNamespaceName(namespaceName);
                hbaseTable.setTableName(fullTableName);
                hbaseTable.setCreateBy("admin");
                hbaseTable.setCreateTime(DateUtils.getNowDate());
                hbaseTable.setRemark("由系统初始化时迁移而来");
                hbaseTable.setStatus(HBaseTableStatus.ONLINE.getCode());
                boolean disabled = ihBaseAdminService.isTableDisabled(tableName);
                if (disabled) {
                    hbaseTable.setDisableFlag(HBaseDisabledFlag.DISABLED.getCode());
                } else {
                    hbaseTable.setDisableFlag(HBaseDisabledFlag.ENABLED.getCode());
                }
                sysHbaseTableService.insertSysHbaseTable(hbaseTable);
            } else {
                log.info("HBase表[" + fullTableName + "]已经被初始化！");
            }
        }
    }
}
