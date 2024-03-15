package com.leo.hbase.manager.web.controller.system;

import com.alibaba.fastjson2.JSON;
import com.hydraql.manager.core.hbase.model.Result;
import com.leo.hbase.manager.common.exception.BusinessException;
import com.leo.hbase.manager.common.utils.StringUtils;
import com.leo.hbase.manager.common.utils.spring.SpringUtils;
import com.leo.hbase.manager.system.dto.HBaseShellCommand;
import com.leo.hbase.manager.web.service.impl.HBaseShellServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author leojie 2023/7/11 09:12
 */
@Component
@ServerEndpoint("/ws/{sessionId}")
public class SysHbaseWebSocketController {
    private static final Logger LOG = LoggerFactory.getLogger(SysHbaseWebSocketController.class);

    private final static AtomicInteger ONLINE_COUNT = new AtomicInteger(0);
    private final static CopyOnWriteArraySet<SysHbaseWebSocketController> WEB_SOCKET_SERVERS = new CopyOnWriteArraySet<>();

    private Session session;
    private String sessionId = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sessionId") String sessionId) {
        this.session = session;
        WEB_SOCKET_SERVERS.add(this);
        this.sessionId = sessionId;
        int onlineNum = ONLINE_COUNT.incrementAndGet();

        try {
            sendMessage("conn_success");
            LOG.info("有新的shell回话开始连接:" + sessionId + "，当前shell连接数为:" + onlineNum);
        } catch (IOException e) {
            LOG.error("websocket IO Exception");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        WEB_SOCKET_SERVERS.remove(this);
        int onlineNum = ONLINE_COUNT.decrementAndGet();
        //断开连接情况下，更新主板占用情况为释放
        LOG.info("释放的shell回话为：" + sessionId);
        //这里写你 释放的时候，要处理的业务
        LOG.info("一shell回话连接关闭！当前shell连接数为" + onlineNum);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        LOG.info("收到来自shell回话：" + sessionId + "的信息:" + message);
        Result result = null;
        if (StringUtils.isBlank(message)) {
            result = Result.failed("请输入需要执行的命令～");
        } else {
            HBaseShellServiceImpl shellService = SpringUtils.getBean(HBaseShellServiceImpl.class);
            HBaseShellCommand shellCommand = JSON.parseObject(message, HBaseShellCommand.class);
            result = shellService.execute(shellCommand);
            System.out.println();
        }

        try {
            sendMessage(JSON.toJSONString(result));
        } catch (IOException e) {
            LOG.info("shell执行结果回显失败～", e);
            throw new BusinessException("shell执行结果回显失败～");
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        LOG.error("发生错误", error);
    }

    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysHbaseWebSocketController)) {
            return false;
        }
        SysHbaseWebSocketController that = (SysHbaseWebSocketController) o;
        return Objects.equals(session, that.session) && Objects.equals(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, sessionId);
    }
}
