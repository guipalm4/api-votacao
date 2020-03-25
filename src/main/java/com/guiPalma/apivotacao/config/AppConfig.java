package com.guiPalma.apivotacao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
//@ComponentScan(basePackages = "com.guiPalma.apivotacao.thread")
@EnableAsync
public class AppConfig {
	
		@Bean(name = "monitoramento")
	    public ThreadPoolTaskExecutor  taskExecutor() {
	        final ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
	        pool.setCorePoolSize(1);
	        pool.setMaxPoolSize(3);        
	        pool.setWaitForTasksToCompleteOnShutdown(true);
	        return pool;
	    }
}
