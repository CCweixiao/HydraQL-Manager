package com.leo.hbase.manager.web.controller.system;

import com.leo.hbase.manager.common.utils.spring.SpringUtils;
import com.leo.hbase.manager.web.service.impl.HBaseShellServiceImpl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * @author leojie 2023/7/10 20:36
 */
@Controller()
@RequestMapping("/system/shell")
public class SysHbaseShellController extends SysHbaseBaseController {
    private final String prefix = "system/shell";

    @RequiresPermissions("hbase:shell:view")
    @GetMapping()
    public String detail(ModelMap mmap) {
        List<String> clusterIds = sysHbaseClusterService.getAllOnlineClusterIds();
        HBaseShellServiceImpl shellService = SpringUtils.getBean(HBaseShellServiceImpl.class);
        Map<String, Map<String, List<String>>> allCommands = shellService.getAllCommands(clusterIds.get(0));
        mmap.put("clusterIdList", clusterIds);
        mmap.put("shellSessionId", getSessionId());
        mmap.put("allCommands", allCommands);
        String basePath = getBasePath();
        basePath = basePath.replace("http", "ws");
        mmap.put("wsBasePath", basePath);
        return prefix + "/shell";
    }
}
