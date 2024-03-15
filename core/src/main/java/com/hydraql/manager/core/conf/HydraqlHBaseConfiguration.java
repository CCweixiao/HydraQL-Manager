package com.hydraql.manager.core.conf;

import com.hydraql.manager.core.util.Preconditions;
import com.hydraql.manager.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author leojie 2024/1/30 17:11
 */
public class HydraqlHBaseConfiguration implements HydraqlConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(HydraqlHBaseConfiguration.class);
    protected final HydraqlProperties properties;

    public HydraqlHBaseConfiguration() {
        this.properties = new HydraqlProperties();
    }

    public HydraqlProperties getProperties() {
        return properties;
    }

    @Override
    public Object get(PropertyKey key) {
        Object value = properties.get(key);
        if (value == null) {
            throw new RuntimeException(String.format("No value set for configuration key %s.", key));
        }
        return value;
    }

    public void set(PropertyKey key, Object value) {
        Preconditions.checkArgument(!value.equals(""),
                String.format("The key \"%s\" cannot be have an empty string as a value. Use "
                        + "Configuration.unset to remove a key from the configuration.", key));
        Preconditions.checkArgument(key.validateValue(value),
                String.format("Invalid value for property key %s: %s", key, value));
        value = key.formatValue(value);
        properties.set(key, value);
    }

    @Override
    public boolean isSet(PropertyKey key) {
        return properties.isSet(key);
    }

    @Override
    public Set<PropertyKey> keySet() {
        return properties.keySet();
    }

    @Override
    public String getString(PropertyKey key) {
        if (key.getType() != PropertyKey.PropertyType.STRING) {
            LOG.warn("PropertyKey {}'s type is {}, please use proper getter method for the type, "
                            + "getString will no longer work for non-STRING property types in 3.0",
                    key, key.getType());
        }
        Object value = get(key);
        if (value instanceof String) {
            return (String) value;
        }
        return value.toString();
    }

    @Override
    public boolean getBoolean(PropertyKey key) {
        Preconditions.checkArgument(key.getType() == PropertyKey.PropertyType.BOOLEAN);
        return (boolean) get(key);
    }

    @Override
    public int getInt(PropertyKey key) {
        Preconditions.checkArgument(key.getType() == PropertyKey.PropertyType.INTEGER);
        return (int) get(key);
    }

    @Override
    public long getLong(PropertyKey key) {
        Preconditions.checkArgument(key.getType() == PropertyKey.PropertyType.LONG
                || key.getType() == PropertyKey.PropertyType.INTEGER);
        return ((Number) get(key)).longValue();
    }

    @Override
    public List<String> getList(PropertyKey key) {
        Preconditions.checkArgument(key.getType() == PropertyKey.PropertyType.LIST);
        String value = (String) get(key);
        // todo fix
        return Arrays.stream(value.split(key.getDelimiter())).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        // Cannot use Collectors.toMap because we support null keys.
        keySet().forEach(key -> map.put(key.getName(), getOrDefault(key, null)));
        return map;
    }

    @Override
    public Properties toProp() {
        Properties prop = new Properties();
        keySet().forEach(key -> {
            Object value = getOrDefault(key, "");
            if (StringUtil.isNotBlank(value.toString())) {
                prop.setProperty(key.getName(), value.toString());
            }
        });
        return prop;
    }

}
