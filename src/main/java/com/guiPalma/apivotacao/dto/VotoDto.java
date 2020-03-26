package com.guiPalma.apivotacao.dto;

import java.util.Calendar;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import com.guiPalma.apivotacao.model.Associado;
import com.guiPalma.apivotacao.model.Pauta;

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
public class VotoDto {
	
	@ApiModelProperty(value = "Id do voto")
	private Long id;
	
	@ApiModelProperty(value = "Cpf  do associado [Apenas números]")
	private String cpfAssociado;
	@ApiModelProperty(value = "Definição do voto. Formato: [Ss]im ou [Nn][aã]o")
	private String voto;	
	
	@ApiModelProperty(value = "Definição da pauta. Informar ID ou descrição")
	private PautaDto pauta;
}
