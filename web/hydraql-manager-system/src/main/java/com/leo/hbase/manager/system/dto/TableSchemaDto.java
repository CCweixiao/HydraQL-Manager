package com.leo.hbase.manager.system.dto;

import com.google.common.base.Converter;
import com.leo.hbase.manager.common.utils.bean.BeanUtils;
import com.leo.hbase.manager.system.domain.SysHbaseTableSchema;
import com.leo.hbase.manager.system.valid.First;
import com.leo.hbase.manager.system.valid.Second;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author leojie 2020/11/29 2:39 下午
 */
@GroupSequence(value = {First.class, Second.class, TableSchemaDto.class})
public class TableSchemaDto {
    private Integer schemaId;
    private String clusterId;
    private String tableName;

    @NotBlank(message = "HBaseTableSchema不能为空", groups = {First.class})
    @Size(min = 1, max = 200000, message = "HBaseTableSchema必须在200000个字符之间", groups = {Second.class})
    private String tableSchema;

    public SysHbaseTableSchema convertTo() {
        TableSchemaDtoConvert convert = new TableSchemaDtoConvert();
        return convert.convert(this);
    }

    public TableSchemaDto convertFor(SysHbaseTableSchema tableSchema) {
        TableSchemaDtoConvert convert = new TableSchemaDtoConvert();
        return convert.reverse().convert(tableSchema);
    }

    public static class TableSchemaDtoConvert extends Converter<TableSchemaDto, SysHbaseTableSchema> {

        @Override
        protected SysHbaseTableSchema doForward(TableSchemaDto tableSchemaDto) {
            SysHbaseTableSchema sysHbaseTableSchema = new SysHbaseTableSchema();
            BeanUtils.copyBeanProp(sysHbaseTableSchema, tableSchemaDto);
            return sysHbaseTableSchema;
        }

        @Override
        protected TableSchemaDto doBackward(SysHbaseTableSchema tableSchema) {
            TableSchemaDto tableSchemaDto = new TableSchemaDto();
            BeanUtils.copyBeanProp(tableSchemaDto, tableSchema);
            return tableSchemaDto;
        }
    }

    public Integer getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(Integer schemaId) {
        this.schemaId = schemaId;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }
}
