//package com.leo.hbase.manager.web.controller.system;
//
//import com.github.CCweixiao.hbase.sdk.hbtop.Record;
//import com.github.CCweixiao.hbase.sdk.hbtop.RecordFilter;
//import com.github.CCweixiao.hbase.sdk.hbtop.Summary;
//import com.github.CCweixiao.hbase.sdk.hbtop.field.Field;
//import com.github.CCweixiao.hbase.sdk.hbtop.mode.Mode;
//import com.leo.hbase.manager.common.core.domain.AjaxResult;
//import com.leo.hbase.manager.common.utils.StringUtils;
//import com.leo.hbase.manager.web.service.IMultiHBaseAdminService;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * @author leojie 2021/1/17 9:08 上午
// */
//@Controller
//@RequestMapping("/system/cluster/metric")
//public class SysHbaseClusterMetricController extends SysHbaseBaseController {
//    private String prefix = "system/cluster/metric";
//
//    @Autowired
//    private IMultiHBaseAdminService multiHBaseAdminService;
//
//    /**
//     * 查询HBase汇总指标
//     */
//    @RequiresPermissions("system:cluster:metric")
//    @GetMapping("/summary")
//    @ResponseBody
//    public AjaxResult summary() {
//        final Summary summary = multiHBaseAdminService.refreshSummary(clusterCodeOfCurrentSession());
//        return AjaxResult.success(summary);
//    }
//
//    /**
//     * 查询HBase写入以及读取请求量
//     */
//    @RequiresPermissions("system:cluster:metric")
//    @GetMapping("/requests")
//    @ResponseBody
//    public AjaxResult requests(QueryRequestsParam queryRequestsParam) {
//        Mode currentMode = getCurrentMode(queryRequestsParam);
//        Field showField = getShowField(currentMode);
//
//        if (StringUtils.isBlank(queryRequestsParam.getModeName())) {
//            currentMode = Mode.TABLE;
//        } else {
//            currentMode = Mode.valueOf(queryRequestsParam.getModeName());
//        }
//
//
//        List<RecordFilter> filters = new ArrayList<>();
//
//        final List<Record> writeRecords = multiHBaseAdminService.refreshRecords(clusterCodeOfCurrentSession(), currentMode, filters, Field.WRITE_REQUEST_COUNT_PER_SECOND, false);
//        final List<Record> readRecords = multiHBaseAdminService.refreshRecords(clusterCodeOfCurrentSession(), currentMode, filters, Field.READ_REQUEST_COUNT_PER_SECOND, false);
//
//        List<String> xValues;
//        if(currentMode == Mode.TABLE){
//            xValues = writeRecords.stream().map(record -> record.get(Field.NAMESPACE).asString().concat(":").concat(record.get(Field.TABLE).asString()))
//                    .collect(Collectors.toList());
//        }else{
//            xValues = writeRecords.stream().map(record -> record.get(showField).asString()).collect(Collectors.toList());
//        }
//
//        List<String> yWriteRequestsValues = writeRecords.stream().map(record -> record.get(Field.WRITE_REQUEST_COUNT_PER_SECOND).asString()).collect(Collectors.toList());
//        List<String> yReadRequestsValues = readRecords.stream().map(record -> record.get(Field.READ_REQUEST_COUNT_PER_SECOND).asString()).collect(Collectors.toList());
//
//        Map<String, Object> data = new HashMap<>(3);
//        data.put("xValues", xValues);
//
//        data.put("yWriteRequestsValues", yWriteRequestsValues);
//        data.put("yReadRequestsValues", yReadRequestsValues);
//
//        data.put("modeName", currentMode.name());
//
//        return AjaxResult.success(data);
//    }
//
//    public static class QueryRequestsParam {
//        private String modeName;
//
//        public String getModeName() {
//            return modeName;
//        }
//
//        public void setModeName(String modeName) {
//            this.modeName = modeName;
//        }
//
//    }
//
//    private Mode getCurrentMode(QueryRequestsParam queryRequestsParam) {
//        Mode currentMode;
//
//        if (StringUtils.isBlank(queryRequestsParam.getModeName())) {
//            currentMode = Mode.TABLE;
//        } else {
//            currentMode = Mode.valueOf(queryRequestsParam.getModeName());
//        }
//
//        return currentMode;
//    }
//
//    private Field getShowField(Mode currentMode) {
//        Field showField;
//
//        if (currentMode == Mode.REGION_SERVER) {
//            showField = Field.REGION_SERVER;
//        } else if (currentMode == Mode.REGION) {
//            showField = Field.REGION_NAME;
//        } else if (currentMode == Mode.NAMESPACE) {
//            showField = Field.NAMESPACE;
//        } else if (currentMode == Mode.TABLE) {
//            showField = Field.TABLE;
//        } else {
//            showField = Field.TABLE;
//        }
//        return showField;
//    }
//
//
//}
