package com.leo.hbase.manager.system.service;

import com.leo.hbase.manager.system.domain.SysHbaseTableTag;

import java.util.List;

/**
 * HBaseTagMapper接口
 * 
 * @author leojie
 * @date 2020-08-16
 */
public interface ISysHbaseTableTagService
{
    int insertSysHbaseTableTag(SysHbaseTableTag sysHbaseTableTag);

    int deleteSysHbaseTableTag(SysHbaseTableTag sysHbaseTableTag);

    int deleteSysHbaseTableTagByTagId(Long tagId);

    int deleteSysHbaseTableTagByTableId(Long tableId);

    int batchAddSysHbaseTableTag(List<SysHbaseTableTag> sysHbaseTableTagList);

}
