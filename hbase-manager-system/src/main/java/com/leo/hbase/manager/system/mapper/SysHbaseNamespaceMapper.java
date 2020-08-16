package com.leo.hbase.manager.system.mapper;

import java.util.List;

import com.leo.hbase.manager.system.domain.SysHbaseNamespace;

/**
 * HBaseNamespaceMapper接口
 *
 * @author leojie
 * @date 2020-08-16
 */
public interface SysHbaseNamespaceMapper {
    /**
     * 查询HBaseNamespace
     *
     * @param namespaceId HBaseNamespaceID
     * @return HBaseNamespace
     */
    public SysHbaseNamespace selectSysHbaseNamespaceById(Long namespaceId);

    /**
     * 查询HBaseNamespace列表
     *
     * @param sysHbaseNamespace HBaseNamespace
     * @return HBaseNamespace集合
     */
    public List<SysHbaseNamespace> selectSysHbaseNamespaceList(SysHbaseNamespace sysHbaseNamespace);

    /**
     * 新增HBaseNamespace
     *
     * @param sysHbaseNamespace HBaseNamespace
     * @return 结果
     */
    public int insertSysHbaseNamespace(SysHbaseNamespace sysHbaseNamespace);

    /**
     * 修改HBaseNamespace
     *
     * @param sysHbaseNamespace HBaseNamespace
     * @return 结果
     */
    public int updateSysHbaseNamespace(SysHbaseNamespace sysHbaseNamespace);

    /**
     * 删除HBaseNamespace
     *
     * @param namespaceId HBaseNamespaceID
     * @return 结果
     */
    public int deleteSysHbaseNamespaceById(Long namespaceId);

    /**
     * 批量删除HBaseNamespace
     *
     * @param namespaceIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysHbaseNamespaceByIds(String[] namespaceIds);
}
