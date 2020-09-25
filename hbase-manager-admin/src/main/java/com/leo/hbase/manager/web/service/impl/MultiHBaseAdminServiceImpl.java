package com.leo.hbase.manager.web.service.impl;

import com.github.CCweixiao.HBaseAdminTemplate;
import com.leo.hbase.manager.web.hds.HBaseClusterDSHolder;
import com.leo.hbase.manager.web.service.IMultiHBaseAdminService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leojie 2020/9/24 9:47 下午
 */
@Service
public class MultiHBaseAdminServiceImpl implements IMultiHBaseAdminService {
    @Override
    public List<String> listAllNamespaceName(String clusterCode) {
        HBaseAdminTemplate hBaseTemplate = HBaseClusterDSHolder.instance().getHBaseAdminTemplate(clusterCode);
        return hBaseTemplate.listNamespaces();
    }
}
