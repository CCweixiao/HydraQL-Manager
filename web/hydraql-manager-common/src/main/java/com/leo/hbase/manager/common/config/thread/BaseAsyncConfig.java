package com.leo.hbase.manager.common.config.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/**
 * @author leojie 2023/7/19 22:12
 */

@Configuration
@EnableAsync
public class BaseAsyncConfig implements AsyncConfigurer {
    private static final Logger LOG = LoggerFactory.getLogger(BaseAsyncConfig.class);

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池数量，方法: 返回可用处理器的Java虚拟机的数量。
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        //最大线程数量
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 5);
        //线程池的队列容量，无界阻塞队列
        // executor.setQueueCapacity(Runtime.getRuntime().availableProcessors() * 2);
        //线程名称的前缀
        executor.setThreadNamePrefix("async-task-executor-");
        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
        //executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    /*异步任务中异常处理*/
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {

        return (Throwable ex, Method method, Object... params) -> {
            LOG.info("######################################start##############################################");
            LOG.warn("class#method: " + method.getDeclaringClass().getName() + "#" + method.getName());
            LOG.warn("type        : " + ex.getClass().getName());
            LOG.warn("exception   : " + ex.getMessage());
            LOG.info("######################################end##############################################");
        };
    }
}
