package com.hydraql.manager.core.extensions;

/**
 * @author leojie 2024/1/25 15:15
 */
public interface ExtensionFactory<T, S> {
    T create(S conf);

    boolean supportsVersion(S conf);
}
