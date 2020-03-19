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

import com.guiPalma.apivotacao.dto.PautaDto;
import com.guiPalma.apivotacao.exceptions.ServiceErrorException;
import com.guiPalma.apivotacao.model.Pauta;
import com.guiPalma.apivotacao.repository.PautaRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PautaServiceTests {

	@Autowired
	private PautaService service;

	@MockBean
	private PautaRepository repository;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	private Long idPauta;
	private String descricao;

	@Before
	public void setUp() {
		this.descricao = "NOME TESTE";
		this.idPauta = 1236L;
	}

	@Test
	public void deve_cadastrar_uma_nova_pauta() {

		var dto = PautaDto.builder().descricao(descricao).build();
		var mockPauta = Pauta.builder().id(idPauta).descricao(descricao).build();

		BDDMockito.given(repository.findByDescricao(Mockito.anyString())).willReturn(null);
		BDDMockito.given(repository.save(Mockito.any(Pauta.class))).willReturn(mockPauta);

		var result = service.cadastrar(dto);

		verify(repository, times(1)).findByDescricao(Mockito.anyString());
		verify(repository, times(1)).save(Mockito.any(Pauta.class));
		assertNotNull(result);
		assertNotNull(result.getId());
		assertEquals(result.getDescricao(), dto.getDescricao());

	}
	
	@Test
	public void deve_lancar_excecao_pauta_ja_cadastrada() {
		
		var dto = PautaDto.builder().descricao(descricao).build();
		var mockPauta = Pauta.builder().id(idPauta).descricao(descricao).build();
		
		BDDMockito.given(repository.findByDescricao(Mockito.anyString())).willReturn(mockPauta);
		
		exceptionRule.expect(ServiceErrorException.class);
	    exceptionRule.expectMessage("Pauta Ja cadastrada.");
	    
	    service.cadastrar(dto);
	    
	    verify(repository,  never()). save(Mockito.any(Pauta.class));
	}

}
