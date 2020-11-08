package com.leo.hbase.manager.web.controller.system;

import com.github.CCweixiao.constant.HMHBaseConstant;
import com.github.CCweixiao.model.NamespaceDesc;
import com.github.CCweixiao.model.TableDesc;
import com.github.CCweixiao.util.SplitGoEnum;
import com.github.CCweixiao.util.StrUtil;
import com.leo.hbase.manager.common.annotation.Log;
import com.leo.hbase.manager.common.constant.HBaseManagerConstants;
import com.leo.hbase.manager.common.core.domain.AjaxResult;
import com.leo.hbase.manager.common.core.page.TableDataInfo;
import com.leo.hbase.manager.common.core.text.Convert;
import com.leo.hbase.manager.common.enums.BusinessType;
import com.leo.hbase.manager.common.utils.poi.ExcelUtil;
import com.leo.hbase.manager.common.utils.security.StrEnDeUtils;
import com.leo.hbase.manager.framework.util.ShiroUtils;
import com.leo.hbase.manager.system.domain.SysHbaseTag;
import com.leo.hbase.manager.system.dto.NamespaceDescDto;
import com.leo.hbase.manager.system.dto.TableDescDto;
import com.leo.hbase.manager.web.controller.query.QueryHBaseTableForm;
import com.leo.hbase.manager.web.service.IMultiHBaseAdminService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * HBaseController
 *
 * @author leojie
 * @date 2020-08-16
 */
@Controller
@RequestMapping("/system/table")
public class SysHbaseTableController extends SysHbaseBaseController {
    private String prefix = "system/table";

    @Autowired
    private IMultiHBaseAdminService multiHBaseAdminService;

    @RequiresPermissions("system:table:view")
    @GetMapping()
    public String table(ModelMap mmap) {
        final List<NamespaceDescDto> namespaceDescList = getAllNamespaces();
        mmap.put("namespaces", namespaceDescList);
        mmap.put("tags", sysHbaseTagService.selectAllSysHbaseTagList());
        return prefix + "/table";
    }

    @RequiresPermissions("system:table:detail")
    @GetMapping("/detail/{tableId}")
    public String detail(@PathVariable("tableId") String tableId, ModelMap mmap) {
        final String tableName = parseTableNameFromTableId(tableId);
        final TableDesc tableDesc = multiHBaseAdminService.getTableDesc(clusterCodeOfCurrentSession(), tableName);
        TableDescDto tableDescDto = new TableDescDto().convertFor(tableDesc);
        tableDescDto.setSysHbaseTagList(getSysHbaseTagByLongIds(tableDescDto.getTagIds()));

        mmap.put("hbaseTable", tableDescDto);
        return prefix + "/detail";
    }

    @RequiresPermissions("system:table:detail")
    @GetMapping("/family/detail/{tableId}")
    public String familyDetail(@PathVariable("tableId") String tableId, ModelMap mmap) {
        final String tableName = StrEnDeUtils.decrypt(tableId);

        String clusterCode = clusterCodeOfCurrentSession();
        TableDesc tableDesc = multiHBaseAdminService.getTableDesc(clusterCode, tableName);
        TableDescDto tableDescDto = new TableDescDto().convertFor(tableDesc);
        mmap.put("tableObj", tableDescDto);

        final List<String> listAllTableName = multiHBaseAdminService.listAllTableName(clusterCode);

        if (listAllTableName == null || listAllTableName.isEmpty()) {
            mmap.put("tableMapList", new ArrayList<>());
        } else {
            List<Map<String, Object>> tableMapList = new ArrayList<>(listAllTableName.size());
            for (String tableName_ : listAllTableName) {
                Map<String, Object> tableMap = new HashMap<>(2);
                tableMap.put("tableId", StrEnDeUtils.encrypt(tableName_));
                tableMap.put("tableName", tableName_);
                tableMapList.add(tableMap);
                mmap.put("tableMapList", tableMapList);
            }
        }
        return "system/family/family";
    }

    /**
     * 查询HBase列表
     */
    @RequiresPermissions("system:table:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(QueryHBaseTableForm queryHBaseTableForm) {
        final List<TableDesc> tableDescList = multiHBaseAdminService.listAllTableDesc(clusterCodeOfCurrentSession());
        final List<TableDescDto> tableDescDtoList = filterFamilyDescList(queryHBaseTableForm, tableDescList);
        return getDataTable(tableDescDtoList);
    }

    /**
     * 导出HBase列表
     */
    @RequiresPermissions("system:table:export")
    @Log(title = "HBase", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(QueryHBaseTableForm queryHBaseTableForm) {
        final List<TableDesc> tableDescList = multiHBaseAdminService.listAllTableDesc(clusterCodeOfCurrentSession());
        final List<TableDescDto> tableDescDtoList = filterFamilyDescList(queryHBaseTableForm, tableDescList);
        ExcelUtil<TableDescDto> util = new ExcelUtil<>(TableDescDto.class);
        return util.exportExcel(tableDescDtoList, "table");
    }

    /**
     * 新增HBase表
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        List<NamespaceDescDto> namespaces = multiHBaseAdminService.listAllNamespaceDesc(clusterCodeOfCurrentSession()).stream()
                .filter(namespaceDesc -> !HMHBaseConstant.DEFAULT_SYS_TABLE_NAMESPACE.equals(namespaceDesc.getNamespaceName()))
                .map(namespaceDesc -> new NamespaceDescDto().convertFor(namespaceDesc)).collect(Collectors.toList());
        mmap.put("namespaces", namespaces);
        mmap.put("tags", sysHbaseTagService.selectAllSysHbaseTagList());
        return prefix + "/add";
    }

    /**
     * 新增保存HBase
     */
    @RequiresPermissions("system:table:add")
    @Log(title = "HBase", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated TableDescDto tableDescDto) {
        String clusterCode = clusterCodeOfCurrentSession();
        final String tableName = tableDescDto.getTableName();
        final String fullTableName = HMHBaseConstant.getFullTableName(tableName);

        if (multiHBaseAdminService.tableIsExists(clusterCode, fullTableName)) {
            return error("HBase表[" + fullTableName + "]已经存在！");
        }

        tableDescDto.setCreateBy(ShiroUtils.getLoginName());
        tableDescDto.setCreateTimestamp(System.currentTimeMillis());
        tableDescDto.setLastUpdateBy(ShiroUtils.getLoginName());
        tableDescDto.setLastUpdateTimestamp(System.currentTimeMillis());

        TableDesc tableDesc = tableDescDto.convertTo();
        boolean createTableRes = false;
        if (StrUtil.isBlank(tableDescDto.getSplitWay())) {
            createTableRes = multiHBaseAdminService.createTable(clusterCode, tableDesc);
        } else if (HBaseManagerConstants.SPLIT_1.equals(tableDescDto.getSplitWay())) {
            if (StrUtil.isBlank(tableDescDto.getStartKey())) {
                return error("预分区开始的key不能为空");
            }
            if (StrUtil.isBlank(tableDescDto.getEndKey())) {
                return error("预分区结束的key不能为空");
            }
            if (tableDescDto.getPreSplitRegions() < 1) {
                return error("预分区数不能为0");
            }
            createTableRes = multiHBaseAdminService.createTable(clusterCode, tableDesc, tableDescDto.getStartKey(),
                    tableDescDto.getEndKey(), tableDescDto.getPreSplitRegions(), false);

        } else if (HBaseManagerConstants.SPLIT_2.equals(tableDescDto.getSplitWay())) {
            String[] splitKeys = Convert.toStrArray(tableDescDto.getPreSplitKeys());
            if (splitKeys.length < 1) {
                return error("预分区的key不能为空");
            }
            createTableRes = multiHBaseAdminService.createTable(clusterCode, tableDesc, splitKeys, false);
        } else if (HBaseManagerConstants.SPLIT_3.equals(tableDescDto.getSplitWay())) {
            final SplitGoEnum splitGoEnum = SplitGoEnum.getSplitGoEnum(tableDescDto.getSplitGo());
            if (splitGoEnum == null) {
                return error("预分区Key的分隔策略不能为空");
            }
            if (tableDescDto.getNumRegions() < 1) {
                return error("预分区数不能为0");
            }

            createTableRes = multiHBaseAdminService.createTable(clusterCode, tableDesc, splitGoEnum, tableDescDto.getNumRegions(), false);
        }

        if (!createTableRes) {
            return error("系统异常，HBase表[" + fullTableName + "]创建失败！");
        }
        return success("HBase表[" + fullTableName + "]创建成功！");
    }

    /**
     * 修改HBase
     */
    @GetMapping("/edit/{tableId}")
    public String edit(@PathVariable("tableId") String tableId, ModelMap mmap) {
        final String tableName = StrEnDeUtils.decrypt(tableId);

        String clusterCode = clusterCodeOfCurrentSession();
        final List<NamespaceDesc> namespaceDescList = multiHBaseAdminService.listAllNamespaceDesc(clusterCode);
        mmap.put("namespaces", namespaceDescList);
        TableDesc tableDesc = multiHBaseAdminService.getTableDesc(clusterCode, tableName);
        TableDescDto tableDescDto = new TableDescDto().convertFor(tableDesc);
        mmap.put("tableDescDto", tableDescDto);
        mmap.put("tags", selectHBaseTagsByTable(tableDescDto));
        return prefix + "/edit";
    }


    /**
     * 修改保存HBase
     */
    @RequiresPermissions("system:table:edit")
    @Log(title = "HBase", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TableDescDto tableDescDto) {
        String clusterCode = clusterCodeOfCurrentSession();
        String tableName = StrEnDeUtils.decrypt(tableDescDto.getTableId());
        tableDescDto.setTableName(tableName);

        tableDescDto.setCreateBy(ShiroUtils.getLoginName());
        tableDescDto.setCreateTimestamp(System.currentTimeMillis());
        tableDescDto.setLastUpdateBy(ShiroUtils.getLoginName());
        tableDescDto.setLastUpdateTimestamp(System.currentTimeMillis());

        TableDesc tableDesc = tableDescDto.convertTo();
        if (tableDesc.isDisabled()) {
            multiHBaseAdminService.disableTable(clusterCode, tableName);
        } else {
            multiHBaseAdminService.enableTable(clusterCode, tableName);
        }
        multiHBaseAdminService.modifyTable(clusterCode, tableDesc);
        return success();
    }

    /**
     * 修改保存HBase表禁用状态
     */
    @RequiresPermissions("system:table:edit")
    @Log(title = "HBase", businessType = BusinessType.UPDATE)
    @PostMapping("/changeDisableStatus")
    @ResponseBody
    public AjaxResult changeDisableStatus(QueryHBaseTableForm queryHBaseTableForm) {
        String clusterCode = clusterCodeOfCurrentSession();
        boolean changeTableDisabledStatusRes = false;
        String tableName = StrEnDeUtils.decrypt(queryHBaseTableForm.getTableId());

        if (multiHBaseAdminService.isTableDisabled(clusterCode, tableName)) {
            changeTableDisabledStatusRes = multiHBaseAdminService.enableTable(clusterCode, tableName);
        }
        if (!multiHBaseAdminService.isTableDisabled(clusterCode, tableName)) {
            changeTableDisabledStatusRes = multiHBaseAdminService.disableTable(clusterCode, tableName);
        }
        if (!changeTableDisabledStatusRes) {
            return error("系统异常，表状态修改失败！");
        }
        return success();
    }


    /**
     * 删除HBase表
     */
    @RequiresPermissions("system:table:remove")
    @Log(title = "HBase", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        final String clusterCode = clusterCodeOfCurrentSession();
        final String tableName = StrEnDeUtils.decrypt(ids);

        if (!multiHBaseAdminService.isTableDisabled(clusterCode, tableName)) {
            return error("非禁用状态的表不能被删除");
        }

        boolean deleteTableDisabledStatusRes = multiHBaseAdminService.deleteTable(clusterCode, tableName);
        if (!deleteTableDisabledStatusRes) {
            return error("系统异常，表状态修改失败！");
        }
        return success();
    }


    /**
     * 清空HBase表的数据
     */
    @RequiresPermissions("system:table:clear")
    @Log(title = "清空HBase表的数据", businessType = BusinessType.DELETE)
    @PostMapping("/truncatePreserveTable")
    @ResponseBody
    public AjaxResult truncatePreserveTable(String tableId) {
        final String tableName = parseTableNameFromTableId(tableId);
        final String clusterCode = clusterCodeOfCurrentSession();

        if (!multiHBaseAdminService.isTableDisabled(clusterCode, tableName)) {
            return error("非禁用表的数据不能被直接清空");
        }

        multiHBaseAdminService.truncatePreserve(clusterCode, tableName);
        return success("HBase表[" + tableName + "]的数据已成功被清空");
    }


    private List<NamespaceDescDto> getAllNamespaces() {
        return multiHBaseAdminService.listAllNamespaceDesc(clusterCodeOfCurrentSession())
                .stream().map(namespaceDesc -> new NamespaceDescDto().convertFor(namespaceDesc))
                .collect(Collectors.toList());
    }


    /**
     * 筛选表标签
     *
     * @param tableDescDto 表的描述信息
     * @return 表标签
     */
    private List<SysHbaseTag> selectHBaseTagsByTable(TableDescDto tableDescDto) {
        List<SysHbaseTag> hbaseTags = getSysHbaseTagByLongIds(tableDescDto.getTagIds());
        List<SysHbaseTag> tags = sysHbaseTagService.selectAllSysHbaseTagList();
        for (SysHbaseTag tag : tags) {
            for (SysHbaseTag hbaseTag : hbaseTags) {
                if (tag.getTagId().longValue() == hbaseTag.getTagId().longValue()) {
                    tag.setFlag(true);
                    break;
                }
            }
        }
        return tags;
    }


}
