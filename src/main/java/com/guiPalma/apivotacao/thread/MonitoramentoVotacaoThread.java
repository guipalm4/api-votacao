package com.guiPalma.apivotacao.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.guiPalma.apivotacao.endpoint.service.MonitoramentoService;
@Component
@Scope("prototype")
public class MonitoramentoVotacaoThread implements Runnable {
		
	private  MonitoramentoService monitoramentoService;	
	private String nome;
	
	public void setNome(String nome) {
		this.nome=nome;
	}
	
	public MonitoramentoVotacaoThread(MonitoramentoService m) {
		this.monitoramentoService = m;
	}

	@Override
	public void run() {		
		try {
			while(true) {				
				monitoramentoService.monitorarSessaoVotacao();
				Thread.sleep(30000);
			}			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
