package com.leo.hbase.manager.system.service;

import java.util.List;

import com.leo.hbase.manager.system.domain.SysUserHbaseTable;
import org.apache.ibatis.annotations.Param;

/**
 * 用户和HBase的关联Service接口
 *
 * @author leojie
 * @date 2021-02-06
 */
public interface ISysUserHbaseTableService {
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
     * 批量删除用户和HBase的关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysUserHbaseTableByIds(String ids);

    /**
     * 删除用户和HBase的关联信息
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
     * 设置用户和HBase表的关联信息
     * @param userId 操作对应用户的ID
     * @param sysUserHbaseTableList 用户和HBase的关联信息列表
     * @return 是否设置成功
     */
    public int authUserTable(Long userId, List<SysUserHbaseTable> sysUserHbaseTableList);
}
