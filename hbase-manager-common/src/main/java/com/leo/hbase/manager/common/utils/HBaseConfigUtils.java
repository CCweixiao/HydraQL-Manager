package com.leo.hbase.manager.common.utils;

import com.leo.hbase.manager.common.constant.HBaseManagerConstants;
import com.leo.hbase.manager.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.*;

/**
 * @author leojie 2020/9/24 8:54 下午
 */
public class HBaseConfigUtils {
    private static final Properties CONFIG;

    private static final Logger LOG = LoggerFactory.getLogger(HBaseConfigUtils.class);

    static {
        CONFIG = new Properties();
        getResources("hbase-manager.properties");
    }

    private static void getResources(String name) {
        try {
            try {
                String osName = System.getProperties().getProperty("os.name");
                if (osName.contains(HBaseManagerConstants.MAC) || osName.contains(HBaseManagerConstants.WIN)) {
                    CONFIG.load(HBaseConfigUtils.class.getClassLoader().getResourceAsStream(name));
                } else {
                    CONFIG.load(new FileInputStream(System.getProperty("user.dir") + "/conf/" + name));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            LOG.debug("Successfully loaded default properties.");

            if (LOG.isDebugEnabled()) {
                LOG.debug("SysteCONFIG looks like this ...");

                String key;
                Enumeration<Object> keys = CONFIG.keys();
                while (keys.hasMoreElements()) {
                    key = (String) keys.nextElement();
                    LOG.debug(key + "=" + CONFIG.getProperty(key));
                }
            }
        } catch (Exception e) {
            LOG.error("Load system name has error,msg is " + e.getMessage());
        }
    }

    /**
     * Retrieve a property as a boolean ... defaults to false if not present.
     */
    public static boolean getBooleanProperty(String name) {
        return getBooleanProperty(name, false);
    }

    /**
     * Retrieve a property as a boolean with specified default if not present.
     */
    public static boolean getBooleanProperty(String name, boolean defaultValue) {
        String value = HBaseConfigUtils.getProperty(name);
        if (value == null) {
            return defaultValue;
        }
        return Boolean.valueOf(value).booleanValue();
    }

    /**
     * Retrieve a property as a boolean array.
     */
    public static boolean[] getBooleanPropertyArray(String name, boolean[] defaultValue, String splitStr) {
        String value = HBaseConfigUtils.getProperty(name);
        if (value == null) {
            return defaultValue;
        }
        try {
            String[] propertyArray = value.split(splitStr);
            boolean[] result = new boolean[propertyArray.length];
            for (int i = 0; i < propertyArray.length; i++) {
                result[i] = Boolean.valueOf(propertyArray[i]).booleanValue();
            }
            return result;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * Retrieve a property as a int,defaults to 0 if not present.
     */
    public static int getIntProperty(String name) {
        return getIntProperty(name, 0);
    }

    /**
     * Retrieve a property as a int.
     */
    public static int getIntProperty(String name, int defaultValue) {
        String value = HBaseConfigUtils.getProperty(name);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * Retrieve a property as a int array.
     */
    public static int[] getIntPropertyArray(String name, int[] defaultValue, String splitStr) {
        String value = HBaseConfigUtils.getProperty(name);
        if (value == null) {
            return defaultValue;
        }
        try {
            String[] propertyArray = value.split(splitStr);
            int[] result = new int[propertyArray.length];
            for (int i = 0; i < propertyArray.length; i++) {
                result[i] = Integer.parseInt(propertyArray[i]);
            }
            return result;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Retrieve a property as a long,defaults to 0L if not present.
     */
    public static Long getLongProperty(String name) {
        return getLongProperty(name, 0L);
    }

    /**
     * Retrieve a property as a long.
     */
    public static Long getLongProperty(String name, Long defaultValue) {
        String value = HBaseConfigUtils.getProperty(name);
        if (value == null || "".equals(value)) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * Retrieve a property value.
     */
    public static String getProperty(String key) {
        return CONFIG.getProperty(key);
    }

    /**
     * Retrieve a property value & default value.
     *
     * @param key          Retrieve key
     * @param defaultValue Return default retrieve value
     * @return String.
     */
    public static String getProperty(String key, String defaultValue) {
        LOG.debug("Fetching property [" + key + "=" + CONFIG.getProperty(key) + "]");
        String value = HBaseConfigUtils.getProperty(key);
        if (value == null || "".equals(value)) {
            return defaultValue;
        }
        return value;
    }

    /**
     * Retrieve a property as a array.
     */
    public static String[] getPropertyArray(String name, String[] defaultValue, String splitStr) {
        String value = HBaseConfigUtils.getProperty(name);
        if (value == null) {
            return defaultValue;
        }
        try {
            return value.split(splitStr);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Retrieve a property as a array,no default value.
     */
    public static String[] getPropertyArray(String name, String splitStr) {
        String value = HBaseConfigUtils.getProperty(name);
        if (value == null) {
            return null;
        }
        try {
            return value.split(splitStr);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Retrieve a property as a array,no default value.
     */
    public static List<String> getPropertyArrayList(String name, String splitStr) {
        String value = HBaseConfigUtils.getProperty(name);
        List<String> list = new ArrayList<>();
        if (value == null) {
            return list;
        }
        try {
            String[] propertyArray = value.split(splitStr);
            list.addAll(Arrays.asList(propertyArray));
        } catch (NumberFormatException e) {
            LOG.error("Split string to list has error, msg is " + e.getCause().getMessage());
        }
        return list;
    }

    /**
     * Retrieve map property keys.
     */
    public static Map<String, String> getPropertyMap(String name) {
        String[] maps = getPropertyArray(name, ",");
        Map<String, String> map = new HashMap<>();
        if (maps == null) {
            return map;
        }
        try {
            for (String str : maps) {
                String[] array = str.split(":");
                if (array.length > 1) {
                    map.put(array[0], array[1]);
                }
            }
        } catch (Exception e) {
            LOG.error("Get PropertyMap info has error,key is :" + name);
        }
        return map;
    }

    /**
     * Retrieve all property keys.
     */
    public static Enumeration<Object> keys() {
        return CONFIG.keys();
    }

    /**
     * Reload special property file.
     *
     * @param name System configure name.
     */
    public static void reload(String name) {
        CONFIG.clear();
        getResources(name);
    }

    /**
     * Construction method.
     */
    private HBaseConfigUtils() {
    }

    public static List<String> getAllClusterAlias() {
        List<String> clusterCodes = HBaseConfigUtils.getPropertyArrayList("hbase.manager.zk.cluster.alias", ",");
        if (clusterCodes.isEmpty()) {
            throw new BusinessException("请在hbase-manager.properties中配置需要管理的集群");
        }
        return clusterCodes;
    }

}
