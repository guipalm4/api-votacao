package com.guiPalma.apivotacao.dto;

import com.guiPalma.apivotacao.endpoint.service.validation.SessaoVotacaoInsert;

import io.swagger.annotations.ApiModelProperty;
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
	
	@ApiModelProperty(value = "Id da sessão")
	private Long id;
	@ApiModelProperty(value = "Descrição da sessão")
	private String descricao;
	@ApiModelProperty(value = "Pauta da sessão")
	private PautaDto pauta;
	@ApiModelProperty(value = "Duração da sessão")
	private Integer duracao;

}
