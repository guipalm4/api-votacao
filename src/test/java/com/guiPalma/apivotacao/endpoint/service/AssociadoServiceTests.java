package com.guiPalma.apivotacao.endpoint.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.guiPalma.apivotacao.dto.AssociadoDto;
import com.guiPalma.apivotacao.exceptions.ServiceErrorException;
import com.guiPalma.apivotacao.model.Associado;
import com.guiPalma.apivotacao.repository.AssociadoRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AssociadoServiceTests {

	@Autowired
	private AssociadoService service;

	@MockBean
	private AssociadoRepository repository;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	private Long idAssociado;
	private String nome;
	private String cpf;

	@Before
	public void setUp() {
		this.nome = "NOME TESTE";
		this.idAssociado = 1236L;
		this.cpf = "12345678901";
	}

	@Test
	public void deve_cadastrar_um_novo_associado() {

		var dto = AssociadoDto.builder().cpf(cpf).nome(nome).build();
		var mockAssociado = Associado.builder().id(idAssociado).nome(nome).cpf(cpf).build();
		
		BDDMockito.given(repository.findByCpf(Mockito.anyString())).willReturn(null);
		BDDMockito.given(repository.save(Mockito.any(Associado.class))).willReturn(mockAssociado);

		var result = service.cadastrar(dto);

		verify(repository, times(1)).findByCpf(Mockito.anyString());
		verify(repository, times(1)).save(Mockito.any(Associado.class));
		assertNotNull(result);
		assertNotNull(result.getId());
		assertEquals(result.getNome(), dto.getNome());
		assertEquals(result.getCpf(), dto.getCpf());

	}
	
	@Test
	public void deve_lancar_excecao_pauta_ja_cadastrada() {
		
		var dto = AssociadoDto.builder().cpf(cpf).nome(nome).build();
		var mockAssociado = Associado.builder().id(idAssociado).nome(nome).cpf(cpf).build();
		
		BDDMockito.given(repository.findByCpf(Mockito.anyString())).willReturn(mockAssociado);
		
		exceptionRule.expect(ServiceErrorException.class);
	    exceptionRule.expectMessage("Associado já cadastrado.");
	    
	    service.cadastrar(dto);
	    
	    verify(repository, times(1)).findByCpf(Mockito.anyString());
	    verify(repository,  never()). save(Mockito.any(Associado.class));
	}

}
