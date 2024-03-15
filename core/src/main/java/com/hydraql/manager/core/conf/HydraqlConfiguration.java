package com.hydraql.manager.core.conf;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author leojie 2024/2/23 14:26
 */
public interface HydraqlConfiguration {
    Object get(PropertyKey key);

    boolean isSet(PropertyKey key);

    default Object getOrDefault(PropertyKey key, Object defaultValue) {
        return isSet(key) ? get(key) : defaultValue;
    }

    Set<PropertyKey> keySet();

    String getString(PropertyKey key);

    boolean getBoolean(PropertyKey key);

    int getInt(PropertyKey key);

    long getLong(PropertyKey key);

    List<String> getList(PropertyKey key);

    Map<String, Object> toMap();

    Properties toProp();

}
