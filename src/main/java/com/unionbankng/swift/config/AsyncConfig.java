package com.unionbankng.swift.config;


import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @org.springframework.context.annotation.Bean
    @Override
    public java.util.concurrent.Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor tPoolExec = new ThreadPoolTaskExecutor();
        tPoolExec.setWaitForTasksToCompleteOnShutdown(false);
        tPoolExec.setMaxPoolSize(15);
        tPoolExec.setCorePoolSize(10);
        tPoolExec.setThreadNamePrefix("ubn_async_thread");
        tPoolExec.setQueueCapacity(50);
        tPoolExec.initialize();
        return tPoolExec;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
