package com.leo.hbase.manager.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hydraql.manager.core.hbase.model.Result;
import com.hydraql.manager.core.template.HydraQLTemplate;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.system.dto.HBaseShellCommand;
import com.leo.hbase.manager.system.dto.HBaseShellCommandModel;
import com.leo.hbase.manager.web.hds.HBaseClusterDSConfig;
import com.leo.hbase.manager.web.service.IHBaseShellService;

/**
 * @author leojie 2023/7/10 22:12
 */
@Service
public class HBaseShellServiceImpl implements IHBaseShellService {
    private final HBaseClusterDSConfig hBaseClusterDSConfig;

    public HBaseShellServiceImpl(HBaseClusterDSConfig hBaseClusterDSConfig) {
        this.hBaseClusterDSConfig = hBaseClusterDSConfig;
    }

    @Override
    public Result execute(HBaseShellCommand command) {
        String clusterId = command.getClusterId();
        if (StringUtils.isBlank(clusterId)) {
            return Result.failed("接收命令的集群ID不能为空～");
        }
        String commandContent = command.getCommand();
        if (StringUtils.isBlank(commandContent)) {
            return Result.failed("待执行命令不能为空～");
        }

        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterId);
        if (!template.shellSessionIsConnected()) {
            return Result.failed(String.format("与集群[%s]的连接已断开～", clusterId));
        }
        return template.executeShellCommand(commandContent);
    }

    @Override
    public  Map<String, Map<String, List<String>>> getAllCommands(String clusterCode) {
        HydraQLTemplate template = hBaseClusterDSConfig.getHydraqlTemplate(clusterCode);
        List<String> allCommands = template.getAllShellCommands();
        return HBaseShellCommandModel.generateHBaseShellCommand(new ArrayList<>(allCommands));
    }
}
