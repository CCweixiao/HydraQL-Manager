package com.leo.hbase.manager.web.controller.system;

import com.hydraql.manager.core.util.HConstants;
import com.leo.hbase.manager.common.annotation.Log;
import com.leo.hbase.manager.common.core.controller.BaseController;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.core.page.TableDataInfo;
import com.leo.hbase.manager.common.enums.BusinessType;
import com.leo.hbase.manager.common.enums.HBaseClusterStatus;
import com.leo.hbase.manager.common.utils.ExceptionUtil;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.system.domain.SysHbaseCluster;
import com.leo.hbase.manager.system.domain.SysHbaseTable;
import com.leo.hbase.manager.system.dto.PropertyDto;
import com.leo.hbase.manager.system.dto.SysHbaseClusterDto;
import com.leo.hbase.manager.system.service.ISysHbaseClusterService;
import com.leo.hbase.manager.system.service.ISysHbaseTableService;
import com.leo.hbase.manager.web.service.IMultiHBaseAdminService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * HBaseClusterController
 *
 * @author leojie
 * @date 2020-08-16
 */
@Controller
@RequestMapping("/system/cluster")
public class SysHbaseClusterController extends BaseController {
    private final String prefix = "system/cluster";

    @Autowired
    private ISysHbaseClusterService sysHbaseClusterService;

    @Autowired
    private ISysHbaseTableService sysHbaseTableService;

    @Autowired
    private IMultiHBaseAdminService multiHBaseAdminService;

    @RequiresPermissions("hbase:cluster:view")
    @GetMapping()
    public String detail() {
        return prefix + "/cluster";
    }


    @RequiresPermissions("hbase:cluster:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list() {
        startPage();
        List<SysHbaseCluster> list = sysHbaseClusterService.selectAllSysHbaseClusterList();
        return getDataTable(list);
    }

    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    @RequiresPermissions("hbase:cluster:add")
    @Log(title = "HBase集群信息新增", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysHbaseClusterDto sysHbaseClusterDto) {
        final String clusterId = sysHbaseClusterDto.getClusterId();
        SysHbaseCluster existsCluster = sysHbaseClusterService.selectSysHbaseClusterById(clusterId);

        if (existsCluster != null && StringUtils.isNotBlank(existsCluster.getClusterId())) {
            return error("集群[" + clusterId + "]已经存在!");
        }
        return toAjax(sysHbaseClusterService.insertSysHbaseCluster(sysHbaseClusterDto.convertTo()));
    }

    @RequiresPermissions("hbase:cluster:testConnection")
    @PostMapping("/testConnection")
    @ResponseBody
    public AjaxResult testConnection(@Validated SysHbaseClusterDto sysHbaseClusterDto) {
        try {
            List<PropertyDto> properties = sysHbaseClusterDto.getProperties();
            if (properties == null) {
                properties = new ArrayList<>();
            } else {
                properties = properties.stream().filter(p -> !p.getPropertyName().equals(HConstants.HBASE_CLIENT_RETRIES_NUMBER))
                        .collect(Collectors.toList());
            }
            properties.add(new PropertyDto(HConstants.HBASE_CLIENT_RETRIES_NUMBER, "3"));
            sysHbaseClusterDto.setProperties(properties);
            boolean connectionCluster = multiHBaseAdminService.testConnectionCluster(sysHbaseClusterDto);
            if (connectionCluster) {
                return AjaxResult.success("连接成功");
            } else {
                return AjaxResult.error("连接失败");
            }
        } catch (Exception e) {
            return AjaxResult.error("连接失败：" + ExceptionUtil.getExceptionMessage(e));
        }
    }

    @GetMapping("/edit/{clusterId}")
    public String edit(@PathVariable("clusterId") String clusterId, ModelMap mmap) {
        SysHbaseCluster sysHbaseCluster = sysHbaseClusterService
                .selectSysHbaseClusterById(clusterId);
        SysHbaseClusterDto sysHbaseClusterDto = new SysHbaseClusterDto().convertFor(sysHbaseCluster);
        mmap.put("sysHbaseClusterDto", sysHbaseClusterDto);
        return prefix + "/edit";
    }

    /**
     * 修改保存HBaseTag
     */
    @RequiresPermissions("hbase:cluster:edit")
    @Log(title = "HBase集群信息更新", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysHbaseClusterDto sysHbaseClusterDto) {
        Integer id = sysHbaseClusterDto.getId();
        if (id == null) {
            return error("待更新集群ID不能为空");
        }
        String clusterId = sysHbaseClusterDto.getClusterId();
        SysHbaseCluster sysHbaseCluster = sysHbaseClusterService.selectSysHbaseClusterById(clusterId);
        if (sysHbaseCluster != null && sysHbaseCluster.getId().intValue() != id.intValue()) {
            return error("待更新集群的clusterId已被占用");
        }
        SysHbaseCluster cluster = sysHbaseClusterDto.convertTo();
        int record = sysHbaseClusterService.updateSysHbaseCluster(cluster);
        return toAjax(record);
    }

    /**
     * 删除HBaseTag
     */
    @RequiresPermissions("hbase:cluster:remove")
    @Log(title = "HBase集群信息删除", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(Integer ids) {
        SysHbaseCluster sysHbaseCluster = sysHbaseClusterService.selectSysHbaseClusterById(ids);
        if (sysHbaseCluster == null) {
            return AjaxResult.error("待删除集群不存在！");
        }
        String clusterId = sysHbaseCluster.getClusterId();
        List<SysHbaseTable> sysHbaseTables = sysHbaseTableService.selectSysHbaseTableListByClusterId(clusterId);
        if (sysHbaseTables != null && !sysHbaseTables.isEmpty()) {
            return AjaxResult.error("待删除集群绑定有表，不允许直接删除！");
        }
        return toAjax(sysHbaseClusterService.deleteSysHbaseClusterById(ids));
    }

    @RequiresPermissions("hbase:cluster:offline")
    @Log(title = "HBase集群下线", businessType = BusinessType.UPDATE)
    @PostMapping("/offlineCluster")
    @ResponseBody
    public AjaxResult offlineCluster(SysHbaseCluster cluster) {
        SysHbaseCluster sysHbaseCluster = sysHbaseClusterService.selectSysHbaseClusterById(cluster.getId());
        if (sysHbaseCluster == null) {
            return AjaxResult.error("待下线集群不存在！");
        }
        cluster.setState(HBaseClusterStatus.OFFLINE.getState());
        return toAjax(sysHbaseClusterService.updateSysHbaseCluster(cluster));
    }

    @RequiresPermissions("hbase:cluster:online")
    @Log(title = "HBase集群上线", businessType = BusinessType.UPDATE)
    @PostMapping("/onlineCluster")
    @ResponseBody
    public AjaxResult onlineCluster(SysHbaseCluster cluster) {
        SysHbaseCluster sysHbaseCluster = sysHbaseClusterService.selectSysHbaseClusterById(cluster.getId());
        if (sysHbaseCluster == null) {
            return AjaxResult.error("待上线集群不存在！");
        }
        cluster.setState(HBaseClusterStatus.ONLINE.getState());
        return toAjax(sysHbaseClusterService.updateSysHbaseCluster(cluster));
    }

    @RequiresPermissions("hbase:cluster:copyClusterInfo")
    @Log(title = "复制集群信息", businessType = BusinessType.UPDATE)
    @PostMapping("/copyClusterInfo")
    @ResponseBody
    public AjaxResult copyClusterInfo(SysHbaseCluster cluster) {
        SysHbaseCluster sysHbaseCluster = sysHbaseClusterService.selectSysHbaseClusterById(cluster.getId());
        if (sysHbaseCluster == null) {
            return AjaxResult.error("待复制集群信息不存在");
        }
        String clusterId = sysHbaseCluster.getClusterId();
        clusterId = clusterId + "_" + UUID.randomUUID().toString().substring(0,8) + "_copy";
        sysHbaseCluster.setClusterId(clusterId);
        return toAjax(sysHbaseClusterService.insertSysHbaseCluster(sysHbaseCluster));
    }
}
