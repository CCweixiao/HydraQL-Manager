package com.leo.hbase.manager.web.controller.system;

import com.github.CCweixiao.model.TableDesc;
import com.leo.hbase.manager.common.annotation.Log;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.enums.BusinessType;
import com.leo.hbase.manager.system.dto.TableSchemaDto;
import com.leo.hbase.manager.web.service.IMultiHBaseAdminService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author leojie 2020/11/29 11:13 上午
 */
@Controller
@RequestMapping("/system/schema")
public class SysHbaseTableSchemaController extends SysHbaseBaseController {
    private String prefix = "system/schema";

    @Autowired
    private IMultiHBaseAdminService multiHBaseAdminService;


    @RequiresPermissions("system:schema:view")
    @GetMapping()
    public String schema() {
        return prefix + "/schema";
    }

    /**
     * 新增保存HBaseTableSchema
     */
    @RequiresPermissions("system:schema:add")
    @Log(title = "HBaseTableSchema", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated TableSchemaDto tableSchemaDto) {
        TableDesc tableDesc = tableSchemaDto.convertTo();
        multiHBaseAdminService.modifyTable(clusterCodeOfCurrentSession(), tableDesc);
        return AjaxResult.success("HBaseTableSchema添加成功");
    }

}
