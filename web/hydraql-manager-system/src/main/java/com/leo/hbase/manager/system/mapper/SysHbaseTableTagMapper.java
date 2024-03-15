package com.leo.hbase.manager.system.mapper;

import com.leo.hbase.manager.system.domain.SysHbaseTableTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * HBaseTagMapper接口
 * 
 * @author leojie
 * @date 2020-08-16
 */
public interface SysHbaseTableTagMapper
{

    int insertSysHbaseTableTag(SysHbaseTableTag sysHbaseTableTag);

    int deleteSysHbaseTableTag(SysHbaseTableTag sysHbaseTableTag);

    int deleteSysHbaseTableTagByTagId(@Param(value = "tagId") Long tagId);

    int deleteSysHbaseTableTagByTableId(@Param(value = "tableId") Long tableId);

    int batchAddSysHbaseTableTag(@Param(value = "sysHbaseTableTagList") List<SysHbaseTableTag> sysHbaseTableTagList);

}
