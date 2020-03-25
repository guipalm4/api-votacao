package com.guiPalma.apivotacao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.guiPalma.apivotacao.endpoint.service.MonitoramentoService;
import com.guiPalma.apivotacao.thread.MonitoramentoVotacaoThread;

//@Configuration
public class AppThreadConfiguration {		

	//@Autowired
	private MonitoramentoService monitoramento;
	
	//@Bean
	public boolean instantiateMonitoramento() {
		monitoramento.monitorarSessaoVotacao();	
	    return true;
	}	
//	@PostConstruct
//    public void init() {
//		ApplicationContext context = new ClassPathXmlApplicationContext(
//				"Thread-config.xml");
//		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context
//				.getBean("taskExecutor");		
//		
//	    MonitoramentoVotacaoThread  monitoramentoTask1 = (MonitoramentoVotacaoThread) context.getBean("monitoramentoVotacaoThread");
////	    context.getAutowireCapableBeanFactory().autowireBean(monitoramentoTask1);
////       context .getAutowireCapableBeanFactory().initializeBean(monitoramentoTask1, null);
//	    monitoramentoTask1.setNome("Thread 1");
//	    taskExecutor.execute(monitoramentoTask1);		
//    }	
}
