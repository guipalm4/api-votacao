package com.guiPalma.apivotacao.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.guiPalma.apivotacao.model.Pauta;

public interface PautaRepository extends PagingAndSortingRepository<Pauta, Long> {
	
	public Pauta findByDescricao(String desc);

}
