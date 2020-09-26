package com.leo.hbase.manager.web.init;

import com.leo.hbase.manager.system.service.ISysHbaseTableService;
import com.leo.hbase.manager.web.service.IMultiHBaseAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author leojie 2020/8/19 11:21 下午
 */
@Component
@Order(2)
public class SysHBaseTableInit implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(SysHBaseTableInit.class);

    @Autowired
    private IMultiHBaseAdminService multiHBaseAdminService;

    @Autowired
    private ISysHbaseTableService sysHbaseTableService;

    @Override
    public void run(String... args) throws Exception {
        log.info("系统开始同步HBase中的表数据......");
        //initHBaseTable();
        log.info("系统成功完成同步HBase中的表数据......");
    }

    /*private void initHBaseTable() {
        List<String> allTables = ihBaseAdminService.listAllTableName();
        for (String tableName : allTables) {

            String namespaceName = HMHBaseConstant.DEFAULT_NAMESPACE_NAME;
            String fullTableName = tableName;
            if (tableName.contains(HMHBaseConstant.TABLE_NAME_SPLIT_CHAR)) {
                namespaceName = tableName.split(HMHBaseConstant.TABLE_NAME_SPLIT_CHAR)[0];
            } else {
                fullTableName = HMHBaseConstant.DEFAULT_NAMESPACE_NAME + HMHBaseConstant.TABLE_NAME_SPLIT_CHAR + tableName;
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
    }*/
}
