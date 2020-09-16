package com.management.picture.config;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;

/**
 * Created on: 2020/9/15
 *
 * @author: Yue Wu
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);

    /**
     * 核心线程数
     */
//    private int corePoolSize = Runtime.getRuntime().availableProcessors();
    private int corePoolSize = 5;
    /**
     * 最大线程数
     */
    private int maxPoolSize = Integer.MAX_VALUE;
    /**
     * 线程销毁时间
     */
    private Long keepAliveTime = 60L;
    /**
     * 任务队列
     */
    private SynchronousQueue synchronousQueue = new SynchronousQueue<>();
    /**
     * 线程名称
     */
    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("datas-thread-%d").build();

    @Bean
    @Scope("prototype")
    public ExecutorService createThreadPool() {
        logger.info("线程池创建===>开始");
        /**
         * 创建线程池
         * Runtime.getRuntime().availableProcessors()
         */
        ExecutorService threadPool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, synchronousQueue, namedThreadFactory);
        logger.info("线程池创建===>结束");
        return threadPool;
    }
}
