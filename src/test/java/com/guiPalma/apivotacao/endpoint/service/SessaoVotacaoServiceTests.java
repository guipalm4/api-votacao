package com.guiPalma.apivotacao.endpoint.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

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
import com.guiPalma.apivotacao.dto.SessaoVotacaoDto;
import com.guiPalma.apivotacao.exceptions.ObjectNotFoundException;
import com.guiPalma.apivotacao.model.Pauta;
import com.guiPalma.apivotacao.model.SessaoVotacao;
import com.guiPalma.apivotacao.repository.PautaRepository;
import com.guiPalma.apivotacao.repository.SessaoVotacaoRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SessaoVotacaoServiceTests {
	
	@Autowired
	private SessaoVotacaoService service;

	@MockBean
	private PautaRepository pautaRepository;
	
	@MockBean
	private SessaoVotacaoRepository sessaoRepository;	
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	private final static Integer DURACAO_DEFAULT = 1;
	
	private Long idPauta;
	private String descricaoPauta;
	
	private Long idSessao;
	private String descricaoSessao;
	

	@Before
	public void setUp() {
		this.descricaoPauta = "NOME TESTE";
		this.idPauta = 1236L;
		this.idSessao = 12345L;
		this.descricaoSessao = "SESSAO VOTACAO TESTE";
	}
	
	@Test
	public void deve_criar_sessao_votacao_com_duracao_defaut_sem_idPauta() {
		
		var dto = SessaoVotacaoDto.builder()
				.descricao(descricaoSessao)
				.pauta(PautaDto.builder().id(null).descricao(descricaoPauta).build())
				.build();
		
		var pautaMock = Pauta.builder().id(idPauta).descricao(descricaoPauta).build();
		var sessaoMock = SessaoVotacao.builder()
				.id(idSessao)
				.descricao(descricaoSessao)
				.pauta(pautaMock)
				.duracao(DURACAO_DEFAULT)
				.build();
		
		BDDMockito.given(pautaRepository.findById(Mockito.anyLong())).willReturn(null);
		BDDMockito.given(pautaRepository.findByDescricao(Mockito.anyString())).willReturn(pautaMock);
		BDDMockito.given(sessaoRepository.save(Mockito.any(SessaoVotacao.class))).willReturn(sessaoMock);
		
		
		var result = service.criarSessaoVotacao(dto);
		
		verify(pautaRepository,  never()).findById(Mockito.anyLong());
		verify(pautaRepository, times(1)).findByDescricao(Mockito.anyString());
		verify(sessaoRepository, times(1)).save(Mockito.any(SessaoVotacao.class));
		assertNotNull(result);
		assertNotNull(result.getId());
		assertEquals(result.getDuracao(), DURACAO_DEFAULT);
		
	}
	
	@Test
	public void deve_criar_sessao_votacao_com_duracao_defaut_com_idPauta() {
		
		var dto = SessaoVotacaoDto.builder()
				.descricao(descricaoSessao)
				.pauta(PautaDto.builder().id(idPauta).descricao(null).build())
				.build();
		
		var pautaMock = Pauta.builder().id(idPauta).descricao(descricaoPauta).build();
		var sessaoMock = SessaoVotacao.builder()
				.id(idSessao)
				.descricao(descricaoSessao)
				.pauta(pautaMock)
				.duracao(DURACAO_DEFAULT)
				.build();
		
		BDDMockito.given(pautaRepository.findById(Mockito.anyLong())).willReturn(Optional.of(pautaMock));
		BDDMockito.given(pautaRepository.findByDescricao(Mockito.anyString())).willReturn(null);
		BDDMockito.given(sessaoRepository.save(Mockito.any(SessaoVotacao.class))).willReturn(sessaoMock);
		
		
		var result = service.criarSessaoVotacao(dto);
		
		verify(pautaRepository,  times(1)).findById(Mockito.anyLong());
		verify(pautaRepository, never()).findByDescricao(Mockito.anyString());
		verify(sessaoRepository, times(1)).save(Mockito.any(SessaoVotacao.class));
		assertNotNull(result);
		assertNotNull(result.getId());
		assertEquals(result.getDuracao(), DURACAO_DEFAULT);
		
	}
	@Test
	public void deve_lancar_excessao_pauta_nao_encontrada() {
		
		var dto = SessaoVotacaoDto.builder()
				.descricao(descricaoSessao)
				.pauta(PautaDto.builder().id(idPauta).descricao(null).build())
				.build();
		
		
		BDDMockito.given(pautaRepository.findById(Mockito.anyLong())).willReturn(Optional.empty());
		BDDMockito.given(pautaRepository.findByDescricao(Mockito.anyString())).willReturn(null);
		
		exceptionRule.expect(ObjectNotFoundException.class);
	    exceptionRule.expectMessage("Pauta não encontrada");
		
		service.criarSessaoVotacao(dto);
	    
		verify(sessaoRepository, never()).save(Mockito.any(SessaoVotacao.class));
	}

}
