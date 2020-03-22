package com.guiPalma.apivotacao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.guiPalma.apivotacao.dto.AssociadoDto;

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
public class Associado implements AbstractEntity{
	
	private static final long serialVersionUID = 4294365970258716275L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotNull(message = "O campo 'descricao' é obrigatório")
	@Column(nullable = false)
	private String nome;
	
	@NotNull(message = "O campo 'cpf' é obrigatório")
	@Column(nullable = false)
	private String cpf;
	
	@Override
	public Long getId() {
		return this.id;
	}
	
	public static Associado fromDto(AssociadoDto dto) {
		return Associado.builder().id(dto.getId())
				.nome(dto.getNome())
				.cpf(dto.getCpf())
				.build();
	}

}
