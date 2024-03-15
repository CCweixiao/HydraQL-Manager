package com.hydraql.manager.core.hbase.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author leojie 2024/1/31 16:49
 */
public class HBaseRowData {
    private final String row;
    private final Map<String, String> data;

    public HBaseRowData(String row) {
        this.row = row;
        this.data = new HashMap<>();
    }

    public HBaseRowData setData(String colName, String data) {
        this.data.put(colName, data);
        return this;
    }

    public String getRow() {
        return row;
    }

    public Map<String, String> getData() {
        return data;
    }
}
