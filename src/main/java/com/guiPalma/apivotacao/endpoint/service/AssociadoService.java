package com.guiPalma.apivotacao.endpoint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.guiPalma.apivotacao.dto.AssociadoDto;
import com.guiPalma.apivotacao.exceptions.ServiceErrorException;
import com.guiPalma.apivotacao.model.Associado;
import com.guiPalma.apivotacao.model.Pauta;
import com.guiPalma.apivotacao.repository.AssociadoRepository;
import com.guiPalma.apivotacao.util.ApiValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AssociadoService {
	
	private final AssociadoRepository associadoRepository;
	
	public Page<Associado> list(Pageable pageable) {
		return associadoRepository.findAll(pageable);
	}

	public Associado cadastrar(AssociadoDto associado) {
				
		var associadoEncontrado = associadoRepository.findByCpf(associado.getCpf());
			
		if(ApiValidator.has(associadoEncontrado)) {
			throw new ServiceErrorException("Associado já cadastrado." );
		}
		return associadoRepository.save(setParams(associado));
	}

	private Associado setParams(AssociadoDto associado) {
		return Associado.fromDto(associado);
	}

}
