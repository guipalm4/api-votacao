package com.guiPalma.apivotacao.endpoint.service;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.guiPalma.apivotacao.model.SessaoVotacao;
import com.guiPalma.apivotacao.repository.SessaoVotacaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MonitoramentoService {
	
	private final SessaoVotacaoRepository sessaoRepository;
	private final SessaoVotacaoService sessaoService;

	@Async("monitoramentoSessao")
    public  void monitorarSessaoVotacao() {		
		System.out.println("Motitoramento sendo executado...");
		while(true) {
			List<SessaoVotacao> sessoesAtivas = sessaoRepository.findByAtiva(Boolean.TRUE);		
			processarVotacoes(sessoesAtivas);
		}
	}

	private void processarVotacoes(List<SessaoVotacao> sessoesAtivas) {
		var listaVotacoesEncerradas = sessoesAtivas.stream().map(sessao->{
			if(isSessaoVencida(sessao)) {
				sessaoService.apurarResultado(sessao.getId());
			}
			return sessao;
		}) .collect(Collectors.toList());		
		comunicarResultado(listaVotacoesEncerradas);
		}
private void comunicarResultado(List<SessaoVotacao> listaVotacoesEncerradas) {
		// TODO Mensageria		
	}

private boolean isSessaoVencida(SessaoVotacao sessao) {
	Calendar inicio = sessao.getDataCricao();
	inicio.add(Calendar.MINUTE, sessao.getDuracao());
	Calendar expiracao = inicio;	
	return expiracao.compareTo(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"))) < 0 ;
}

}

