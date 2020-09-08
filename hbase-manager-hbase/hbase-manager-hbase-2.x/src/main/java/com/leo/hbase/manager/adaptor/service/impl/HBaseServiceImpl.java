package com.leo.hbase.manager.adaptor.service.impl;

import com.github.CCweixiao.HBaseTemplate;
import com.github.CCweixiao.util.HBytesUtil;
import com.github.CCweixiao.util.StrUtil;
import com.leo.hbase.manager.adaptor.service.IHBaseService;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leojie 2020/9/7 8:29 下午
 */
@Service
public class HBaseServiceImpl implements IHBaseService {
    @Autowired
    private HBaseTemplate hBaseTemplate;

    @Override
    public List<Map<String, Object>> get(String tableName, String rowKey) {
        return hBaseTemplate.get(tableName, rowKey, (result, rowNum) -> {
            List<Cell> cs = result.listCells();
            List<Map<String, Object>> dataMaps = new ArrayList<>(cs.size());

            for (Cell cell : cs) {
                Map<String, Object> resultMap = resultToMap(result, cell);
                dataMaps.add(resultMap);
            }
            return dataMaps;
        });
    }

    @Override
    public List<List<Map<String, Object>>> find(String tableName, String familyName, String startKey, Integer limit) {
        Scan scan = new Scan();
        if (StrUtil.isNotBlank(familyName)) {
            scan.addFamily(Bytes.toBytes(familyName));
        }
        if (StrUtil.isNotBlank(startKey)) {
            scan.withStartRow(Bytes.toBytes(startKey));
        }

        return hBaseTemplate.find(tableName, scan, limit, (result, rowNum) -> {
            List<Cell> cs = result.listCells();
            List<Map<String, Object>> dataList = new ArrayList<>(cs.size());
            for (Cell cell : cs) {
                Map<String, Object> resultMap = resultToMap(result, cell);
                dataList.add(resultMap);
            }
            return dataList;
        });
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName, String qualifier) {
        hBaseTemplate.delete(tableName, rowKey, familyName, qualifier);
    }

    private Map<String, Object> resultToMap(Result result, Cell cell) {
        Map<String, Object> resultMap = new HashMap<>(4);
        String fieldName = Bytes.toString(CellUtil.cloneFamily(cell)) + ":" + Bytes.toString(CellUtil.cloneQualifier(cell));
        byte[] value = CellUtil.cloneValue(cell);
        resultMap.put("rowKey", Bytes.toString(result.getRow()));
        resultMap.put("familyName", fieldName);
        resultMap.put("timestamp", cell.getTimestamp());
        resultMap.put("value", HBytesUtil.toObject(value, Object.class));
        return resultMap;
    }
}
