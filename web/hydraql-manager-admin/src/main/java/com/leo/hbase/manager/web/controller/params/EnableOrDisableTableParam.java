package com.leo.hbase.manager.web.controller.params;

import com.leo.hbase.manager.system.valid.EnumValue;
import com.leo.hbase.manager.system.valid.First;
import com.leo.hbase.manager.system.valid.Second;
import com.leo.hbase.manager.system.valid.Third;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author leojie 2023/7/17 23:03
 */
@GroupSequence(value = {First.class, Second.class, Third.class, EnableOrDisableTableParam.class})
public class EnableOrDisableTableParam {
    @NotNull(message = "表ID不能为空", groups = {First.class})
    private Long tableId;

    @NotBlank(message = "禁用标识不能为空", groups = {Second.class})
    @EnumValue(strValues = {"0", "2"}, message = "禁用标识只能为0｜2", groups = {Third.class})
    private String disableFlag;

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(String disableFlag) {
        this.disableFlag = disableFlag;
    }
}
