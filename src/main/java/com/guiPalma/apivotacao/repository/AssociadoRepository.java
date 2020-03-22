package com.guiPalma.apivotacao.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.guiPalma.apivotacao.model.Associado;

public interface AssociadoRepository extends PagingAndSortingRepository<Associado, Long> {
	
	public Associado findByCpf(String cpf);
	public Associado findByNome(String nome);

}
