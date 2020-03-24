package com.guiPalma.apivotacao.endpoint.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiPalma.apivotacao.dto.PautaDto;
import com.guiPalma.apivotacao.endpoint.service.PautaService;
import com.guiPalma.apivotacao.model.Pauta;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("v1/admin/pauta")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PautaController {
	
	private final PautaService pautaService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<Pauta>> list(Pageable pageable) {
		return new ResponseEntity<>(pautaService.list(pageable), HttpStatus.OK);
	}
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pauta> cadastrar(@Valid @RequestBody PautaDto pauta){
		return new ResponseEntity<>(pautaService.cadastrar(pauta), HttpStatus.CREATED);	
	}

}
