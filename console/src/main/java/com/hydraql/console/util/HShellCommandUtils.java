package com.hydraql.console.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author leojie 2024/3/15 15:30
 */
public final class HShellCommandUtils {
    private HShellCommandUtils() {

    }

    public static Set<String> getAllCommands() throws IOException {
        try(InputStream in = HShellCommandUtils.class.getClassLoader()
                .getResourceAsStream("hydraql/console/commands")) {
            assert in != null;
            List<String> contentList = getFileContent(in);
            Set<String> commands = new HashSet<>(contentList.size());
            for (String content : contentList) {
                commands.add(content.trim());
            }
            return commands;
        }
    }

    private static List<String> getFileContent(InputStream in) throws IOException {
        try (BufferedReader br =  new BufferedReader(new InputStreamReader(in))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        }
    }
}
