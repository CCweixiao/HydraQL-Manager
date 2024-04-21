package com.hydraql.manager.core.conf;

import com.hydraql.manager.core.util.Preconditions;
import com.hydraql.manager.core.util.StringUtil;
import org.apache.logging.log4j.util.Strings;

import static com.hydraql.manager.core.conf.PropertyKey.Builder.stringBuilder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author leojie 2024/2/23 10:53
 */
public class PropertyKey implements Comparable<PropertyKey> {
    private static final Map<String, PropertyKey> DEFAULT_KEYS_MAP = new ConcurrentHashMap<>();

    public enum PropertyType {
        BOOLEAN(Boolean.class),
        INTEGER(Integer.class),
        LONG(Long.class),
        STRING(String.class),
        LIST(String.class);

        private final Class<?> mJavaType;

        PropertyType(Class<?> javaType) {
            mJavaType = javaType;
        }

        Class<?> getJavaType() {
            return mJavaType;
        }
    }

    public static final class Builder {
        private DefaultSupplier mDefaultSupplier;
        private Object mDefaultValue;
        private String mDescription;
        private String mName;
        private final PropertyType mType;
        private boolean mIsBuiltIn = true;
        private final Optional<String> mDelimiter;
        private Function<Object, Boolean> mValueValidationFunction;

        public static Builder booleanBuilder(String name) {
            return new Builder(name, PropertyType.BOOLEAN);
        }

        public static Builder intBuilder(String name) {
            return new Builder(name, PropertyType.INTEGER);
        }

        public static Builder longBuilder(String name) {
            return new Builder(name, PropertyType.LONG);
        }

        public static Builder stringBuilder(String name) {
            return new Builder(name, PropertyType.STRING);
        }

        public static Builder listBuilder(String name) {
            return new Builder(name, PropertyType.LIST, Optional.of(","));
        }

        private Builder(String name, PropertyType type) {
            this(name, type, Optional.empty());
        }

        private Builder(
                String name,
                PropertyType type,
                Optional<String> delimiter) {
            mName = name;
            mType = type;
            mDelimiter = delimiter;
        }

        public Builder setName(String name) {
            mName = name;
            return this;
        }

        public Builder setDefaultSupplier(DefaultSupplier defaultSupplier) {
            if (defaultSupplier.get() == null
                    || defaultSupplier.get().getClass().equals(mType.getJavaType())) {
                mDefaultSupplier = defaultSupplier;
            } else {
                mDefaultSupplier = new DefaultSupplier(() -> String.valueOf(defaultSupplier.get()),
                        defaultSupplier.getDescription());
            }
            return this;
        }

        public Builder setDefaultValue(Object defaultValue) {
            Preconditions.checkArgument(validateValue(defaultValue, mType, mValueValidationFunction));
            mDefaultValue = defaultValue;
            return this;
        }

        public Builder setDefaultSupplier(Supplier<Object> supplier, String description) {
            return setDefaultSupplier(new DefaultSupplier(supplier, description));
        }

        public Builder setDescription(String description) {
            mDescription = description;
            return this;
        }
        public Builder setValueValidationFunction(Function<Object, Boolean> valueValidationFunction) {
            mValueValidationFunction = valueValidationFunction;
            return this;
        }

        public PropertyKey build() {
            PropertyKey key = buildUnregistered();
            Preconditions.checkState(PropertyKey.register(key),
                    String.format("Cannot register existing key \"%s\"", mName));
            return key;
        }

        public PropertyKey buildUnregistered() {
            DefaultSupplier defaultSupplier = mDefaultSupplier;
            if (defaultSupplier == null) {
                if (mDefaultValue == null) {
                    defaultSupplier = new DefaultSupplier(() -> null, "null");
                } else {
                    defaultSupplier = new DefaultSupplier(() -> mDefaultValue, String.valueOf(mDefaultValue));
                }
            }

            if (mValueValidationFunction != null && defaultSupplier.get() != null) {
                Preconditions.checkState(mValueValidationFunction.apply(defaultSupplier.get()),
                        String.format("Invalid value for property key %s: %s", mName, defaultSupplier.get()));
            }

            return new PropertyKey(mName, mDescription, mType, mDelimiter, defaultSupplier,
                    mIsBuiltIn, mValueValidationFunction);
        }

        @Override
        public String toString() {
            return "defaultValue=" + mDefaultValue + "," +
                    "description=" + mDescription + "," +
                    "name=" + mName + ";";
        }

    }

    public static final class Name {
        public static final String HOME = "hydraql.manager.home";
        public static final String HBASE_VERSION = "hydraql.hbase.version";
        public static final String HBASE_STRICT_VERSION_MATCH_ENABLED = "hydraql.hbase.strict.version.match.enabled";
        public static final String PLUGINS_DIR = "hydraql.plugins.dir";
        public static final String HBASE_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
        public static final String HBASE_ZOOKEEPER_CLIENT_PORT = "hbase.zookeeper.property.clientPort";
        public static final String HBASE_ZOOKEEPER_NODE_PARENT = "zookeeper.znode.parent";
        public static final String HBASE_DFS_ROOT_DIR = "hbase.rootdir";
        public static final String HBASE_SECURITY_AUTH = "hbase.security.authentication";
        public static final String HBASE_REGION_SERVER_KERBEROS_PRINCIPAL = "hbase.regionserver.kerberos.principal";
        public static final String HBASE_MASTER_KERBEROS_PRINCIPAL = "hbase.master.kerberos.principal";
        public static final String KRB5_CONF_PATH = "java.security.krb5.conf";
        public static final String KRB5_REALM = "java.security.krb5.realm";
        public static final String KRB5_KDC_SERVER_ADDR = "java.security.krb5.kdc";
        public static final String HBASE_KERBEROS_PROXY_USER = "kerberos.proxy.user";
        public static final String HBASE_KERBEROS_PRINCIPAL = "kerberos.principal";
        public static final String HBASE_KERBEROS_KEYTAB_FILE = "keytab.file";

        public static final String FILTER_NAMESPACE_PREFIX = "filter.namespace.prefix";
        public static final String FILTER_TABLE_NAME_PREFIX = "filter.table.name.prefix";
        public static final String HBASE_CLIENT_RETRY = "hbase.client.retries.number";
    }

    public static final PropertyKey HYDRAQL_MANAGER_PLUGINS_DIR =
            stringBuilder(Name.PLUGINS_DIR)
                    .setDefaultValue("")
                    .setDescription("hydraql manager plugins dir.")
                    .build();

    public static final PropertyKey HYDRAQL_HBASE_VERSION =
            stringBuilder(Name.HBASE_VERSION)
                    .setDefaultValue("1.2.0")
                    .setDescription("hbase version")
                    .build();

    public static final PropertyKey HYDRAQL_HBASE_STRICT_VERSION_MATCH_ENABLED =
            Builder.booleanBuilder(Name.HBASE_STRICT_VERSION_MATCH_ENABLED)
                    .setDefaultValue(false)
                    .setDescription("When enabled, Hydraql finds the hbase plugin by strict version "
                            + "matching. Otherwise only version prefix is compared.")
                    .build();

    public static final PropertyKey HYDRAQL_HBASE_ZOOKEEPER_QUORUM =
            stringBuilder(Name.HBASE_ZOOKEEPER_QUORUM)
                    .setDefaultValue("localhost")
                    .setDescription("")
                    .build();

    public static final PropertyKey HYDRAQL_HBASE_ZOOKEEPER_CLIENT_PORT =
            stringBuilder(Name.HBASE_ZOOKEEPER_CLIENT_PORT)
                    .setDefaultValue("2181")
                    .setDescription("")
                    .build();

    public static final PropertyKey HYDRAQL_HBASE_ZOOKEEPER_NODE_PARENT =
            stringBuilder(Name.HBASE_ZOOKEEPER_NODE_PARENT)
                    .setDefaultValue("/hbase")
                    .setDescription("")
                    .build();

    public static final PropertyKey HYDRAQL_HBASE_DFS_ROOT_DIR =
            stringBuilder(Name.HBASE_DFS_ROOT_DIR)
                    .setDefaultValue("/hbase")
                    .setDescription("")
                    .build();

    public static final PropertyKey HYDRAQL_HBASE_SECURITY_AUTH =
            stringBuilder(Name.HBASE_SECURITY_AUTH)
                    .setDefaultValue("simple")
                    .setDescription("")
                    .build();

    public static final PropertyKey HYDRAQL_HBASE_REGION_SERVER_KERBEROS_PRINCIPAL =
            stringBuilder(Name.HBASE_REGION_SERVER_KERBEROS_PRINCIPAL)
                    .setDefaultValue("")
                    .setDescription("")
                    .build();

    public static final PropertyKey HYDRAQL_HBASE_MASTER_KERBEROS_PRINCIPAL =
            stringBuilder(Name.HBASE_MASTER_KERBEROS_PRINCIPAL)
                    .setDefaultValue("")
                    .setDescription("")
                    .build();

    public static final PropertyKey HYDRAQL_HBASE_KERBEROS_PRINCIPAL =
            stringBuilder(Name.HBASE_KERBEROS_PRINCIPAL)
                    .setDefaultValue("")
                    .setDescription("")
                    .build();

    public static final PropertyKey HYDRAQL_HBASE_KERBEROS_KEYTAB_FILE =
            stringBuilder(Name.HBASE_KERBEROS_KEYTAB_FILE)
                    .setDefaultValue("")
                    .setDescription("")
                    .build();

    public static final PropertyKey HYDRAQL_HBASE_KERBEROS_PROXY_USER =
            stringBuilder(Name.HBASE_KERBEROS_PROXY_USER)
                    .setDefaultValue("")
                    .setDescription("")
                    .build();

    public static final PropertyKey HYDRAQL_KRB5_CONF_PATH =
            stringBuilder(Name.KRB5_CONF_PATH)
                    .setDefaultValue("/etc/krb5.conf")
                    .setDescription("")
                    .build();

    public static final PropertyKey HYDRAQL_KRB5_REALM =
            stringBuilder(Name.KRB5_REALM)
                    .setDefaultValue("")
                    .setDescription("")
                    .build();

    public static final PropertyKey HYDRAQL_KRB5_KDC_SERVER_ADDR =
            stringBuilder(Name.KRB5_KDC_SERVER_ADDR)
                    .setDefaultValue("")
                    .setDescription("")
                    .build();

    public static final PropertyKey HYDRAQL_HBASE_CLIENT_RETRY =
            stringBuilder(Name.HBASE_CLIENT_RETRY)
                    .setDefaultValue(3)
                    .setDescription("")
                    .build();

    public static Collection<? extends PropertyKey> defaultKeys() {
        return DEFAULT_KEYS_MAP.values();
    }

    private final String mName;
    private final String mDescription;
    private final PropertyType mType;
    private final Optional<String> mDelimiter;
    private final DefaultSupplier mDefaultSupplier;
    private final boolean mIsBuiltIn;
    private final Function<Object, Boolean> mValueValidationFunction;

    private PropertyKey(String name, String description, PropertyType type, Optional<String> delimiter,
                        DefaultSupplier defaultSupplier, boolean isBuiltIn,
                        Function<Object, Boolean> valueValidationFunction) {
        mName = Preconditions.checkNotNull(name, "name");
        mDescription = StringUtil.isBlank(description) ? "N/A" : description;
        mType = type;
        mDelimiter = delimiter;
        mDefaultSupplier = defaultSupplier;
        mIsBuiltIn = isBuiltIn;
        mValueValidationFunction = valueValidationFunction;
    }

    public static boolean register(PropertyKey key) {
        String name = key.getName();
        if (DEFAULT_KEYS_MAP.containsKey(name)) {
            if (DEFAULT_KEYS_MAP.get(name).isBuiltIn() || !key.isBuiltIn()) {
                return false;
            }
        }

        DEFAULT_KEYS_MAP.put(name, key);
        return true;
    }

    public PropertyType getType() {
        return mType;
    }

    public String getDelimiter() {
        Preconditions.checkState(mType == PropertyType.LIST && mDelimiter.isPresent(),
                String.format("PropertyKey %s is not of list type", mName));
        return mDelimiter.get();
    }

    public String getDescription() {
        return mDescription;
    }

    public String getName() {
        return mName;
    }

    public Object getDefaultValue() {
        return mDefaultSupplier.get();
    }

    public DefaultSupplier getDefaultSupplier() {
        return mDefaultSupplier;
    }

    public boolean isBuiltIn() {
        return mIsBuiltIn;
    }

    public boolean validateValue(Object value) {
        return validateValue(value, mType, mValueValidationFunction);
    }

    public static PropertyKey fromString(String input) {
        PropertyKey key = DEFAULT_KEYS_MAP.get(input);
        if (key != null) {
            return key;
        }
        throw new IllegalArgumentException("Unknown configuration " + input);
    }

    private static boolean validateValue(
            Object value, PropertyType type,
            Function<Object, Boolean> valueValidationFunction) {

        switch (type) {
            case BOOLEAN:
            case INTEGER:
                if (!type.getJavaType().equals(value.getClass())) {
                    return false;
                }
                break;
            case LONG:
                // Low-precision types can be implicitly converted to high-precision types
                // without loss of precision
                if (!value.getClass().equals(Integer.class) && !value.getClass().equals(Long.class)) {
                    return false;
                }
                break;
            case LIST:
                if (!(value instanceof List)) {
                    return false;
                }
                break;
            default:
                break;
        }

        if (valueValidationFunction == null) {
            return true;
        }
        return valueValidationFunction.apply(value);
    }

    public Object formatValue(Object value) {
        return formatValue(value, mType, mDelimiter);
    }

    private static Object formatValue(Object value, PropertyType type, Optional<String> delimiter) {
        if (value instanceof Number) {
            switch (type) {
                case LONG:
                    value = ((Number) value).longValue();
                    break;
                case INTEGER:
                    value = ((Number) value).intValue();
                    break;
                default:
                    break;
            }
        } else if (value instanceof String) {
            return value;
        } else if (value instanceof List) {
            Preconditions.checkArgument(type == PropertyType.LIST);
            // todo fix
            value = Strings.join((List) value, ',');
        }
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PropertyKey)) {
            return false;
        }
        PropertyKey that = (PropertyKey) o;
        return Objects.equals(mName, that.mName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mName);
    }

    @Override
    public String toString() {
        return mName;
    }

    @Override
    public int compareTo(PropertyKey o) {
        return mName.compareTo(o.mName);
    }
}
