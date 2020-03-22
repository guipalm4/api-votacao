package com.guiPalma.apivotacao.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

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
	
	@OneToMany	
	@JoinColumn(name = "pauta_id")
	private List<SessaoVotacao>	sessoes;
	
	@Override
	public Long getId() {
		return this.id;
	}
	
	public static Pauta fromDto(PautaDto dto) {
		return Pauta.builder().id(dto.getId()).descricao(dto.getDescricao()).build();
	}

}
