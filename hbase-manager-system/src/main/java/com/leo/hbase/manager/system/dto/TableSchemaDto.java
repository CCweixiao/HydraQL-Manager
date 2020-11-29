package com.leo.hbase.manager.system.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.model.TableDesc;
import com.google.common.base.Converter;
import com.leo.hbase.manager.common.utils.JSONUtil;
import com.leo.hbase.manager.system.valid.First;
import com.leo.hbase.manager.system.valid.Second;
import com.leo.hbase.manager.system.valid.Third;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leojie 2020/11/29 2:39 下午
 */
@GroupSequence(value = {First.class, Second.class, TableSchemaDto.class})
public class TableSchemaDto {
    @NotBlank(message = "HBaseTableSchema不能为空", groups = {Third.class})
    @Size(min = 1, max = 200000, message = "HBaseTableSchema必须在200000个字符之间", groups = {Second.class})
    private String tableSchema;

    public TableDesc convertTo() {
        TableSchemaDtoConvert convert = new TableSchemaDtoConvert();
        return convert.convert(this);
    }

    public TableSchemaDto convertFor(TableDesc tableDesc) {
        TableSchemaDtoConvert convert = new TableSchemaDtoConvert();
        return convert.reverse().convert(tableDesc);
    }

    public static class TableSchemaDtoConvert extends Converter<TableSchemaDto, TableDesc> {

        @Override
        protected TableDesc doForward(TableSchemaDto tableSchemaDto) {
            String schemaJsonStr = tableSchemaDto.getTableSchema();
            final JSONObject schemaJson = JSON.parseObject(schemaJsonStr);
            if (schemaJson == null) {
                throw new HBaseOperationsException("HBaseTableSchemaJson解析失败");
            }
            String tableName = JSONUtil.getStrV(schemaJson, "tableName");

            TableDesc tableDesc = new TableDesc();
            tableDesc.setTableName(tableName);
            Map<String, String> columnSchemaPro = new HashMap<>(1);
            columnSchemaPro.put("tableSchema", schemaJsonStr);
            tableDesc.setTableProps(columnSchemaPro);

            return tableDesc;
        }

        @Override
        protected TableSchemaDto doBackward(TableDesc tableDesc) {
            final String tableSchema = tableDesc.getTableProps().getOrDefault("tableSchema", "");
            TableSchemaDto tableSchemaDto = new TableSchemaDto();
            tableSchemaDto.setTableSchema(tableSchema);
            return tableSchemaDto;
        }
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }
}
