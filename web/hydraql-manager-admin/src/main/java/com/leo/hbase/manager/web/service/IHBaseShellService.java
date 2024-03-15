package com.leo.hbase.manager.web.service;

import com.hydraql.manager.core.hbase.model.Result;
import com.leo.hbase.manager.system.dto.HBaseShellCommand;

import java.util.List;
import java.util.Map;

/**
 * @author leojie 2023/7/10 22:12
 */
public interface IHBaseShellService {
    Result execute(HBaseShellCommand command);

    Map<String, Map<String, List<String>>> getAllCommands(String clusterCode);
}
