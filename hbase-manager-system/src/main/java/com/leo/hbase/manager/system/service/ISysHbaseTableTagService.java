package com.leo.hbase.manager.system.service;

import java.util.List;
import com.leo.hbase.manager.system.domain.SysHbaseTableTag;

/**
 * HBase所属TagService接口
 * 
 * @author leojie
 * @date 2020-08-16
 */
public interface ISysHbaseTableTagService 
{
    /**
     * 查询HBase所属Tag
     * 
     * @param tableId HBase所属TagID
     * @return HBase所属Tag
     */
    public SysHbaseTableTag selectSysHbaseTableTagById(Long tableId);

    /**
     * 查询HBase所属Tag列表
     * 
     * @param sysHbaseTableTag HBase所属Tag
     * @return HBase所属Tag集合
     */
    public List<SysHbaseTableTag> selectSysHbaseTableTagList(SysHbaseTableTag sysHbaseTableTag);

    /**
     * 新增HBase所属Tag
     * 
     * @param sysHbaseTableTag HBase所属Tag
     * @return 结果
     */
    public int insertSysHbaseTableTag(SysHbaseTableTag sysHbaseTableTag);

    /**
     * 批量添加HBase所属标签
     * @param sysHbaseTableTagList HBase所属Tag
     * @return 结果
     */
    public int batchInsertSysHbaseTableTag(List<SysHbaseTableTag> sysHbaseTableTagList);

    /**
     * 修改HBase所属Tag
     * 
     * @param sysHbaseTableTag HBase所属Tag
     * @return 结果
     */
    public int updateSysHbaseTableTag(SysHbaseTableTag sysHbaseTableTag);

    /**
     * 批量删除HBase所属Tag
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysHbaseTableTagByIds(String ids);

    /**
     * 删除HBase所属Tag信息
     * 
     * @param tableId HBase所属TagID
     * @return 结果
     */
    public int deleteSysHbaseTableTagById(Long tableId);

}
