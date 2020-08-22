package com.leo.hbase.manager.system.service.impl;

import java.util.List;
import com.leo.hbase.manager.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.leo.hbase.manager.system.mapper.SysHbaseFamilyMapper;
import com.leo.hbase.manager.system.domain.SysHbaseFamily;
import com.leo.hbase.manager.system.service.ISysHbaseFamilyService;
import com.leo.hbase.manager.common.core.text.Convert;

/**
 * HBase FamilyService业务层处理
 * 
 * @author leojie
 * @date 2020-08-22
 */
@Service
public class SysHbaseFamilyServiceImpl implements ISysHbaseFamilyService 
{
    @Autowired
    private SysHbaseFamilyMapper sysHbaseFamilyMapper;

    /**
     * 查询HBase Family
     * 
     * @param familyId HBase FamilyID
     * @return HBase Family
     */
    @Override
    public SysHbaseFamily selectSysHbaseFamilyById(Long familyId)
    {
        return sysHbaseFamilyMapper.selectSysHbaseFamilyById(familyId);
    }

    /**
     * 查询HBase Family列表
     * 
     * @param sysHbaseFamily HBase Family
     * @return HBase Family
     */
    @Override
    public List<SysHbaseFamily> selectSysHbaseFamilyList(SysHbaseFamily sysHbaseFamily)
    {
        return sysHbaseFamilyMapper.selectSysHbaseFamilyList(sysHbaseFamily);
    }

    /**
     * 新增HBase Family
     * 
     * @param sysHbaseFamily HBase Family
     * @return 结果
     */
    @Override
    public int insertSysHbaseFamily(SysHbaseFamily sysHbaseFamily)
    {
        sysHbaseFamily.setCreateTime(DateUtils.getNowDate());
        return sysHbaseFamilyMapper.insertSysHbaseFamily(sysHbaseFamily);
    }

    /**
     * 修改HBase Family
     * 
     * @param sysHbaseFamily HBase Family
     * @return 结果
     */
    @Override
    public int updateSysHbaseFamily(SysHbaseFamily sysHbaseFamily)
    {
        sysHbaseFamily.setUpdateTime(DateUtils.getNowDate());
        return sysHbaseFamilyMapper.updateSysHbaseFamily(sysHbaseFamily);
    }

    /**
     * 删除HBase Family对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysHbaseFamilyByIds(String ids)
    {
        return sysHbaseFamilyMapper.deleteSysHbaseFamilyByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除HBase Family信息
     * 
     * @param familyId HBase FamilyID
     * @return 结果
     */
    @Override
    public int deleteSysHbaseFamilyById(Long familyId)
    {
        return sysHbaseFamilyMapper.deleteSysHbaseFamilyById(familyId);
    }
}
