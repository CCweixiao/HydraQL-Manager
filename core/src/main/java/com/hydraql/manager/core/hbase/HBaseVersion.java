package com.hydraql.manager.core.hbase;

import java.util.regex.Pattern;

/**
 * @author leojie 2024/2/23 15:12
 */
public enum HBaseVersion {
    HBASE_1_2("hbase-1.2", "(hbase-?1\\.2(\\.(\\d+))?|1\\.2(\\.(\\d+)(-.*)?)?)"),
    HBASE_1_3("hbase-1.3", "(hbase-?1\\.3(\\.(\\d+))?|1\\.3(\\.(\\d+)(-.*)?)?)"),
    HBASE_1_4("hbase-1.4", "(hbase-?1\\.4(\\.(\\d+))?|1\\.4(\\.(\\d+)(-.*)?)?)"),
    HBASE_2_2("hbase-2.2", "(hbase-?2\\.2(\\.(\\d+))?|2\\.2(\\.(\\d+)(-.*)?)?)"),
    HBASE_2_3("hbase-2.3", "(hbase-?2\\.3(\\.(\\d+))?|2\\.3(\\.(\\d+)(-.*)?)?)"),
    HBASE_2_4("hbase-2.4", "(hbase-?2\\.4(\\.(\\d+))?|2\\.4(\\.(\\d+)(-.*)?)?)"),
    HBASE_2_5("hbase-2.5", "(hbase-?2\\.5(\\.(\\d+))?|2\\.5(\\.(\\d+)(-.*)?)?)"),
    HBASE_2_6("hbase-2.6", "(hbase-?2\\.6(\\.(\\d+))?|2\\.6(\\.(\\d+)(-.*)?)?)"),
            ;

    private final String mCanonicalVersion;
    private final Pattern mVersionPattern;

    HBaseVersion(String canonicalVersion, String versionPattern) {
        mCanonicalVersion = canonicalVersion;
        mVersionPattern = Pattern.compile(versionPattern);
    }

    public static HBaseVersion find(String versionString) {
        for (HBaseVersion version : HBaseVersion.values()) {
            if (version.mVersionPattern.matcher(versionString).matches()) {
                return version;
            }
        }
        return null;
    }

    /**
     * @param versionA base version
     * @param versionB version to compare
     * @return if the two versions match explicitly or by pattern
     */
    public static boolean matches(String versionA, String versionB) {
        if (versionA.equals(versionB)) {
            return true;
        }
        if (HBaseVersion.find(versionA) != null
                && HBaseVersion.find(versionA) == HBaseVersion.find(versionB)) {
            return true;
        }
        return false;
    }

    /**
     * @return the canonical version string
     */
    public String getCanonicalVersion() {
        return mCanonicalVersion;
    }
}
