package com.hydraql.manager.core.conf;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author leojie@apache.org 2024/4/21 20:27
 */
public class PropertyKeyTest {
    @Test
    public void testProperty() {
        HydraQLHBaseConfiguration conf = new HydraQLHBaseConfiguration();
        boolean setRes = conf.isSet(PropertyKey.HYDRAQL_MANAGER_PLUGINS_DIR);
        Assert.assertFalse(setRes);
        conf.set(PropertyKey.HYDRAQL_MANAGER_PLUGINS_DIR, "/tmp");
        setRes = conf.isSet(PropertyKey.HYDRAQL_MANAGER_PLUGINS_DIR);
        Assert.assertTrue(setRes);
    }
}
