package com.guiPalma.apivotacao.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	private Long id;
	
	@NotNull(message = "O campo 'descricao' é obrigatório")
	@Column(nullable = false)
	private String descricao;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "pauta_voto",
		joinColumns = @JoinColumn(name = "pauta_id"),
		inverseJoinColumns = @JoinColumn(name = "voto_id")
	)
	private List<Voto> votos;
	
	@Override
	public Long getId() {
		return this.id;
	}
	
	public static Pauta fromDto(PautaDto dto) {
		return Pauta.builder().id(dto.getId()).descricao(dto.getDescricao()).build();
	}

}
