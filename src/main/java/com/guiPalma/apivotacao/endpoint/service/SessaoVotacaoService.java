package com.guiPalma.apivotacao.endpoint.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.guiPalma.apivotacao.dto.PautaDto;
import com.guiPalma.apivotacao.dto.ResultadoDto;
import com.guiPalma.apivotacao.dto.SessaoVotacaoDto;
import com.guiPalma.apivotacao.dto.VotoDto;
import com.guiPalma.apivotacao.exceptions.ObjectNotFoundException;
import com.guiPalma.apivotacao.exceptions.ServiceErrorException;
import com.guiPalma.apivotacao.model.Associado;
import com.guiPalma.apivotacao.model.Pauta;
import com.guiPalma.apivotacao.model.SessaoVotacao;
import com.guiPalma.apivotacao.model.Voto;
import com.guiPalma.apivotacao.repository.AssociadoRepository;
import com.guiPalma.apivotacao.repository.PautaRepository;
import com.guiPalma.apivotacao.repository.SessaoVotacaoRepository;
import com.guiPalma.apivotacao.repository.VotoRepository;
import com.guiPalma.apivotacao.util.ApiValidator;
import com.guiPalma.apivotacao.util.ServiceValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SessaoVotacaoService {
	
private final SessaoVotacaoRepository sessaoVotacaoRepository;
private final PautaRepository pautaRepository;
private final AssociadoRepository associadoRepository;
private final VotoRepository votoRepository;

private final ServiceValidator serviceValidator;
	
	public Page<SessaoVotacao> list(Pageable pageable) {
		return sessaoVotacaoRepository.findAll(pageable);
	}
	
	private final static Integer DURACAO_DEFAULT = 1;

	public SessaoVotacao criarSessaoVotacao(SessaoVotacaoDto sessao) {
		
		Pauta pautaEncontrada = localizarPauta(sessao.getPauta());
		
		if(ApiValidator.has(pautaEncontrada)) {
			if(! serviceValidator.existeSessaoVinculada(pautaEncontrada)) {			
				return sessaoVotacaoRepository.save(setParams(sessao,pautaEncontrada));
			}throw new ServiceErrorException("Pauta ja está sendo utilizada em outra sessão de votação");
		}throw new ObjectNotFoundException("Pauta não encontrada");
	}
	
	public Voto votar(VotoDto votoDto) {
		var pauta = localizarPauta(votoDto.getPauta());			
		if(serviceValidator.validarPauta(pauta)) {
			var associado  =  associadoRepository.findByCpf(votoDto.getCpfAssociado());
			if(serviceValidator.validarAssociado(associado, pauta)) {
				var voto = votoRepository.save(setParams(votoDto, associado, pauta));
				pauta.getVotos().add(voto);
				pautaRepository.save(pauta);
				return voto;
			}
		}
		return null;
	}	
	private Voto setParams(VotoDto votoDto, Associado associado, Pauta pauta) {		
		return Voto.builder()
				.id(null)
				.associado(associado)
				.pauta(pauta)
				.voto((votoDto.getVoto().startsWith("S") || votoDto.getVoto().startsWith("s")) ? 'S' : 'N')
				.build();
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

	public ResultadoDto apurarResultado(Long idSessaoVotacao) {
		
		Optional<SessaoVotacao> sessaoEncontrada = sessaoVotacaoRepository.findById(idSessaoVotacao);
		
		if(sessaoEncontrada.isPresent()) {
			var sessao = sessaoEncontrada.get();					
			if(sessao.getAtiva()) {
				bloquearSessaoVotacao(sessao);				
				if(ApiValidator.has(sessao.getPauta())) {
					var pauta = sessao.getPauta();
					ResultadoDto resultadoVotacao = gerarResultado(pauta,sessao);
					sessao.setResultadoVotacao(resultadoVotacao.getResultado());
					sessaoVotacaoRepository.save(sessao);
					return resultadoVotacao;					
				}throw new ServiceErrorException("Não há nenhuma pauta vinculada a essa sessão de votação");
			}throw new ServiceErrorException("Sessao de votacão já encerrada");
		}throw new ObjectNotFoundException("Sessão de votação não localizada");
	}

	private ResultadoDto gerarResultado(Pauta pauta, SessaoVotacao sessao) {
		
		Long sim = contabilizarVotos(pauta ,'S');
		Long nao = contabilizarVotos(pauta, 'N');
		
		return ResultadoDto.builder()
										.sim(sim)
										.nao(nao)
										.sessao(SessaoVotacaoDto.builder().id(sessao.getId()).descricao(sessao.getDescricao()).build())
										.pauta(PautaDto.builder().id(pauta.getId()).descricao(pauta.getDescricao()).build())										
										.resultado(sim > nao ? "Sim" : "Não")
										.build();
	}

	private Long contabilizarVotos(Pauta pauta, char c) 
	{
		return pauta.getVotos().stream().filter(voto -> voto.getVoto() ==c).count();
	}

	private void bloquearSessaoVotacao(SessaoVotacao sessao) {
		sessao.setAtiva(Boolean.FALSE);
		sessaoVotacaoRepository.save(sessao);
	}
}