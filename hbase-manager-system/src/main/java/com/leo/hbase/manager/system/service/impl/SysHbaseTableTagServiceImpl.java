package com.leo.hbase.manager.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.leo.hbase.manager.system.mapper.SysHbaseTableTagMapper;
import com.leo.hbase.manager.system.domain.SysHbaseTableTag;
import com.leo.hbase.manager.system.service.ISysHbaseTableTagService;
import com.leo.hbase.manager.common.core.text.Convert;

/**
 * HBase所属TagService业务层处理
 * 
 * @author leojie
 * @date 2020-08-16
 */
@Service
public class SysHbaseTableTagServiceImpl implements ISysHbaseTableTagService 
{
    @Autowired
    private SysHbaseTableTagMapper sysHbaseTableTagMapper;

    /**
     * 查询HBase所属Tag
     * 
     * @param tableId HBase所属TagID
     * @return HBase所属Tag
     */
    @Override
    public SysHbaseTableTag selectSysHbaseTableTagById(Long tableId)
    {
        return sysHbaseTableTagMapper.selectSysHbaseTableTagById(tableId);
    }

    /**
     * 查询HBase所属Tag列表
     * 
     * @param sysHbaseTableTag HBase所属Tag
     * @return HBase所属Tag
     */
    @Override
    public List<SysHbaseTableTag> selectSysHbaseTableTagList(SysHbaseTableTag sysHbaseTableTag)
    {
        return sysHbaseTableTagMapper.selectSysHbaseTableTagList(sysHbaseTableTag);
    }

    /**
     * 新增HBase所属Tag
     * 
     * @param sysHbaseTableTag HBase所属Tag
     * @return 结果
     */
    @Override
    public int insertSysHbaseTableTag(SysHbaseTableTag sysHbaseTableTag)
    {
        return sysHbaseTableTagMapper.insertSysHbaseTableTag(sysHbaseTableTag);
    }

    @Override
    public int batchInsertSysHbaseTableTag(List<SysHbaseTableTag> sysHbaseTableTagList) {
        return sysHbaseTableTagMapper.batchInsertSysHbaseTableTag(sysHbaseTableTagList);
    }

    /**
     * 修改HBase所属Tag
     * 
     * @param sysHbaseTableTag HBase所属Tag
     * @return 结果
     */
    @Override
    public int updateSysHbaseTableTag(SysHbaseTableTag sysHbaseTableTag)
    {
        return sysHbaseTableTagMapper.updateSysHbaseTableTag(sysHbaseTableTag);
    }

    /**
     * 删除HBase所属Tag对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysHbaseTableTagByIds(String ids)
    {
        return sysHbaseTableTagMapper.deleteSysHbaseTableTagByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除HBase所属Tag信息
     * 
     * @param tableId HBase所属TagID
     * @return 结果
     */
    @Override
    public int deleteSysHbaseTableTagById(Long tableId)
    {
        return sysHbaseTableTagMapper.deleteSysHbaseTableTagById(tableId);
    }
}
