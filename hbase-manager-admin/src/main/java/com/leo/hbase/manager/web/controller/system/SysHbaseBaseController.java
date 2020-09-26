package com.leo.hbase.manager.web.controller.system;

import com.github.CCweixiao.util.StrUtil;
import com.leo.hbase.manager.common.core.controller.BaseController;
import com.leo.hbase.manager.common.exception.BusinessException;
import com.leo.hbase.manager.common.utils.HBaseConfigUtils;
import com.leo.hbase.manager.common.utils.spring.SpringUtils;
import com.leo.hbase.manager.framework.shiro.session.OnlineSession;
import com.leo.hbase.manager.framework.shiro.session.OnlineSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

/**
 * @author leojie 2020/9/26 4:19 下午
 */
public class SysHbaseBaseController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(SysHbaseBaseController.class);

    public String clusterCodeOfCurrentSession() {
        HttpSession session = getSession();
        if (session == null) {
            throw new BusinessException("当前会话已经过期，请登录重试");
        }
        String sessionId = session.getId();
        OnlineSessionDAO onlineSessionDAO = SpringUtils.getBean(OnlineSessionDAO.class);
        OnlineSession onlineSession = (OnlineSession) onlineSessionDAO.readSession(sessionId);
        if (onlineSession == null) {
            throw new BusinessException("当前会话已经过期，请登录重试");
        }
        final String cluster = onlineSession.getCluster();
        if (StrUtil.isBlank(cluster)) {
            return HBaseConfigUtils.getAllClusterAlias().get(0);
        }
        LOG.info("当前用户{},拥有的sessionID是{},操作的集群是{}", onlineSession.getLoginName(), sessionId, cluster);
        return cluster;
    }
}
