package com.guiPalma.apivotacao.dto;

import java.util.Calendar;

import com.guiPalma.apivotacao.endpoint.service.validation.SessaoVotacaoInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SessaoVotacaoInsert
public class SessaoVotacaoDto {
	
	private Long id;
	private String descricao;
	private PautaDto pauta;
	private Integer duracao;
	private Calendar dataCriacao;
	private Boolean ativa;
}
