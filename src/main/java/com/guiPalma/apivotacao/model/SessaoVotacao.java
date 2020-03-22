package com.guiPalma.apivotacao.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guiPalma.apivotacao.dto.SessaoVotacaoDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SessaoVotacao implements AbstractEntity {

	private static final long serialVersionUID = 5887605143221475221L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;	
	
	@NotNull(message = "O campo 'descricao' é obrigatório")
	@Column(nullable = false)
	private String descricao;	
	
	@NotNull
	@ManyToOne
	@JsonIgnore
    @JoinColumn(name = "pauta_id")
	private Pauta pauta;
	
	@Column(nullable = false)
	private Integer duracao;
	
	@Column(nullable = false)
	private Calendar dataCricao;
	
	private Boolean ativa;
	
	@Override
	public Long getId() {
		return this.id;
	}
	
	public static SessaoVotacao fromDto(SessaoVotacaoDto dto) {
		return SessaoVotacao.builder().id(dto.getId())
				.descricao(dto.getDescricao())
				.pauta(Pauta.fromDto(dto.getPauta()))
				.build();
	}

}
