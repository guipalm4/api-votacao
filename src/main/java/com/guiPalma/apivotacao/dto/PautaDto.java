package com.guiPalma.apivotacao.dto;

import com.guiPalma.apivotacao.endpoint.service.validation.PautaInsert;

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
@PautaInsert
public class PautaDto {
	
	@ApiModelProperty(value = "Id da pauta")
	private Long id;
	@ApiModelProperty(value = "Descricao da pauta")
	private String descricao;
	
}
