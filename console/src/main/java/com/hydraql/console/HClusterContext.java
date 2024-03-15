package com.hydraql.console;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author leojie 2023/7/30 10:48
 */
public class HClusterContext {
    private final ConcurrentMap<String, Map<String, String>> clusterConf;
    private String currentSelectedCluster;
    private final Lock lock = new ReentrantLock();

    public void setCurrentSelectedCluster(String clusterName) {
        lock.lock();
        try {
            this.currentSelectedCluster = clusterName;
        } finally {
            lock.unlock();
        }
    }

    public String getCurrentSelectedCluster() {
        return this.currentSelectedCluster;
    }

    private HClusterContext() {
        clusterConf = new ConcurrentHashMap<>();
    }
    public static class HClusterContextHolder {
        public static final HClusterContext INSTANCE = new HClusterContext();
    }

    public static HClusterContext getInstance() {
        return HClusterContextHolder.INSTANCE;
    }

    public Map<String, String> getCurrentClusterProperties() {
        if (StringUtil.isBlank(getCurrentSelectedCluster())) {
            throw new IllegalStateException("Please switch cluster first.");
        }
        return getClusterProperties(getCurrentSelectedCluster());
    }

    private Map<String, String> getClusterProperties(String clusterName) {
        lock.lock();
        try {
            Map<String, String> p = clusterConf.get(clusterName);
            if (p != null) {
                return p;
            }
            p = readConf(clusterName);
            clusterConf.put(clusterName, p);
            return readConf(clusterName);
        } finally {
            lock.unlock();
        }
    }

    private Map<String, String> readConf(String clusterName) {
        Map<String, String> p = new HashMap<>();
        String clusterConfDirPath = getClusterConfigDirPath();
        File clusterConfFile = new File(clusterConfDirPath.concat(File.separator).concat(clusterName)
                .concat(".properties"));
        if (!clusterConfFile.exists()) {
            throw new IllegalStateException(String.format("The cluster %s not exists.", clusterName));
        }
        try (BufferedReader br = Files.newBufferedReader(Paths.get(clusterConfFile.toURI()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] kv = line.split("=");
                p.put(kv[0], kv[1]);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return p;
    }

    public File getClusterConfDirFile() {
        String clusterConfDirPath = getClusterConfigDirPath();
        File clusterConfDirFile = new File(clusterConfDirPath);
        if (!clusterConfDirFile.exists()) {
            clusterConfDirFile.mkdirs();
        }
        if (!clusterConfDirFile.isDirectory()) {
            throw new IllegalStateException("Please ensure that the default storage path of the cluster" +
                    " configuration file is a folder.");
        }
        return clusterConfDirFile;
    }

    private String getClusterConfigDirPath() {
        File userDirFile = new File(System.getProperty("user.dir"));
        return userDirFile.getAbsolutePath().concat(File.separator).concat("cluster_info_conf");
    }
}
