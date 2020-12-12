package com.leo.hbase.manager.system.dto;

import com.leo.hbase.manager.system.valid.First;
import com.leo.hbase.manager.system.valid.Second;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author leojie 2020/12/12 2:58 下午
 */
@GroupSequence(value = {First.class, Second.class, QueryHBaseSqlDto.class})
public class QueryHBaseSqlDto {
    @NotBlank(message = "HQL不能为空", groups = {First.class})
    @Size(min = 1, max = 200000, message = "HQL必须在200000个字符之间", groups = {Second.class})
    private String hql;

    public String getHql() {
        return hql;
    }

    public void setHql(String hql) {
        this.hql = hql;
    }
}
