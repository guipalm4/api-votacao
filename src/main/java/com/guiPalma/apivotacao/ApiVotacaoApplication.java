package com.guiPalma.apivotacao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import com.guiPalma.apivotacao.endpoint.service.MonitoramentoService;
import com.guiPalma.apivotacao.thread.MonitoramentoVotacaoThread;
@SpringBootApplication
@EnableAsync
public class ApiVotacaoApplication  implements ApplicationRunner {
	
	@Autowired
	MonitoramentoService monitoramentoService;
	
	public static void main(String[] args) {		
		SpringApplication.run(ApiVotacaoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(new MonitoramentoVotacaoThread(monitoramentoService));
		
	}		

}
