package com.guiPalma.apivotacao.endpoint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.guiPalma.apivotacao.model.SessaoVotacao;
import com.guiPalma.apivotacao.repository.SessaoVotacaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SessaoVotacaoService {
	
private final SessaoVotacaoRepository sessaoVotacaoRepository;
	
	public Page<SessaoVotacao> list(Pageable pageable) {
		return sessaoVotacaoRepository.findAll(pageable);
	}

}
