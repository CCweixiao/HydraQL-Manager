package com.leo.hbase.manager.web.controller.system;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author leojie 2020/11/29 11:13 上午
 */
@Controller
@RequestMapping("/system/sql")
public class SysHbaseTableSqlController extends SysHbaseBaseController{
    private String prefix = "system/sql";

    @RequiresPermissions("system:sql:view")
    @GetMapping()
    public String sql()
    {
        return prefix + "/sql";
    }



}
