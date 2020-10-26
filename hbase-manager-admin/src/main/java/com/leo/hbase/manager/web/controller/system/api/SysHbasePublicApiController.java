package com.leo.hbase.manager.web.controller.system.api;

import com.github.CCweixiao.model.TableDesc;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.enums.HBaseDisabledFlag;
import com.leo.hbase.manager.common.enums.HBaseTableStatus;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.system.dto.TableDescDto;
import com.leo.hbase.manager.system.valid.*;
import com.leo.hbase.manager.web.service.IMultiHBaseAdminService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 公共API
 *
 * @author leojie 2020/10/26 11:01 下午
 */
@Api("HBase表信息管理公共API")
@RestController
@RequestMapping("/public/api/hbase")
public class SysHbasePublicApiController {
    @Autowired
    private IMultiHBaseAdminService multiHBaseAdminService;

    @ApiOperation("获取所有的线上启用表")
    @ApiImplicitParam(name = "queryOnlineHBaseTableForm", value = "查询线上HBase表的参数实体", dataType = "QueryOnlineHBaseTableForm")
    @PostMapping("/onlineTables")
    public AjaxResult list(@Validated QueryOnlineHBaseTableForm queryOnlineHBaseTableForm) {

        final List<TableDesc> tableDescList = multiHBaseAdminService.listAllTableDesc(queryOnlineHBaseTableForm.getCluster());

        final List<TableDescDto> tableDescDtoList = tableDescList.stream().map(tableDesc -> new TableDescDto().convertFor(tableDesc)).filter(tableDescDto -> {
            if (StringUtils.isNotBlank(queryOnlineHBaseTableForm.getNamespaceName())) {
                return tableDescDto.getNamespaceName().equals(queryOnlineHBaseTableForm.getNamespaceName());
            }
            return true;
        }).filter(tableDescDto -> {
            if (StringUtils.isNotBlank(queryOnlineHBaseTableForm.getTableName())) {
                return tableDescDto.getTableName().toLowerCase().contains(queryOnlineHBaseTableForm.getTableName().toLowerCase());
            }
            return true;
        }).filter(tableDescDto -> {
            if (StringUtils.isNotBlank(queryOnlineHBaseTableForm.getDisableFlag())) {
                return tableDescDto.getDisableFlag().equals(queryOnlineHBaseTableForm.getDisableFlag());
            } else {
                return tableDescDto.getDisableFlag().equals(HBaseDisabledFlag.ENABLED.getCode());
            }
        }).filter(tableDescDto -> {
            if (StringUtils.isNotBlank(queryOnlineHBaseTableForm.getStatus())) {
                return tableDescDto.getStatus().equals(queryOnlineHBaseTableForm.getStatus());
            } else {
                return tableDescDto.getStatus().equals(HBaseTableStatus.ONLINE.getCode());
            }
        }).sorted(Comparator.comparing(TableDescDto::getLastUpdateTimestamp).reversed()).collect(Collectors.toList());
        return AjaxResult.success(tableDescDtoList);
    }

}

@GroupSequence(value = {First.class,Second.class, QueryOnlineHBaseTableForm.class})
@ApiModel("查询线上表参数实体")
class QueryOnlineHBaseTableForm {
    /**
     * 查询集群ID，对应着配置文件的集群链接地址
     */
    @ApiModelProperty("集群code")
    @NotBlank(message = "HBase集群code不能为空", groups = {First.class})
    @Size(min = 1, max = 128, message = "HBase集群code长度不能超过128个字符", groups = {Second.class})
    private String cluster;

    /**
     * 表的命名空间
     */
    @ApiModelProperty("表的命名空间")
    private String namespaceName;

    /**
     * HBase表名称
     */
    @ApiModelProperty("HBase表名称")
    private String tableName;

    /**
     * 表的禁用状态
     */
    @ApiModelProperty("表的禁用状态")
    private String disableFlag;

    /**
     * 状态（0线上表 1测试表 2启用表）
     */
    @ApiModelProperty("状态（0线上表 1测试表 2启用表）")
    private String status;

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getNamespaceName() {
        return namespaceName;
    }

    public void setNamespaceName(String namespaceName) {
        this.namespaceName = namespaceName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(String disableFlag) {
        this.disableFlag = disableFlag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
