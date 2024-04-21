package com.hydraql.manager.core.template;

import com.hydraql.manager.core.conf.HydraQLHBaseConfiguration;
import com.hydraql.manager.core.extensions.ExtensionFactory;

/**
 * @author leojie 2024/1/25 16:26
 */
public interface HydraQLTemplateFactory extends ExtensionFactory<HydraQLTemplate, HydraQLHBaseConfiguration> {
    HydraQLTemplate create(HydraQLHBaseConfiguration conf);

    default String getVersion() {
        return "";
    }
}
