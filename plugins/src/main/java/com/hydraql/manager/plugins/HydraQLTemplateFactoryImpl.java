package com.hydraql.manager.plugins;

import com.hydraql.manager.core.conf.HydraQLHBaseConfiguration;
import com.hydraql.manager.core.conf.PropertyKey;
import com.hydraql.manager.core.hbase.HBaseVersion;
import com.hydraql.manager.core.template.HydraQLTemplate;
import com.hydraql.manager.core.template.HydraQLTemplateFactory;

/**
 * @author leojie 2024/1/26 09:26
 */
public class HydraQLTemplateFactoryImpl implements HydraQLTemplateFactory {
    public HydraQLTemplateFactoryImpl() {

    }

    @Override
    public HydraQLTemplate create(HydraQLHBaseConfiguration conf) {
        return HydraQLTemplateImpl.createInstance(conf);
    }

    @Override
    public boolean supportsVersion(HydraQLHBaseConfiguration conf) {
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
