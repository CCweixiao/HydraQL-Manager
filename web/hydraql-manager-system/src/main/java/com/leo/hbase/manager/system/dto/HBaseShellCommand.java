package com.leo.hbase.manager.system.dto;

/**
 * @author leojie 2023/7/10 22:02
 */
public class HBaseShellCommand {
    private String clusterId;
    private String command;
    private long startTime;

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "HBaseShellCommand{" +
                "clusterId='" + clusterId + '\'' +
                ", command='" + command + '\'' +
                ", startTime=" + startTime +
                '}';
    }
}
