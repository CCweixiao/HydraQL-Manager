package com.hydraql.manager.core.conf;

import java.util.function.Supplier;

/**
 * @author leojie 2024/2/23 11:06
 */
public class DefaultSupplier implements Supplier<Object> {
    private final Supplier<Object> mSupplier;
    private final String mDescription;

    /**
     * @param supplier the value
     * @param description a description of the default value
     */
    public DefaultSupplier(Supplier<Object> supplier, String description) {
        mSupplier = supplier;
        mDescription = description;
    }

    @Override
    public Object get() {
        return mSupplier.get();
    }

    public String getDescription() {
        return mDescription;
    }
}