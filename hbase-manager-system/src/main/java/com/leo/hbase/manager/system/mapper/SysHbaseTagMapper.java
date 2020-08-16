package com.leo.hbase.manager.system.mapper;

import java.util.List;
import com.leo.hbase.manager.system.domain.SysHbaseTag;

/**
 * HBaseTagMapper接口
 * 
 * @author leojie
 * @date 2020-08-16
 */
public interface SysHbaseTagMapper 
{
    /**
     * 查询HBaseTag
     * 
     * @param tagId HBaseTagID
     * @return HBaseTag
     */
    public SysHbaseTag selectSysHbaseTagById(Long tagId);

    /**
     * 查询HBaseTag列表
     * 
     * @param sysHbaseTag HBaseTag
     * @return HBaseTag集合
     */
    public List<SysHbaseTag> selectSysHbaseTagList(SysHbaseTag sysHbaseTag);

    /**
     * 根据table id 查询所有HBaseTag列表
     * @param tableId 表ID
     * @return HBaseTag集合
     */
    public List<SysHbaseTag> selectSysHbaseTagsByTableId(Long tableId);

    /**
     * 查询所有HBaseTag列表
     *
     * @return HBaseTag集合
     */
    public List<SysHbaseTag> selectAllSysHbaseTagList();

    /**
     * 新增HBaseTag
     * 
     * @param sysHbaseTag HBaseTag
     * @return 结果
     */
    public int insertSysHbaseTag(SysHbaseTag sysHbaseTag);

    /**
     * 修改HBaseTag
     * 
     * @param sysHbaseTag HBaseTag
     * @return 结果
     */
    public int updateSysHbaseTag(SysHbaseTag sysHbaseTag);

    /**
     * 删除HBaseTag
     * 
     * @param tagId HBaseTagID
     * @return 结果
     */
    public int deleteSysHbaseTagById(Long tagId);

    /**
     * 批量删除HBaseTag
     * 
     * @param tagIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysHbaseTagByIds(String[] tagIds);
}
