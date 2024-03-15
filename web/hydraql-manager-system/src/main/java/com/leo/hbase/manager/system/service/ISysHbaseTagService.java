package com.leo.hbase.manager.system.service;

import com.leo.hbase.manager.system.domain.SysHbaseTag;

import java.util.List;

/**
 * HBaseTagService接口
 *
 * @author leojie
 * @date 2020-08-16
 */
public interface ISysHbaseTagService {
    /**
     * 查询HBaseTag
     *
     * @param tagId HBaseTagID
     * @return HBaseTag
     */
    public SysHbaseTag selectSysHbaseTagById(Long tagId);

    /**
     * 根据名称查询HBaseTag
     *
     * @param tagName tag name
     * @return HBaseTag
     */
    public SysHbaseTag selectSysHbaseTagByName(String tagName);

    /**
     * 查询HBaseTag列表
     *
     * @param sysHbaseTag HBaseTag
     * @return HBaseTag集合
     */
    public List<SysHbaseTag> selectSysHbaseTagList(SysHbaseTag sysHbaseTag);

    /**
     * 批量查询tag
     *
     * @param ids tag ids
     * @return HBaseTag 列表
     */
    public List<SysHbaseTag> selectSysHbaseTagListByIds(String ids);

    /**
     * 查询所有HBaseTag列表
     *
     * @return HBaseTag集合
     */
    public List<SysHbaseTag> selectAllSysHbaseTagList();

    /**
     * 根据table id 查询所有HBaseTag列表
     *
     * @param tableId 表ID
     * @return HBaseTag集合
     */
    public List<SysHbaseTag> selectSysHbaseTagsByTableId(Long tableId);

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
     * 批量删除HBaseTag
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysHbaseTagByIds(String ids);

    /**
     * 删除HBaseTag信息
     *
     * @param tagId HBaseTagID
     * @return 结果
     */
    public int deleteSysHbaseTagById(Long tagId);
}
