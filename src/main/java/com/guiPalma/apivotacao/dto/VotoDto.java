package com.guiPalma.apivotacao.dto;

import java.util.Calendar;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import com.guiPalma.apivotacao.model.Associado;
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
public class VotoDto {

	private Long id;
	private String cpfAssociado;
	private String voto;	
	private PautaDto pauta;
}
