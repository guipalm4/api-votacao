package com.guiPalma.apivotacao.endpoint.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.guiPalma.apivotacao.dto.PautaDto;
import com.guiPalma.apivotacao.dto.ResultadoDto;
import com.guiPalma.apivotacao.dto.SessaoVotacaoDto;
import com.guiPalma.apivotacao.endpoint.mensageria.NotificadorResultado;
import com.guiPalma.apivotacao.model.SessaoVotacao;
import com.guiPalma.apivotacao.repository.SessaoVotacaoRepository;
import com.guiPalma.apivotacao.util.ApiValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MonitoramentoService {
	
	private final SessaoVotacaoRepository sessaoRepository;
	private final SessaoVotacaoService sessaoService;
	private final NotificadorResultado notificador;

	@Async("monitoramento")
    public  void monitorarSessaoVotacao() {		
		System.out.println("Monitoramento sendo executado...");		
		boolean temVotacoesAtivas = true;
		while(temVotacoesAtivas) {
			List<SessaoVotacao> sessoesAtivas = sessaoRepository.findByAtiva(Boolean.TRUE);		
			if(sessoesAtivas.size() > 0) {			
				processarVotacoes(sessoesAtivas);
			}
			temVotacoesAtivas = sessaoRepository.countByAtiva(Boolean.TRUE) >0L;
		}
		
	}
	private void processarVotacoes(List<SessaoVotacao> sessoesAtivas) {
		 sessoesAtivas.stream().map(sessao->{
			if(isSessaoVencida(sessao)) {
				var resultado = sessaoService.apurarResultado(sessao.getId());
				if(ApiValidator.has(resultado)) {
					comunicarResultado(resultado);
				}
			}
			return sessao;
		}) .collect(Collectors.toList());		
		}
private void comunicarResultado(ResultadoDto resultado) {	
	notificador.send(resultado);	
	}

private boolean isSessaoVencida(SessaoVotacao sessao) {		
	Date  inicio = sessao.getDataCricao();	
	Calendar calendar = Calendar.getInstance();	
	calendar.setTime(inicio);
	calendar.add(Calendar.MINUTE, sessao.getDuracao());	
	Date expiracao = calendar.getTime();		
	return expiracao.compareTo(Calendar.getInstance().getTime()) < 0 ;
}

}

