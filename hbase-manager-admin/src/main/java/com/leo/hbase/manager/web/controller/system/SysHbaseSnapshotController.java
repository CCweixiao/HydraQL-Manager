package com.leo.hbase.manager.web.controller.system;

import com.github.CCweixiao.model.SnapshotDesc;
import com.github.CCweixiao.util.StrUtil;
import com.leo.hbase.manager.common.annotation.Log;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.core.page.TableDataInfo;
import com.leo.hbase.manager.common.enums.BusinessType;
import com.leo.hbase.manager.common.utils.security.StrEnDeUtils;
import com.leo.hbase.manager.system.dto.SnapshotDescDto;
import com.leo.hbase.manager.web.service.IMultiHBaseAdminService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 快照管理Controller
 *
 * @author leojie 2020/11/14 7:17 下午
 */
@Controller
@RequestMapping("/system/snapshot")
public class SysHbaseSnapshotController extends SysHbaseBaseController {
    private String prefix = "system/snapshot";

    @Autowired
    private IMultiHBaseAdminService multiHBaseAdminService;

    @RequiresPermissions("system:snapshot:view")
    @GetMapping()
    public String snapshot(ModelMap mmap) {
        final List<String> allTableNames = multiHBaseAdminService.listAllTableName(clusterCodeOfCurrentSession());
        mmap.put("allTableNames", allTableNames);
        return prefix + "/snapshot";
    }

    /**
     * 查询HBaseNamespace列表
     */
    @RequiresPermissions("system:snapshot:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SnapshotDescDto snapshotDescDto) {
        final List<SnapshotDesc> snapshotDescList = multiHBaseAdminService.listAllSnapshotDesc(clusterCodeOfCurrentSession());

        List<SnapshotDescDto> snapshotDescDtoList = new ArrayList<>();
        if (snapshotDescList != null && !snapshotDescList.isEmpty()) {
            snapshotDescDtoList = snapshotDescList.stream().map(snapshotDesc -> new SnapshotDescDto().convertFor(snapshotDesc))
                    .filter(snapshotDescDto1 -> {
                        if (StrUtil.isNotBlank(snapshotDescDto.getTableName())) {
                            return snapshotDescDto.getTableName().equals(snapshotDescDto1.getTableName());
                        }
                        return true;
                    }).sorted(Comparator.comparing(SnapshotDescDto::getCreateTime).reversed())
                    .collect(Collectors.toList());
        }
        return getDataTable(snapshotDescDtoList);
    }

    /**
     * 新增HBase表快照
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        final List<String> allTableNames = multiHBaseAdminService.listAllTableName(clusterCodeOfCurrentSession());
        mmap.put("allTableNames", allTableNames);
        return prefix + "/add";
    }

    /**
     * 新增保存HBaseNamespace
     */
    @RequiresPermissions("system:snapshot:add")
    @Log(title = "HBase表快照", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SnapshotDescDto snapshotDescDto) {
        String clusterCode = clusterCodeOfCurrentSession();

        SnapshotDesc snapshotDesc = snapshotDescDto.convertTo();
        final boolean createdOrNot = multiHBaseAdminService.createSnapshot(clusterCode, snapshotDesc);

        if (!createdOrNot) {
            return error("快照[" + snapshotDescDto.getSnapshotName() + "]创建失败");
        }

        return success("快照[" + snapshotDescDto.getSnapshotName() + "]创建成功");
    }

    /**
     * 删除HBase表快照
     */
    @RequiresPermissions("system:snapshot:remove")
    @Log(title = "HBase表快照", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        if (StrUtil.isBlank(ids)) {
            return error("请选择需要删除的快照");
        }
        String clusterCode = clusterCodeOfCurrentSession();
        String[] snapshotIdList = ids.split(",");
        boolean deletedOrNot = false;
        for (String snapshotId : snapshotIdList) {
            String snapshotName = StrEnDeUtils.decrypt(snapshotId);
            deletedOrNot = multiHBaseAdminService.removeSnapshot(clusterCode, snapshotName);
        }

        if (!deletedOrNot) {
            return error("快照删除失败");
        }
        return success("快照删除成功");
    }

}
