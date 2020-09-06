package com.springboot.completablefuture;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;

@Component
@Configuration
public class ConfigurationApp extends AsyncSupportConfigurer {

    @Bean("executortest")
    public TaskExecutor getExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(10);
        executor.setDaemon(true);
        executor.setThreadNamePrefix("Test-");
        executor.setDaemon(true);
        executor.afterPropertiesSet();
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.shutdown();
        return executor;

    }
}
