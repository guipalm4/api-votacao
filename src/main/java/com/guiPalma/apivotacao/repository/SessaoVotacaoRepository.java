package com.guiPalma.apivotacao.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.guiPalma.apivotacao.model.Pauta;
import com.guiPalma.apivotacao.model.SessaoVotacao;

public interface SessaoVotacaoRepository extends PagingAndSortingRepository<SessaoVotacao, Long>{
	
	public SessaoVotacao findByPauta(Pauta pauta);
	public Long countByPauta(Pauta pauta);
}
