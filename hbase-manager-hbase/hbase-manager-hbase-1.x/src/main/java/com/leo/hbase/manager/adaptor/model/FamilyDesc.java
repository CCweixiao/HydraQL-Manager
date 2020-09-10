package com.leo.hbase.manager.adaptor.model;

/**
 * @author leojie 2020/9/9 10:25 下午
 */
public class FamilyDesc {
    private final String familyName;
    private final Integer maxVersions;
    private final Integer timeToLive;
    private final String compressionType;
    private final Integer replicationScope;

    public FamilyDesc(Builder builder) {
        this.familyName = builder.familyName;
        this.maxVersions = builder.maxVersions;
        this.timeToLive = builder.timeToLive;
        this.compressionType = builder.compressionType;
        this.replicationScope = builder.replicationScope;
    }

    public static class Builder {
        private String familyName;
        private Integer maxVersions;
        private Integer timeToLive;
        private String compressionType;
        private Integer replicationScope;

        public Builder familyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        public Builder maxVersions(Integer maxVersions) {
            this.maxVersions = maxVersions;
            return this;
        }

        public Builder timeToLive(Integer timeToLive) {
            this.timeToLive = timeToLive;
            return this;
        }

        public Builder compressionType(String compressionType) {
            this.compressionType = compressionType;
            return this;
        }

        public Builder replicationScope(Integer replicationScope) {
            this.replicationScope = replicationScope;
            return this;
        }

        public FamilyDesc build() {
            return new FamilyDesc(this);
        }
    }
}
