package com.leo.hbase.manager.framework.runner;

import com.leo.hbase.manager.adaptor.service.IHBaseAdminService;
import com.leo.hbase.manager.common.utils.DateUtils;
import com.leo.hbase.manager.system.domain.SysHbaseNamespace;
import com.leo.hbase.manager.system.service.ISysHbaseNamespaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 命名空间 数据初始化
 *
 * @author leojie 2020/8/19 11:12 下午
 */
@Component
@Order(1)
public class SysHBaseNamespaceInit implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(SysHBaseNamespaceInit.class);
    @Autowired
    private IHBaseAdminService ihBaseAdminService;

    @Autowired
    private ISysHbaseNamespaceService sysHbaseNamespaceService;


    @Override
    public void run(String... args) throws Exception {
        log.info("系统开始同步HBase中的namespace数据......");

        //initNamespace();

        log.info("系统成功完成同步HBase中的namespace数据......");
    }

 /*   private void initNamespace() {
        List<String> allNamespace = ihBaseAdminService.listAllNamespace();
        if (allNamespace != null && !allNamespace.isEmpty()) {
            for (String namespaceName : allNamespace) {
                SysHbaseNamespace namespace = sysHbaseNamespaceService.selectSysHbaseNamespaceByName(namespaceName);
                if (namespace == null || namespace.getNamespaceId() < 1) {
                    namespace = new SysHbaseNamespace();
                    namespace.setNamespaceName(namespaceName);
                    namespace.setCreateTime(DateUtils.getNowDate());
                    sysHbaseNamespaceService.insertSysHbaseNamespace(namespace);
                }
            }
        }
    }*/
}
