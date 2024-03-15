package com.hydraql.console;

import java.util.List;

import com.hydraql.manager.core.conf.HydraqlHBaseConfiguration;
import com.hydraql.manager.core.conf.PropertyKey;
import com.hydraql.manager.core.template.HydraqlTemplate;

/**
 * @author leojie 2024/3/15 10:37
 */
public class HqlConsoleTest {
    public static void main(String[] args) {
        HydraqlHBaseConfiguration configuration = new HydraqlHBaseConfiguration();
        configuration.set(PropertyKey.HYDRAQL_MANAGER_PLUGINS_DIR, "/Users/leojie/other_project/HydraQLManager/build/plugins");
        configuration.set(PropertyKey.HYDRAQL_HBASE_VERSION, "1.4.3");
        configuration.set(PropertyKey.HYDRAQL_HBASE_ZOOKEEPER_QUORUM, "myhbase");
        HydraqlTemplate hydraqlTemplate = HydraqlTemplate.Factory.create(configuration);
        List<String> namespaceNames = hydraqlTemplate.listNamespaceNames();
        System.out.println(namespaceNames);
    }
}
