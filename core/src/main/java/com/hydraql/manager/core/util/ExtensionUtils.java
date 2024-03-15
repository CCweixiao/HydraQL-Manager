package com.hydraql.manager.core.util;

import java.io.File;

/**
 * @author leojie 2024/1/26 09:42
 */
public class ExtensionUtils {
    private static final File[] EMPTY_EXTENSIONS_LIST = new File[0];

    /**
     * List extension jars from the configured extensions directory.
     *
     * @param extensionDir the directory containing extensions
     * @return an array of files (one file per jar)
     */
    public static File[] listExtensions(String extensionDir) {
        File[] extensions = new File(extensionDir)
                .listFiles(file -> file.getPath().toLowerCase().endsWith(".jar"));
        if (extensions == null) {
            // Directory does not exist
            return EMPTY_EXTENSIONS_LIST;
        }
        return extensions;
    }

    private ExtensionUtils() {}
}
