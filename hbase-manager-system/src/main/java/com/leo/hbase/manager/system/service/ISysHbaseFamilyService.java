package com.leo.hbase.manager.system.service;

import java.util.List;
import com.leo.hbase.manager.system.domain.SysHbaseFamily;

/**
 * HBase FamilyService接口
 * 
 * @author leojie
 * @date 2020-08-22
 */
public interface ISysHbaseFamilyService 
{
    /**
     * 查询HBase Family
     * 
     * @param familyId HBase FamilyID
     * @return HBase Family
     */
    public SysHbaseFamily selectSysHbaseFamilyById(Long familyId);

    /**
     * 查询HBase Family列表
     * 
     * @param sysHbaseFamily HBase Family
     * @return HBase Family集合
     */
    public List<SysHbaseFamily> selectSysHbaseFamilyList(SysHbaseFamily sysHbaseFamily);

    /**
     * 新增HBase Family
     * 
     * @param sysHbaseFamily HBase Family
     * @return 结果
     */
    public int insertSysHbaseFamily(SysHbaseFamily sysHbaseFamily);

    /**
     * 修改HBase Family
     * 
     * @param sysHbaseFamily HBase Family
     * @return 结果
     */
    public int updateSysHbaseFamily(SysHbaseFamily sysHbaseFamily);

    /**
     * 批量删除HBase Family
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysHbaseFamilyByIds(String ids);

    /**
     * 删除HBase Family信息
     * 
     * @param familyId HBase FamilyID
     * @return 结果
     */
    public int deleteSysHbaseFamilyById(Long familyId);
}
