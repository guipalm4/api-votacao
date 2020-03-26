package com.guiPalma.apivotacao.dto;

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
public class AssociadoDto {
	@ApiModelProperty(value = "Id do associado")
	private Long id;	
	@ApiModelProperty(value = "Cpf  do associado [Apenas números]")
	private String cpf;
	@ApiModelProperty(value = "Nome do associado")
	private String nome;

}
