package com.leo.hbase.manager.system.service.impl;

import java.util.List;
import com.leo.hbase.manager.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.leo.hbase.manager.system.mapper.SysHbaseTagMapper;
import com.leo.hbase.manager.system.domain.SysHbaseTag;
import com.leo.hbase.manager.system.service.ISysHbaseTagService;
import com.leo.hbase.manager.common.core.text.Convert;

/**
 * HBaseTagService业务层处理
 * 
 * @author leojie
 * @date 2020-08-16
 */
@Service
public class SysHbaseTagServiceImpl implements ISysHbaseTagService 
{
    @Autowired
    private SysHbaseTagMapper sysHbaseTagMapper;

    /**
     * 查询HBaseTag
     * 
     * @param tagId HBaseTagID
     * @return HBaseTag
     */
    @Override
    public SysHbaseTag selectSysHbaseTagById(Long tagId)
    {
        return sysHbaseTagMapper.selectSysHbaseTagById(tagId);
    }

    /**
     * 查询HBaseTag列表
     * 
     * @param sysHbaseTag HBaseTag
     * @return HBaseTag
     */
    @Override
    public List<SysHbaseTag> selectSysHbaseTagList(SysHbaseTag sysHbaseTag)
    {
        return sysHbaseTagMapper.selectSysHbaseTagList(sysHbaseTag);
    }

    /**
     * 新增HBaseTag
     * 
     * @param sysHbaseTag HBaseTag
     * @return 结果
     */
    @Override
    public int insertSysHbaseTag(SysHbaseTag sysHbaseTag)
    {
        sysHbaseTag.setCreateTime(DateUtils.getNowDate());
        return sysHbaseTagMapper.insertSysHbaseTag(sysHbaseTag);
    }

    /**
     * 修改HBaseTag
     * 
     * @param sysHbaseTag HBaseTag
     * @return 结果
     */
    @Override
    public int updateSysHbaseTag(SysHbaseTag sysHbaseTag)
    {
        sysHbaseTag.setUpdateTime(DateUtils.getNowDate());
        return sysHbaseTagMapper.updateSysHbaseTag(sysHbaseTag);
    }

    /**
     * 删除HBaseTag对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysHbaseTagByIds(String ids)
    {
        return sysHbaseTagMapper.deleteSysHbaseTagByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除HBaseTag信息
     * 
     * @param tagId HBaseTagID
     * @return 结果
     */
    @Override
    public int deleteSysHbaseTagById(Long tagId)
    {
        return sysHbaseTagMapper.deleteSysHbaseTagById(tagId);
    }
}
