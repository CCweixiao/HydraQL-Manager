package com.leo.hbase.manager.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.leo.hbase.manager.system.mapper.SysHbaseUserTableMapper;
import com.leo.hbase.manager.system.domain.SysHbaseUserTable;
import com.leo.hbase.manager.system.service.ISysHbaseUserTableService;
import com.leo.hbase.manager.common.core.text.Convert;

/**
 * HBase所属用户Service业务层处理
 * 
 * @author leojie
 * @date 2020-08-16
 */
@Service
public class SysHbaseUserTableServiceImpl implements ISysHbaseUserTableService 
{
    @Autowired
    private SysHbaseUserTableMapper sysHbaseUserTableMapper;

    /**
     * 查询HBase所属用户
     * 
     * @param userId HBase所属用户ID
     * @return HBase所属用户
     */
    @Override
    public SysHbaseUserTable selectSysHbaseUserTableById(Long userId)
    {
        return sysHbaseUserTableMapper.selectSysHbaseUserTableById(userId);
    }

    /**
     * 查询HBase所属用户列表
     * 
     * @param sysHbaseUserTable HBase所属用户
     * @return HBase所属用户
     */
    @Override
    public List<SysHbaseUserTable> selectSysHbaseUserTableList(SysHbaseUserTable sysHbaseUserTable)
    {
        return sysHbaseUserTableMapper.selectSysHbaseUserTableList(sysHbaseUserTable);
    }

    /**
     * 新增HBase所属用户
     * 
     * @param sysHbaseUserTable HBase所属用户
     * @return 结果
     */
    @Override
    public int insertSysHbaseUserTable(SysHbaseUserTable sysHbaseUserTable)
    {
        return sysHbaseUserTableMapper.insertSysHbaseUserTable(sysHbaseUserTable);
    }

    /**
     * 修改HBase所属用户
     * 
     * @param sysHbaseUserTable HBase所属用户
     * @return 结果
     */
    @Override
    public int updateSysHbaseUserTable(SysHbaseUserTable sysHbaseUserTable)
    {
        return sysHbaseUserTableMapper.updateSysHbaseUserTable(sysHbaseUserTable);
    }

    /**
     * 删除HBase所属用户对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysHbaseUserTableByIds(String ids)
    {
        return sysHbaseUserTableMapper.deleteSysHbaseUserTableByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除HBase所属用户信息
     * 
     * @param userId HBase所属用户ID
     * @return 结果
     */
    @Override
    public int deleteSysHbaseUserTableById(Long userId)
    {
        return sysHbaseUserTableMapper.deleteSysHbaseUserTableById(userId);
    }
}
