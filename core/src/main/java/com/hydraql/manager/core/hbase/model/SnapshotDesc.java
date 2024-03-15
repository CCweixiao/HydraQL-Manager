package com.hydraql.manager.core.hbase.model;

/**
 * @author leojie 2024/2/23 22:42
 */
public class SnapshotDesc {
    private String snapshotName;
    private String tableName;
    private long createTime;

    public String getSnapshotName() {
        return snapshotName;
    }

    public void setSnapshotName(String snapshotName) {
        this.snapshotName = snapshotName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append('{');
        s.append("NAME");
        s.append(" => '");
        s.append(getSnapshotName());
        s.append(", ");
        s.append("TABLE_NAME");
        s.append(" => '");
        s.append(getTableName());
        s.append(", ");
        s.append("CREATE_TIME");
        s.append(" => '");
        s.append(getCreateTime());
        s.append("'");
        s.append("'");
        s.append('}');
        return s.toString();
    }
}
