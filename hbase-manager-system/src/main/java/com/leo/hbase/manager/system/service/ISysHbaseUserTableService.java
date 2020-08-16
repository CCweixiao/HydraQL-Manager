package com.leo.hbase.manager.system.service;

import java.util.List;
import com.leo.hbase.manager.system.domain.SysHbaseUserTable;

/**
 * HBase所属用户Service接口
 * 
 * @author leojie
 * @date 2020-08-16
 */
public interface ISysHbaseUserTableService 
{
    /**
     * 查询HBase所属用户
     * 
     * @param userId HBase所属用户ID
     * @return HBase所属用户
     */
    public SysHbaseUserTable selectSysHbaseUserTableById(Long userId);

    /**
     * 查询HBase所属用户列表
     * 
     * @param sysHbaseUserTable HBase所属用户
     * @return HBase所属用户集合
     */
    public List<SysHbaseUserTable> selectSysHbaseUserTableList(SysHbaseUserTable sysHbaseUserTable);

    /**
     * 新增HBase所属用户
     * 
     * @param sysHbaseUserTable HBase所属用户
     * @return 结果
     */
    public int insertSysHbaseUserTable(SysHbaseUserTable sysHbaseUserTable);

    /**
     * 修改HBase所属用户
     * 
     * @param sysHbaseUserTable HBase所属用户
     * @return 结果
     */
    public int updateSysHbaseUserTable(SysHbaseUserTable sysHbaseUserTable);

    /**
     * 批量删除HBase所属用户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysHbaseUserTableByIds(String ids);

    /**
     * 删除HBase所属用户信息
     * 
     * @param userId HBase所属用户ID
     * @return 结果
     */
    public int deleteSysHbaseUserTableById(Long userId);
}
