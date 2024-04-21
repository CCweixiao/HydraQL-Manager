package com.hydraql.manager.core.extensions;

import com.hydraql.manager.core.conf.HydraQLConfiguration;
import com.hydraql.manager.core.conf.PropertyKey;
import com.hydraql.manager.core.template.HydraQLTemplateFactory;
import com.hydraql.manager.core.util.ExtensionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @author leojie 2024/1/25 15:17
 */
public class ExtensionFactoryRegistry<T extends ExtensionFactory<?, S>,
        S extends HydraQLConfiguration> {
    private static final Logger LOG = LoggerFactory.getLogger(ExtensionFactoryRegistry.class);
    public static final String UNKNOWN_VERSION = "unknown";

    private final List<T> mFactories = new CopyOnWriteArrayList<>();
    private final String mExtensionPattern;
    private final Class<T> mFactoryClass;
    private boolean mInit = false;

    public ExtensionFactoryRegistry(Class<T> factoryClass, String extensionPattern) {
        this.mFactoryClass = factoryClass;
        this.mExtensionPattern = extensionPattern;
        init();
    }

    private synchronized void init() {
        ServiceLoader<T> discoveredFactories =
                ServiceLoader.load(mFactoryClass, mFactoryClass.getClassLoader());
        for (T factory : discoveredFactories) {
            LOG.debug("Discovered base extension factory implementation {} - {}", factory.getClass(),
                    factory);
            register(factory);
        }
    }

    public List<T> getAvailable() {
        return Collections.unmodifiableList(mFactories);
    }

    public List<T> findAll(S conf) {
        List<T> eligibleFactories = scanRegistered(conf);
        if (!eligibleFactories.isEmpty()) {
            LOG.info("Find {} eligible items from registered factories.", eligibleFactories.size());
            return eligibleFactories;
        }

        List<T> factories = new ArrayList<>(mFactories);
        String pluginsDir = conf.getString(PropertyKey.HYDRAQL_MANAGER_PLUGINS_DIR);
        scanExtensions(factories, pluginsDir);
        LOG.info("Loaded plugin jars from {}, And "
                + "the total number of loaded factory core jars is {}", pluginsDir, factories.size());
        if (conf.isSet(PropertyKey.HYDRAQL_HBASE_VERSION)) {
            LOG.info("hydraql.hbase.version is set by user, target version is {}",
                    conf.getString(PropertyKey.HYDRAQL_HBASE_VERSION));
        } else {
            LOG.info("hydraql.hbase.version is not set by user");
        }

        for (T factory : factories) {
            // if `getVersion` returns null set the version to "unknown"
            String version = UNKNOWN_VERSION;
            if (factory instanceof HydraQLTemplateFactory) {
                version = Optional.ofNullable(((HydraQLTemplateFactory) factory)
                        .getVersion()).orElse(UNKNOWN_VERSION);
            }
            if (factory.supportsVersion(conf)) {
                LOG.info(String.format("Adding factory %s of version %s", factory.getClass().getSimpleName(), version));
                eligibleFactories.add(factory);
            } else {
                LOG.info("Factory implementation {} of version {} "
                        + "isn't eligible", factory.getClass().getSimpleName(), version);
            }
        }

        if (eligibleFactories.isEmpty()) {
            LOG.warn("No factory implementation.");
        }
        return eligibleFactories;
    }

    public List<T> scanRegistered(S conf) {
        return mFactories.stream()
                .filter(factory -> factory.supportsVersion(conf))
                .collect(Collectors.toList());
    }

    private void scanExtensions(List<T> factories, String extensionsDir) {
        LOG.info("Loading extension jars from {}", extensionsDir);
        scan(Arrays.asList(ExtensionUtils.listExtensions(extensionsDir)), factories);
    }

    private void scanLibs(List<T> factories, String libDir) {
        LOG.info("Loading core jars from {}", libDir);
        List<File> files = new ArrayList<>();
        try (DirectoryStream<Path> stream =
                     Files.newDirectoryStream(Paths.get(libDir), mExtensionPattern)) {
            for (Path entry : stream) {
                if (entry.toFile().isFile()) {
                    files.add(entry.toFile());
                }
            }
        } catch (IOException e) {
            LOG.warn("Failed to load libs: {}", e.toString());
        }
        scan(files, factories);
    }

    public void scan(List<File> files, List<T> factories) {
        for (File jar : files) {
            try {
                URL extensionURL = jar.toURI().toURL();
                String jarPath = extensionURL.toString();
                ClassLoader extensionsClassLoader = new ExtensionsClassLoader(new URL[]{extensionURL},
                        ClassLoader.getSystemClassLoader());
                ServiceLoader<T> extensionServiceLoader =
                        ServiceLoader.load(mFactoryClass, extensionsClassLoader);
                for (T factory : extensionServiceLoader) {
                    LOG.debug("Discovered a factory implementation {} - {} in jar {}", factory.getClass(),
                            factory, jarPath);
                    register(factory, factories);
                    register(factory);
                }
            } catch (Throwable t) {
                LOG.warn("Failed to load jar {}: {}", jar, t.toString());
            }
        }
    }


    public void register(T factory) {
        register(factory, mFactories);
    }

    private void register(T factory, List<T> factories) {
        if (factory == null) {
            return;
        }
        // Insert at start of list so it will take precedence over automatically discovered and
        // previously registered factories
        factories.add(0, factory);
    }

    public synchronized void reset() {
        if (mInit) {
            // Reset state
            mInit = false;
            mFactories.clear();
        }

        // Reinitialise
        init();
    }

    public void unregister(T factory) {
        unregister(factory, mFactories);
    }

    private void unregister(T factory, List<T> factories) {
        if (factory == null) {
            return;
        }

        factories.remove(factory);
    }

}
