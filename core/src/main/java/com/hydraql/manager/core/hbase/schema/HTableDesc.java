package com.hydraql.manager.core.hbase.schema;

import com.hydraql.manager.core.util.StringUtil;

import java.util.*;

/**
 * @author leojie 2024/2/23 16:52
 */
public class HTableDesc implements Comparable<HTableDesc> {
    private String name;
    private long maxFileSize;
    private boolean readOnly;
    private long memStoreFlushSize;
    private boolean compactionEnabled;

    private String regionSplitPolicyClassName;
    private Map<String, String> configuration;
    private Map<String, String> values;
    private List<ColumnFamilyDesc> columnFamilyDescList;

    public HTableDesc() {
    }

    public HTableDesc(Builder builder) {
        this.name = builder.name;
        this.maxFileSize = builder.maxFileSize;
        this.readOnly = builder.readOnly;
        this.memStoreFlushSize = builder.memStoreFlushSize;
        this.compactionEnabled = builder.compactionEnabled;
        this.configuration = builder.configuration;
        this.values = builder.values;
        this.columnFamilyDescList = builder.columnFamilyDescList;
        this.regionSplitPolicyClassName = builder.regionSplitPolicyClassName;
    }

    public static class Builder {
        private final String name;
        private long maxFileSize;
        private boolean readOnly;
        private long memStoreFlushSize;
        private boolean compactionEnabled;
        private String regionSplitPolicyClassName;
        private Map<String, String> configuration;
        private Map<String, String> values;
        private List<ColumnFamilyDesc> columnFamilyDescList;

        public Builder(String name) {
            this.name = name;
            this.maxFileSize = 10737418240L;
            this.readOnly = false;
            this.memStoreFlushSize = 134217728L;
            this.compactionEnabled = true;
            this.regionSplitPolicyClassName = null;
            this.columnFamilyDescList = new ArrayList<>();
            this.configuration = new HashMap<>();
            this.values = new HashMap<>();
        }

        public Builder(String namespace, String name) {
            this(namespace.concat(":").concat(name));
        }

        public Builder maxFileSize(long maxFileSize) {
            this.maxFileSize = maxFileSize;
            return this;
        }

        public Builder readOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public Builder memStoreFlushSize(long memStoreFlushSize) {
            this.memStoreFlushSize = memStoreFlushSize;
            return this;
        }

        public Builder compactionEnabled(boolean compactionEnabled) {
            this.compactionEnabled = compactionEnabled;
            return this;
        }

        public Builder regionSplitPolicyClassName(String regionSplitPolicyClassName) {
            this.regionSplitPolicyClassName = regionSplitPolicyClassName;
            return this;
        }

        public Builder setConfiguration(Map<String, String> configuration) {
            this.configuration.putAll(configuration);
            return this;
        }

        public Builder setConfiguration(String key, String value) {
            if (StringUtil.isBlank(key)) {
                return this;
            }
            this.configuration.put(key, value);
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

        public Builder columnFamilyDescList(List<ColumnFamilyDesc> columnFamilyDescList) {
            this.columnFamilyDescList = columnFamilyDescList;
            return this;
        }

        public Builder addFamilyDesc(ColumnFamilyDesc columnFamilyDesc) {
            this.columnFamilyDescList.add(columnFamilyDesc);
            return this;
        }

        public Builder addFamilyDesc(List<ColumnFamilyDesc> columnFamilyDescList) {
            this.columnFamilyDescList.addAll(columnFamilyDescList);
            return this;
        }

        public HTableDesc build() {
            if (this.columnFamilyDescList.isEmpty()) {
                throw new RuntimeException("The HBase table should have at least one family.");
            }
            return new HTableDesc(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public long getMemStoreFlushSize() {
        return memStoreFlushSize;
    }

    public void setMemStoreFlushSize(long memStoreFlushSize) {
        this.memStoreFlushSize = memStoreFlushSize;
    }

    public boolean isCompactionEnabled() {
        return compactionEnabled;
    }

    public void setCompactionEnabled(boolean compactionEnabled) {
        this.compactionEnabled = compactionEnabled;
    }

    public String getRegionSplitPolicyClassName() {
        return regionSplitPolicyClassName;
    }

    public void setRegionSplitPolicyClassName(String regionSplitPolicyClassName) {
        this.regionSplitPolicyClassName = regionSplitPolicyClassName;
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

    public List<ColumnFamilyDesc> getColumnFamilyDescList() {
        return columnFamilyDescList;
    }

    public void setColumnFamilyDescList(List<ColumnFamilyDesc> columnFamilyDescList) {
        this.columnFamilyDescList = columnFamilyDescList;
    }

    public static Builder of(String tableName) {
        return new Builder(tableName);
    }

    public static Builder of(String namespace, String name) {
        return new Builder(namespace, name);
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
        if (!(obj instanceof HTableDesc)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        HTableDesc that = (HTableDesc) obj;
        return Objects.equals(this.name, that.name);
    }

    @Override
    public int compareTo(HTableDesc o) {
        return name.compareTo(o.name);
    }

}
