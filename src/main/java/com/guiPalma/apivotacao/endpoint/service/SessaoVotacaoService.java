package com.guiPalma.apivotacao.endpoint.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.guiPalma.apivotacao.dto.PautaDto;
import com.guiPalma.apivotacao.dto.SessaoVotacaoDto;
import com.guiPalma.apivotacao.exceptions.ObjectNotFoundException;
import com.guiPalma.apivotacao.model.Pauta;
import com.guiPalma.apivotacao.model.SessaoVotacao;
import com.guiPalma.apivotacao.repository.PautaRepository;
import com.guiPalma.apivotacao.repository.SessaoVotacaoRepository;
import com.guiPalma.apivotacao.util.ApiValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SessaoVotacaoService {
	
private final SessaoVotacaoRepository sessaoVotacaoRepository;
private final PautaRepository pautaRepository;
	
	public Page<SessaoVotacao> list(Pageable pageable) {
		return sessaoVotacaoRepository.findAll(pageable);
	}
	
	private final static Integer DURACAO_DEFAULT = 1;

	public SessaoVotacao criarSessaoVotacao(SessaoVotacaoDto sessao) {
		
		Pauta pautaEncontrada = localizarPauta(sessao.getPauta());
		
		if(ApiValidator.has(pautaEncontrada)) {
			return sessaoVotacaoRepository.save(setParams(sessao,pautaEncontrada));
		}throw new ObjectNotFoundException("Pauta não encontrada");
		
	}

	private SessaoVotacao setParams(SessaoVotacaoDto sessao, Pauta pauta) {
		
		return SessaoVotacao.builder()
				.id(null)
				.descricao(sessao.getDescricao())
				.pauta(pauta)
				.duracao(ApiValidator.has(sessao.getDuracao()) ? sessao.getDuracao() : DURACAO_DEFAULT)
				.dataCricao(configurarData())
				.ativa(Boolean.TRUE)
				.build();
	}

	private Calendar configurarData() {
		
		return Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));		
	}

	private Pauta localizarPauta(PautaDto pauta) {
		
		if (ApiValidator.has(pauta.getId())) {
			var pautaEncontrada = pautaRepository.findById(pauta.getId());			
			if(pautaEncontrada.isPresent()) {
				return pautaEncontrada.get(); 
			}
		}
		if (ApiValidator.has(pauta.getDescricao())){
			return pautaRepository.findByDescricao(pauta.getDescricao());
		}
		return null;		
	}
}