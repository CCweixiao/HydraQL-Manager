package com.leo.hbase.manager.system.dto;

import java.util.*;

/**
 * @author leojie 2023/7/12 09:43
 */
public class HBaseShellCommandModel {

    private HBaseShellCommandModel() {

    }

    private static Map<String, List<String>> emptyOptions() {
        Map<String, List<String>> empty = new HashMap<>(2);
        empty.put("options", new ArrayList<>(0));
        empty.put("args", new ArrayList<>(0));
        return empty;
    }

    public static Map<String, Map<String, List<String>>> generateHBaseShellCommand(List<String> commands) {
        Map<String, Map<String, List<String>>> commandData = new HashMap<>(commands.size());
        for (String command : commands) {
            commandData.put(command, emptyOptions());
        }
        return commandData;
    }

    public static  Map<String, Map<String, List<String>>> generateDefaultHBaseShellCommand() {
        return generateHBaseShellCommand(Arrays.asList("list", "list_namespace"));
    }

    public static void main(String[] args) {
        Map<String, Map<String, List<String>>> data = generateDefaultHBaseShellCommand();
        System.out.println(data);
    }

}
