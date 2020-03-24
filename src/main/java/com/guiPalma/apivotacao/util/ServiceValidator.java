package com.guiPalma.apivotacao.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.guiPalma.apivotacao.endpoint.consumer.CpfResponse;
import com.guiPalma.apivotacao.endpoint.consumer.CpfServiceConsumer;
import com.guiPalma.apivotacao.exceptions.ObjectNotFoundException;
import com.guiPalma.apivotacao.exceptions.ServiceErrorException;
import com.guiPalma.apivotacao.model.Associado;
import com.guiPalma.apivotacao.model.Pauta;
import com.guiPalma.apivotacao.repository.SessaoVotacaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceValidator {

	private static final String STATUS_PODE_VOTAR = "ABLE_TO_VOTE";
	private final SessaoVotacaoRepository sessaoVotacaoRepository;
	private final CpfServiceConsumer cpfServiceConsumer;

	public boolean associadoPodeVotar(Pauta pauta, Associado associado) {
		return !pauta.getVotos().stream().filter(voto -> voto.getAssociado().getCpf().equals(associado.getCpf()))
				.findAny().isPresent();
	}

	private Boolean isCpfValido(String cpf) {
		CpfResponse response = null;
		try {
			response = cpfServiceConsumer.validarCpf(cpf);
		} catch (Exception e) {
			return false;
		}
		return ApiValidator.has(response) && response.getStatus().equalsIgnoreCase(STATUS_PODE_VOTAR);
	}

	public boolean validarPauta(Pauta pauta) {
		if (ApiValidator.has(pauta)) {
			if (existeSessaoVinculada(pauta)) {
				if (existeSessaoAtivaParaPauta(pauta)) {
					return true;
				}
				throw new ServiceErrorException("Sessão de votaçao não esta ativa");
			}
			throw new ServiceErrorException("Pauta não vinculada a nenhuma sessão de votação");
		}
		throw new ObjectNotFoundException("Pauta não encontrada");

	}

	public Boolean existeSessaoAtivaParaPauta(Pauta pauta) {
		var votacao = sessaoVotacaoRepository.findByPauta(pauta);

		if (ApiValidator.has(votacao)) {
			return votacao.getAtiva();
		}
		return false;
	}

	public boolean existeSessaoVinculada(Pauta pautaEncontrada) {
		return sessaoVotacaoRepository.countByPauta(pautaEncontrada) > 0;
	}

	public boolean validarAssociado(Associado associado, Pauta pauta) {
		if(ApiValidator.has(associado)) {
			if(isCpfValido(associado.getCpf())) {
				if(associadoPodeVotar(pauta,associado)) {
					return true;
				}throw new ServiceErrorException("Associado já votou nessa pauta");
			}throw new ServiceErrorException("CPF do associado é inválido");
		}throw new ObjectNotFoundException("Associado não encontrado");		
	}

}
