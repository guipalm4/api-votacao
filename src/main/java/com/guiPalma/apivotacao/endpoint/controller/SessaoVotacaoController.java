package com.guiPalma.apivotacao.endpoint.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiPalma.apivotacao.dto.ResultadoDto;
import com.guiPalma.apivotacao.dto.SessaoVotacaoDto;
import com.guiPalma.apivotacao.dto.VotoDto;
import com.guiPalma.apivotacao.endpoint.service.SessaoVotacaoService;
import com.guiPalma.apivotacao.model.SessaoVotacao;
import com.guiPalma.apivotacao.model.Voto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("v1/admin/votacao")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SessaoVotacaoController {
	
	private final SessaoVotacaoService sessaoVotacaoService; 
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Iterable<SessaoVotacao>> list(Pageable pageable) {
		return new ResponseEntity<>(sessaoVotacaoService.list(pageable), HttpStatus.OK);
	}
	@PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<SessaoVotacao> criar(@Valid @RequestBody SessaoVotacaoDto sessao){
		return new ResponseEntity<>(sessaoVotacaoService.criarSessaoVotacao(sessao), HttpStatus.CREATED);
	}
	@PostMapping(path = "/votar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Voto> criar(@Valid @RequestBody VotoDto voto){
		return new ResponseEntity<>(sessaoVotacaoService.votar(voto), HttpStatus.CREATED);
	}
	@GetMapping("/{idSessao}/resultado")
    public ResponseEntity<ResultadoDto> apurarResultado(@PathVariable("idSessao") Long idSessaoVotacao) {
		return new ResponseEntity<>(sessaoVotacaoService.apurarResultado(idSessaoVotacao), HttpStatus.OK);
	}
}
