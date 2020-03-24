package com.guiPalma.apivotacao.endpoint.consumer;

import com.guiPalma.apivotacao.dto.PautaDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CpfResponse {
	
	private String status;
	

}
