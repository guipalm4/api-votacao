package com.guiPalma.apivotacao.dto;

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
public class ResultadoDto {
	
	private SessaoVotacaoDto sessao;
	private PautaDto pauta;
	private Long sim;
	private Long nao;
	private String resultado;

}
