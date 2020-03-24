package com.guiPalma.apivotacao;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.guiPalma.apivotacao.endpoint.service.MonitoramentoService;
@SpringBootApplication
@EnableAsync
public class ApiVotacaoApplication {

	@Autowired
	private static MonitoramentoService monitoramento;
	
	public static void main(String[] args) {
		monitoramento.monitorarSessaoVotacao();
		SpringApplication.run(ApiVotacaoApplication.class, args);
	}
	
    @Bean(name = "monitoramentoSessao")
    public Executor asyncExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(3);        
        executor.initialize();
        return executor;
    }

}
