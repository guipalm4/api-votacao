package com.guiPalma.apivotacao.endpoint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.guiPalma.apivotacao.dto.PautaDto;
import com.guiPalma.apivotacao.exceptions.ServiceErrorException;
import com.guiPalma.apivotacao.model.Pauta;
import com.guiPalma.apivotacao.repository.PautaRepository;
import com.guiPalma.apivotacao.util.ApiValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PautaService {
	
	private final PautaRepository pautaRepository;
	
	public Page<Pauta> list(Pageable pageable) {
		return pautaRepository.findAll(pageable);
	}

	public Pauta cadastrar(PautaDto pauta) {
				
		Pauta pautaEncontrada = pautaRepository.findByDescricao(pauta.getDescricao());
			
		if(ApiValidator.has(pautaEncontrada)) {
			throw new ServiceErrorException("Pauta Ja cadastrada." );
		}
		return pautaRepository.save(setParams(pauta));
	}

	private Pauta setParams(PautaDto pauta) {
		return Pauta.fromDto(pauta);
	}

}
