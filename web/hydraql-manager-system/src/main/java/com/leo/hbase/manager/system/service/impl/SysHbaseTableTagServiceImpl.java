package com.leo.hbase.manager.system.service.impl;

import com.leo.hbase.manager.system.domain.SysHbaseTableTag;
import com.leo.hbase.manager.system.mapper.SysHbaseTableTagMapper;
import com.leo.hbase.manager.system.service.ISysHbaseTableTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leojie 2023/7/16 22:25
 */
@Service
public class SysHbaseTableTagServiceImpl implements ISysHbaseTableTagService {
    @Autowired
    private SysHbaseTableTagMapper sysHbaseTableTagMapper;

    @Override
    public int insertSysHbaseTableTag(SysHbaseTableTag sysHbaseTableTag) {
        return sysHbaseTableTagMapper.insertSysHbaseTableTag(sysHbaseTableTag);
    }

    @Override
    public int deleteSysHbaseTableTag(SysHbaseTableTag sysHbaseTableTag) {
        return sysHbaseTableTagMapper.deleteSysHbaseTableTag(sysHbaseTableTag);
    }

    @Override
    public int deleteSysHbaseTableTagByTagId(Long tagId) {
        return sysHbaseTableTagMapper.deleteSysHbaseTableTagByTagId(tagId);
    }

    @Override
    public int deleteSysHbaseTableTagByTableId(Long tableId) {
        return sysHbaseTableTagMapper.deleteSysHbaseTableTagByTableId(tableId);
    }

    @Override
    public int batchAddSysHbaseTableTag(List<SysHbaseTableTag> sysHbaseTableTagList) {
        return sysHbaseTableTagMapper.batchAddSysHbaseTableTag(sysHbaseTableTagList);
    }
}
