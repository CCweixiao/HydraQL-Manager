package com.leo.hbase.manager.system.mapper;

import java.util.List;

import com.leo.hbase.manager.system.domain.SysRoleDept;
import com.leo.hbase.manager.system.domain.SysUserHbaseTable;
import org.apache.ibatis.annotations.Param;

/**
 * 用户和HBase的关联Mapper接口
 * 
 * @author leojie
 * @date 2021-02-06
 */
public interface SysUserHbaseTableMapper 
{
    /**
     * 查询用户和HBase的关联
     * 
     * @param userId 用户和HBase的关联ID
     * @return 用户和HBase的关联
     */
    public SysUserHbaseTable selectSysUserHbaseTableById(Long userId);

    /**
     * 查询用户和HBase的关联列表
     * 
     * @param sysUserHbaseTable 用户和HBase的关联
     * @return 用户和HBase的关联集合
     */
    public List<SysUserHbaseTable> selectSysUserHbaseTableList(SysUserHbaseTable sysUserHbaseTable);

    /**
     * 查询用户和HBase的关联列表
     *
     * @param userId 用户ID
     * @param clusterAlias 集群ID
     * @return 用户和HBase的关联集合
     */
    public List<SysUserHbaseTable> selectSysUserHbaseTableListByUserAndClusterAlias(@Param("userId") Long userId,
                                                                                    @Param("clusterAlias") String clusterAlias);

    /**
     * 新增用户和HBase的关联
     * 
     * @param sysUserHbaseTable 用户和HBase的关联
     * @return 结果
     */
    public int insertSysUserHbaseTable(SysUserHbaseTable sysUserHbaseTable);

    /**
     * 修改用户和HBase的关联
     * 
     * @param sysUserHbaseTable 用户和HBase的关联
     * @return 结果
     */
    public int updateSysUserHbaseTable(SysUserHbaseTable sysUserHbaseTable);

    /**
     * 删除用户和HBase的关联
     * 
     * @param userId 用户和HBase的关联ID
     * @return 结果
     */
    public int deleteSysUserHbaseTableById(Long userId);

    /**
     * 删除用户和HBase的关联
     *
     * @param sysUserHbaseTable 用户和HBase的关联
     * @return 结果
     */
    public int deleteSysUserHbaseTable(SysUserHbaseTable sysUserHbaseTable);

    /**
     * 批量删除用户和HBase的关联
     * 
     * @param userIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysUserHbaseTableByIds(String[] userIds);

    /**
     * 批量新增用户和HBase的关联
     *
     * @param sysUserHbaseTableList 用户和HBase的关联列表
     * @return 结果
     */
    public int batchUserTable(List<SysUserHbaseTable> sysUserHbaseTableList);
}
