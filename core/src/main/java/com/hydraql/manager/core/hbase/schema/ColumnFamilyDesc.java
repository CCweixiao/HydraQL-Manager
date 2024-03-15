package com.hydraql.manager.core.hbase.schema;

import com.hydraql.manager.core.util.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author leojie 2024/2/23 17:00
 */
public class ColumnFamilyDesc implements Comparable<ColumnFamilyDesc> {
    private String name;
    private int replicationScope;
    private int maxVersions;
    private int minVersions;
    private String compressionType;
    private String storagePolicy;
    private String bloomFilterType;
    private int timeToLive;
    private int blockSize;
    private boolean blockCacheEnabled;
    private boolean inMemory;
    private String keepDeletedCells;
    private String dataBlockEncoding;
    private boolean cacheDataOnWrite;
    private boolean cacheDataInL1;
    private boolean cacheIndexesOnWrite;
    private boolean cacheBloomsOnWrite;
    private boolean evictBlocksOnClose;
    private boolean prefetchBlocksOnOpen;
    private Map<String, String> configuration;
    private Map<String, String> values;
    private boolean mobEnabled;
    private long mobThreshold;

    public ColumnFamilyDesc() {
    }

    private ColumnFamilyDesc(Builder builder) {
        this.name = builder.name;
        this.replicationScope = builder.replicationScope;
        this.maxVersions = builder.maxVersions;
        this.minVersions = builder.minVersions;
        this.compressionType = builder.compressionType;
        this.storagePolicy = builder.storagePolicy;
        this.bloomFilterType = builder.bloomFilterType;
        this.timeToLive = builder.timeToLive;
        this.blockSize = builder.blockSize;
        this.blockCacheEnabled = builder.blockCacheEnabled;
        this.inMemory = builder.inMemory;
        this.keepDeletedCells = builder.keepDeletedCells;
        this.dataBlockEncoding = builder.dataBlockEncoding;
        this.cacheDataOnWrite = builder.cacheDataOnWrite;
        this.cacheDataInL1 = builder.cacheDataInL1;
        this.cacheIndexesOnWrite = builder.cacheIndexesOnWrite;
        this.cacheBloomsOnWrite = builder.cacheBloomsOnWrite;
        this.evictBlocksOnClose = builder.evictBlocksOnClose;
        this.prefetchBlocksOnOpen = builder.prefetchBlocksOnOpen;
        this.configuration = builder.configuration;
        this.values = builder.values;
        this.mobEnabled = builder.mobEnabled;
        this.mobThreshold = builder.mobThreshold;
    }

    public static class Builder {
        private final String name;
        private int replicationScope;
        private int maxVersions;
        private int minVersions;
        private String compressionType;
        private String storagePolicy;
        private String bloomFilterType;
        private int timeToLive;
        private int blockSize;
        private boolean blockCacheEnabled;
        private boolean inMemory;
        private String keepDeletedCells;
        private String dataBlockEncoding;
        private boolean cacheDataOnWrite;
        private boolean cacheDataInL1;
        private boolean cacheIndexesOnWrite;
        private boolean cacheBloomsOnWrite;
        private boolean evictBlocksOnClose;
        private boolean prefetchBlocksOnOpen;
        private Map<String, String> configuration;
        private Map<String, String> values;
        private boolean mobEnabled;
        private long mobThreshold;

        public Builder(String name) {
            this.name = name;
            this.bloomFilterType = "ROW";
            this.replicationScope = 0;
            this.maxVersions = 1;
            this.minVersions = 0;
            this.compressionType = "none";
            this.storagePolicy = null;
            this.timeToLive = Integer.MAX_VALUE;
            this.blockSize = 65536;
            this.inMemory = false;
            this.blockCacheEnabled = true;
            this.keepDeletedCells = "FALSE";
            this.dataBlockEncoding = "NONE";
            this.cacheDataOnWrite = false;
            this.cacheDataInL1 = false;
            this.cacheIndexesOnWrite = false;
            this.cacheBloomsOnWrite = false;
            this.evictBlocksOnClose = false;
            this.prefetchBlocksOnOpen = false;
            this.configuration = new HashMap<>();
            this.values = new HashMap<>();
            this.mobEnabled = false;
            this.mobThreshold = 102400L;
        }

        public Builder replicationScope(int replicationScope) {
            this.replicationScope = replicationScope;
            return this;
        }

        public Builder maxVersions(int maxVersions) {
            this.maxVersions = maxVersions;
            return this;
        }

        public Builder minVersions(int minVersions) {
            this.minVersions = minVersions;
            return this;
        }

        public Builder compressionType(String compressionType) {
            this.compressionType = compressionType;
            return this;
        }

        public Builder storagePolicy(String storagePolicy) {
            this.storagePolicy = storagePolicy;
            return this;
        }

        public Builder bloomFilterType(String bloomFilterType) {
            this.bloomFilterType = bloomFilterType;
            return this;
        }

        public Builder timeToLive(int timeToLive) {
            this.timeToLive = timeToLive;
            return this;
        }

        public Builder blockSize(int blockSize) {
            this.blockSize = blockSize;
            return this;
        }

        public Builder blockCacheEnabled(boolean blockCacheEnabled) {
            this.blockCacheEnabled = blockCacheEnabled;
            return this;
        }

        public Builder inMemory(boolean inMemory) {
            this.inMemory = inMemory;
            return this;
        }

        public Builder keepDeletedCells(String keepDeletedCells) {
            this.keepDeletedCells = keepDeletedCells;
            return this;
        }

        public Builder dataBlockEncoding(String dataBlockEncoding) {
            this.dataBlockEncoding = dataBlockEncoding;
            return this;
        }

        public Builder cacheDataOnWrite(boolean cacheDataOnWrite) {
            this.cacheDataOnWrite = cacheDataOnWrite;
            return this;
        }

        public Builder cacheDataInL1(boolean cacheDataInL1) {
            this.cacheDataInL1 = cacheDataInL1;
            return this;
        }

        public Builder cacheIndexesOnWrite(boolean cacheIndexesOnWrite) {
            this.cacheIndexesOnWrite = cacheIndexesOnWrite;
            return this;
        }

        public Builder cacheBloomsOnWrite(boolean cacheBloomsOnWrite) {
            this.cacheBloomsOnWrite = cacheBloomsOnWrite;
            return this;
        }

        public Builder evictBlocksOnClose(boolean evictBlocksOnClose) {
            this.evictBlocksOnClose = evictBlocksOnClose;
            return this;
        }

        public Builder prefetchBlocksOnOpen(boolean prefetchBlocksOnOpen) {
            this.prefetchBlocksOnOpen = prefetchBlocksOnOpen;
            return this;
        }

        public Builder setConfiguration(String key, String value) {
            if (StringUtil.isBlank(key)) {
                return this;
            }
            this.configuration.put(key, value);
            return this;
        }

        public Builder setConfiguration(Map<String, String> configuration) {
            this.configuration.putAll(configuration);
            return this;
        }

        public Builder setValue(String key, String value) {
            if (StringUtil.isBlank(key)) {
                return this;
            }
            this.values.put(key, value);
            return this;
        }

        public Builder setValue(Map<String, String> values) {
            this.values.putAll(values);
            return this;
        }

        public Builder mobEnabled(boolean mobEnabled) {
            this.mobEnabled = mobEnabled;
            return this;
        }

        public Builder mobThreshold(long mobThreshold) {
            this.mobThreshold = mobThreshold;
            return this;
        }

        public ColumnFamilyDesc build() {
            return new ColumnFamilyDesc(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReplicationScope() {
        return replicationScope;
    }

    public void setReplicationScope(int replicationScope) {
        this.replicationScope = replicationScope;
    }

    public int getMaxVersions() {
        return maxVersions;
    }

    public void setMaxVersions(int maxVersions) {
        this.maxVersions = maxVersions;
    }

    public int getMinVersions() {
        return minVersions;
    }

    public void setMinVersions(int minVersions) {
        this.minVersions = minVersions;
    }

    public String getCompressionType() {
        return compressionType;
    }

    public void setCompressionType(String compressionType) {
        this.compressionType = compressionType;
    }

    public String getStoragePolicy() {
        return storagePolicy;
    }

    public void setStoragePolicy(String storagePolicy) {
        this.storagePolicy = storagePolicy;
    }

    public String getBloomFilterType() {
        return bloomFilterType;
    }

    public void setBloomFilterType(String bloomFilterType) {
        this.bloomFilterType = bloomFilterType;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public boolean isBlockCacheEnabled() {
        return blockCacheEnabled;
    }

    public void setBlockCacheEnabled(boolean blockCacheEnabled) {
        this.blockCacheEnabled = blockCacheEnabled;
    }

    public boolean isInMemory() {
        return inMemory;
    }

    public void setInMemory(boolean inMemory) {
        this.inMemory = inMemory;
    }

    public String getKeepDeletedCells() {
        return keepDeletedCells;
    }

    public void setKeepDeletedCells(String keepDeletedCells) {
        this.keepDeletedCells = keepDeletedCells;
    }

    public String getDataBlockEncoding() {
        return dataBlockEncoding;
    }

    public void setDataBlockEncoding(String dataBlockEncoding) {
        this.dataBlockEncoding = dataBlockEncoding;
    }

    public boolean isCacheDataOnWrite() {
        return cacheDataOnWrite;
    }

    public void setCacheDataOnWrite(boolean cacheDataOnWrite) {
        this.cacheDataOnWrite = cacheDataOnWrite;
    }

    public boolean isCacheDataInL1() {
        return cacheDataInL1;
    }

    public void setCacheDataInL1(boolean cacheDataInL1) {
        this.cacheDataInL1 = cacheDataInL1;
    }

    public boolean isCacheIndexesOnWrite() {
        return cacheIndexesOnWrite;
    }

    public void setCacheIndexesOnWrite(boolean cacheIndexesOnWrite) {
        this.cacheIndexesOnWrite = cacheIndexesOnWrite;
    }

    public boolean isCacheBloomsOnWrite() {
        return cacheBloomsOnWrite;
    }

    public void setCacheBloomsOnWrite(boolean cacheBloomsOnWrite) {
        this.cacheBloomsOnWrite = cacheBloomsOnWrite;
    }

    public boolean isEvictBlocksOnClose() {
        return evictBlocksOnClose;
    }

    public void setEvictBlocksOnClose(boolean evictBlocksOnClose) {
        this.evictBlocksOnClose = evictBlocksOnClose;
    }

    public boolean isPrefetchBlocksOnOpen() {
        return prefetchBlocksOnOpen;
    }

    public void setPrefetchBlocksOnOpen(boolean prefetchBlocksOnOpen) {
        this.prefetchBlocksOnOpen = prefetchBlocksOnOpen;
    }

    public Map<String, String> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Map<String, String> configuration) {
        this.configuration = configuration;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    public boolean isMobEnabled() {
        return mobEnabled;
    }

    public void setMobEnabled(boolean mobEnabled) {
        this.mobEnabled = mobEnabled;
    }

    public long getMobThreshold() {
        return mobThreshold;
    }

    public void setMobThreshold(long mobThreshold) {
        this.mobThreshold = mobThreshold;
    }

    public static Builder of(String name) {
        return new Builder(name);
    }

    public String humanReadableTTL(long interval) {
        StringBuilder sb = new StringBuilder();
        if (interval == 2147483647L) {
            sb.append("FOREVER");
            return sb.toString();
        } else if (interval < 60L) {
            sb.append(interval);
            sb.append(" 秒");
            return sb.toString();
        } else {
            int days = (int) (interval / 86400L);
            int hours = (int) (interval - (86400L * days)) / 3600;
            int minutes = (int) (interval - (86400L * days) - (long) (3600 * hours)) / 60;
            int seconds = (int) (interval - (86400L * days) - (long) (3600 * hours) - (long) (60 * minutes));
            sb.append(interval);
            sb.append(" 秒 (");
            if (days > 0) {
                sb.append(days);
                sb.append(" 天");
            }

            if (hours > 0) {
                sb.append(days > 0 ? " " : "");
                sb.append(hours);
                sb.append(" 小时");
            }

            if (minutes > 0) {
                sb.append(days + hours > 0 ? " " : "");
                sb.append(minutes);
                sb.append(" 分钟");
            }

            if (seconds > 0) {
                sb.append(days + hours + minutes > 0 ? " " : "");
                sb.append(seconds);
                sb.append(" 秒");
            }

            sb.append(")");
            return sb.toString();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ColumnFamilyDesc)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ColumnFamilyDesc that = (ColumnFamilyDesc) obj;
        return Objects.equals(this.name, that.name);
    }

    @Override
    public int compareTo(ColumnFamilyDesc o) {
        return name.compareTo(o.name);
    }
}
