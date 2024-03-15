package com.hydraql.manager.plugins;

import com.hydraql.manager.core.conf.HydraqlHBaseConfiguration;
import com.hydraql.manager.core.conf.PropertyKey;
import com.hydraql.manager.core.hbase.HBaseVersion;
import com.hydraql.manager.core.template.HydraqlTemplate;
import com.hydraql.manager.core.template.HydraqlTemplateFactory;

/**
 * @author leojie 2024/1/26 09:26
 */
public class HydraqlTemplateFactoryImpl implements HydraqlTemplateFactory {
    public HydraqlTemplateFactoryImpl() {

    }

    @Override
    public HydraqlTemplate create(HydraqlHBaseConfiguration conf) {
        return HydraqlTemplateImpl.createInstance(conf);
    }

    @Override
    public boolean supportsVersion(HydraqlHBaseConfiguration conf) {
        if (conf.isSet(PropertyKey.HYDRAQL_HBASE_VERSION)) {
            String userConfiguredVersion = conf.getString(PropertyKey.HYDRAQL_HBASE_VERSION);
            return HBaseVersion.matches(userConfiguredVersion, getVersion());
        }
        return false;
    }

    @Override
    public String getVersion() {
        return HydraqlConstants.HYDRAQL_HBASE_VERSION;
    }
}
