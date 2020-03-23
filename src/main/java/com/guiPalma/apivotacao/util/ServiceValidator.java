package com.guiPalma.apivotacao.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.guiPalma.apivotacao.exceptions.ObjectNotFoundException;
import com.guiPalma.apivotacao.exceptions.ServiceErrorException;
import com.guiPalma.apivotacao.model.Associado;
import com.guiPalma.apivotacao.model.Pauta;
import com.guiPalma.apivotacao.repository.SessaoVotacaoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceValidator {
	
	private final SessaoVotacaoRepository sessaoVotacaoRepository;
	
	public  boolean associadoPodeVotar(Pauta pauta, Associado associado) {
		return ! pauta.getVotos().stream().filter(voto-> voto.getAssociado().getCpf().equals(associado.getCpf())).findAny().isPresent();
	}	
	public  boolean validarPauta(Pauta pauta) {
		if(ApiValidator.has(pauta)) {
			if(existeSessaoVinculada(pauta)){ 
					if(existeSessaoAtivaParaPauta(pauta)) {
						return true;						
					}throw new ServiceErrorException("Sessão de votaçao não esta ativa");
			}throw new ServiceErrorException("Pauta não vinculada a nenhuma sessão de votação");		
		}throw new ObjectNotFoundException("Pauta não encontrada");
		
	}
		
	public  Boolean existeSessaoAtivaParaPauta(Pauta pauta) {		
		var votacao = sessaoVotacaoRepository.findByPauta(pauta);
		
		if(ApiValidator.has(votacao)) {
			return votacao.getAtiva();
		}
		return false;
	}
	
	public  boolean existeSessaoVinculada(Pauta pautaEncontrada) {
		return sessaoVotacaoRepository.countByPauta(pautaEncontrada) > 0;
	}

}
