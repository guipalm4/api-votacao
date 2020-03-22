package com.guiPalma.apivotacao.dto;

import com.guiPalma.apivotacao.endpoint.service.validation.PautaInsert;

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

	private Long id;
	private String descricao;
	
}
