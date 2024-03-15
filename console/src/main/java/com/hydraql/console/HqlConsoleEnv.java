package com.hydraql.console;

import java.io.File;

/**
 * @author leojie 2024/3/15 15:03
 */
public final class HqlConsoleEnv {
    private static final String HYDRAQL_CONSOLE_HOME_NAME = "hydraql_console_home";
    private static final String HYDRAQL_CONSOLE_DEFAULT_HOME = "/tmp/hydraql";
    private static final String HYDRAQL_PLUGINS_NAME = "plugins";
    private static final String HQDRAQL_CLUSTER_INFO_CONF = "cluster_info_conf";

    private HqlConsoleEnv() {

    }

    private static String getHqlConsoleHome() {
        return System.getProperty(HYDRAQL_CONSOLE_HOME_NAME, HYDRAQL_CONSOLE_DEFAULT_HOME);
    }

    public static String getHqlConsolePluginsHome() {
        String home = getHqlConsoleHome();
        return new File(home, HYDRAQL_PLUGINS_NAME).getPath();
    }

    public static String getHqlConsoleClusterConfDir() {
        String hqlConsoleHome = HqlConsoleEnv.getHqlConsoleHome();
        return new File(hqlConsoleHome, HQDRAQL_CLUSTER_INFO_CONF).getPath();
    }
}
