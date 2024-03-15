package com.hydraql.manager.core.template;

import com.hydraql.manager.core.conf.HydraqlHBaseConfiguration;
import com.hydraql.manager.core.conf.PropertyKey;
import com.hydraql.manager.core.extensions.ExtensionFactoryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author leojie 2024/1/26 09:55
 */
public class HydraqlTemplateFactoryRegistry {
    private static final Logger LOG = LoggerFactory.getLogger(HydraqlTemplateFactoryRegistry.class);
    private static final String UFS_EXTENSION_PATTERN = "hydraql-manager-plugins-*.jar";

    private static ExtensionFactoryRegistry<HydraqlTemplateFactory, HydraqlHBaseConfiguration> sRegistryInstance;

    private HydraqlTemplateFactoryRegistry() {}

    static {
        init();
    }

    public static List<HydraqlTemplateFactory> available() {
        return sRegistryInstance.getAvailable();
    }

    public static List<HydraqlTemplateFactory> findAll(HydraqlHBaseConfiguration conf) {
        List<HydraqlTemplateFactory> eligibleFactories = sRegistryInstance.findAll(conf);
        if (conf.getBoolean(PropertyKey.HYDRAQL_HBASE_STRICT_VERSION_MATCH_ENABLED)
                && !eligibleFactories.isEmpty()
                && conf.isSet(PropertyKey.HYDRAQL_HBASE_VERSION)) {
            String configuredVersion = conf.getString(PropertyKey.HYDRAQL_HBASE_VERSION);
            eligibleFactories.removeIf(hydraqlTemplateFactory ->
                    !configuredVersion.equals(hydraqlTemplateFactory.getVersion()));

        }
        return eligibleFactories;
    }

    public static List<String> getSupportedVersions(HydraqlHBaseConfiguration conf) {
        List<HydraqlTemplateFactory> factories = sRegistryInstance.findAll(conf);
        List<String> supportedVersions = new ArrayList<>();
        for (HydraqlTemplateFactory factory : factories) {
            if (!factory.getVersion().isEmpty()) {
                supportedVersions.add(factory.getVersion());
            }
        }
        return supportedVersions;
    }

    private static synchronized void init() {
        if (sRegistryInstance == null) {
            sRegistryInstance = new ExtensionFactoryRegistry<>(HydraqlTemplateFactory.class,
                    UFS_EXTENSION_PATTERN);
        }
    }

    public static void register(HydraqlTemplateFactory factory) {
        sRegistryInstance.register(factory);
    }

    public static synchronized void reset() {
        sRegistryInstance.reset();
    }

    public static void unregister(HydraqlTemplateFactory factory) {
        sRegistryInstance.unregister(factory);
    }

}
