package com.guiPalma.apivotacao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

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
public class Voto implements AbstractEntity{
	
	private static final long serialVersionUID = -9131178730270490587L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotNull(message = "O campo 'associado' é obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_associado")
	private Associado associado;
	
	@NotNull(message = "O voto é obrigatório")
	@Column(nullable = false)	
	private char voto;
	
	@ManyToOne
	@JoinColumn(name = "id_pauta")
	private Pauta pauta;
	
	@Override
	public Long getId() {
		return this.id;
	}	
	
	public Long getIdPauta() {
		return this.pauta.getId();
	}
	

}
