package com.hydraql.manager.core.template;

import com.hydraql.manager.core.conf.HydraqlHBaseConfiguration;
import com.hydraql.manager.core.extensions.ExtensionFactory;

/**
 * @author leojie 2024/1/25 16:26
 */
public interface HydraqlTemplateFactory extends ExtensionFactory<HydraqlTemplate, HydraqlHBaseConfiguration> {
    HydraqlTemplate create(HydraqlHBaseConfiguration conf);

    default String getVersion() {
        return "";
    }
}
