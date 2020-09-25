package com.leo.hbase.manager.web.service;

import java.util.List;

/**
 * @author leojie 2020/9/24 9:46 下午
 */

public interface IMultiHBaseAdminService {
    /**
     * 获取所有的命名空间
     * @param clusterCode 集群code
     * @return 所有的命名空间
     */
    List<String> listAllNamespaceName(String clusterCode);
}
