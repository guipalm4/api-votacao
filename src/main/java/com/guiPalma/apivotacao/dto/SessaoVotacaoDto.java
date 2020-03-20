package com.guiPalma.apivotacao.dto;

import com.guiPalma.apivotacao.model.Pauta;

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
public class SessaoVotacaoDto {
	
	private Long id;
	private String descricao;
	private PautaDto pauta;

}
