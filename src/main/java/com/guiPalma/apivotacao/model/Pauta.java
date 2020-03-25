package com.guiPalma.apivotacao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guiPalma.apivotacao.dto.PautaDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "pauta")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pauta implements AbstractEntity{
	private static final long serialVersionUID = -1329846082700053690L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "id")
	private Long id;
	
	@NotNull(message = "O campo 'descricao' é obrigatório")
	@Column(nullable = false)
	private String descricao;
	
	@JsonIgnore
	 @OneToOne(mappedBy = "pauta")
	private SessaoVotacao sessao;	
	
	@Override
	public Long getId() {
		return this.id;
	}
	
	public static Pauta fromDto(PautaDto dto) {
		return Pauta.builder().id(dto.getId()).descricao(dto.getDescricao()).build();
	}

}
