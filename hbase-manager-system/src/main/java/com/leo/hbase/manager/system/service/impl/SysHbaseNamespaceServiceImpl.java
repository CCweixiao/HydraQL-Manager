package com.leo.hbase.manager.system.service.impl;

import java.util.List;

import com.leo.hbase.manager.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.leo.hbase.manager.system.mapper.SysHbaseNamespaceMapper;
import com.leo.hbase.manager.system.domain.SysHbaseNamespace;
import com.leo.hbase.manager.system.service.ISysHbaseNamespaceService;
import com.leo.hbase.manager.common.core.text.Convert;

/**
 * HBaseNamespaceService业务层处理
 *
 * @author leojie
 * @date 2020-08-16
 */
@Service
public class SysHbaseNamespaceServiceImpl implements ISysHbaseNamespaceService {
    @Autowired
    private SysHbaseNamespaceMapper sysHbaseNamespaceMapper;

    /**
     * 查询HBaseNamespace
     *
     * @param namespaceId HBaseNamespaceID
     * @return HBaseNamespace
     */
    @Override
    public SysHbaseNamespace selectSysHbaseNamespaceById(Long namespaceId) {
        return sysHbaseNamespaceMapper.selectSysHbaseNamespaceById(namespaceId);
    }

    /**
     * 查询HBaseNamespace列表
     *
     * @param sysHbaseNamespace HBaseNamespace
     * @return HBaseNamespace
     */
    @Override
    public List<SysHbaseNamespace> selectSysHbaseNamespaceList(SysHbaseNamespace sysHbaseNamespace) {
        return sysHbaseNamespaceMapper.selectSysHbaseNamespaceList(sysHbaseNamespace);
    }

    @Override
    public List<SysHbaseNamespace> selectAllSysHbaseNamespaceList() {
        return sysHbaseNamespaceMapper.selectAllSysHbaseNamespaceList();
    }

    /**
     * 新增HBaseNamespace
     *
     * @param sysHbaseNamespace HBaseNamespace
     * @return 结果
     */
    @Override
    public int insertSysHbaseNamespace(SysHbaseNamespace sysHbaseNamespace) {
        sysHbaseNamespace.setCreateTime(DateUtils.getNowDate());
        return sysHbaseNamespaceMapper.insertSysHbaseNamespace(sysHbaseNamespace);
    }

    /**
     * 修改HBaseNamespace
     *
     * @param sysHbaseNamespace HBaseNamespace
     * @return 结果
     */
    @Override
    public int updateSysHbaseNamespace(SysHbaseNamespace sysHbaseNamespace) {
        sysHbaseNamespace.setUpdateTime(DateUtils.getNowDate());
        return sysHbaseNamespaceMapper.updateSysHbaseNamespace(sysHbaseNamespace);
    }

    /**
     * 删除HBaseNamespace对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysHbaseNamespaceByIds(String ids) {
        return sysHbaseNamespaceMapper.deleteSysHbaseNamespaceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除HBaseNamespace信息
     *
     * @param namespaceId HBaseNamespaceID
     * @return 结果
     */
    @Override
    public int deleteSysHbaseNamespaceById(Long namespaceId) {
        return sysHbaseNamespaceMapper.deleteSysHbaseNamespaceById(namespaceId);
    }
}
