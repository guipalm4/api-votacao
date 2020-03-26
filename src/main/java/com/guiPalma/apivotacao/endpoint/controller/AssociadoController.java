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

import com.guiPalma.apivotacao.dto.AssociadoDto;
import com.guiPalma.apivotacao.endpoint.service.AssociadoService;
import com.guiPalma.apivotacao.model.Associado;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("v1/admin/associado")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "Endpoints para cadastro e consulta de associados")
public class AssociadoController {
	
	private final AssociadoService associadoService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Lista todos os associados  cadastrados", response = Associado[].class)
	public ResponseEntity<Iterable<Associado>> list(Pageable pageable) {
		return new ResponseEntity<>(associadoService.list(pageable), HttpStatus.OK);
	}
	@ApiOperation(value = "Cadastra um associado", response = Associado.class)
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Associado> cadastrar(@Valid @RequestBody AssociadoDto associado){
		return new ResponseEntity<>(associadoService.cadastrar(associado), HttpStatus.CREATED);	
	}

}
