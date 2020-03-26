package com.guiPalma.apivotacao.endpoint.mensageria;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class MensagemResultado  implements Serializable {
	
	private static final long serialVersionUID = 4983191562997173296L;
	private String sessao;
	private String pauta;	
	private Long sim;
	private Long nao;
	private String resultado;
	
	public MensagemResultado(@JsonProperty("sessao") String sessao,
																@JsonProperty("pauta") String pauta,
																@JsonProperty("sim") Long sim,
																@JsonProperty("nao") Long nao,
																@JsonProperty("resultado") String resultado) {
	
		this.sessao = sessao;
		this.pauta = pauta;
		this.sim = sim;
		this.nao = nao;
		this.resultado = resultado;
	}
	
	
	}


