package com.gft.atr.services;

import static com.gft.atr.enums.UnidadeReferencia.LITRO;
import static com.gft.atr.enums.UnidadeReferencia.MILILITRO;
import static com.gft.atr.enums.UnidadeReferencia.UNIDADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.MultipleFailuresError;

import com.gft.atr.entities.UnidadeMedida;
import com.gft.atr.entities.builders.UnidadeMedidaBuilder;
import com.gft.atr.exceptions.ImpossivelCalcularFatorConversaoException;
import com.gft.atr.exceptions.UnidadeMedidaNaoEncontradaException;
import com.gft.atr.repositories.UnidadeMedidaRepository;
import com.gft.atr.utils.UnidadeMedidaUtils;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes de UnidadeMedidaService")
class UnidadeMedidaServiceTest {
	
	private static final Long ID_INVALIDO = 2L;
	
	@Mock
  private UnidadeMedidaRepository unidadeMedidaRepository;
  
  @InjectMocks
  private UnidadeMedidaService unidadeMedidaService;
  
	private UnidadeMedida unidade;
	private UnidadeMedida unidade2;
	private UnidadeMedida unidadeFake;
	
	private void assertUnidadeMedida(UnidadeMedida unidadeMedidaAtual) throws MultipleFailuresError {
  	// given
  	UnidadeMedida unidadeMedidaEsperada = UnidadeMedidaUtils.createFakeEntity();
  	
  	assertAll(
  		"confirma que a retornou objeto salvo e verifica se atributos são conforme o esperado."
  		,() -> assertThat(unidadeMedidaAtual).isNotNull()
  		,() -> assertEquals(unidadeMedidaEsperada.getId(),													unidadeMedidaAtual.getId())
  		,() -> assertEquals(unidadeMedidaEsperada.getNome(), 												unidadeMedidaAtual.getNome())
  		,() -> assertEquals(unidadeMedidaEsperada.getAbreviacao(), 									unidadeMedidaAtual.getAbreviacao())
  		,() -> assertEquals(unidadeMedidaEsperada.getQuantidadeUnidadeReferencia(), unidadeMedidaAtual.getQuantidadeUnidadeReferencia())
  		,() -> assertEquals(unidadeMedidaEsperada.getUnidadeReferencia(), 					unidadeMedidaAtual.getUnidadeReferencia())
  		,() -> assertEquals(unidadeMedidaEsperada.getIngredientes(), 								unidadeMedidaAtual.getIngredientes())
    );
	}
	
  
  private void assertListaUnidadeMedida(List<UnidadeMedida> unidadesMedidaListadas) {
  	// given
   	UnidadeMedida unidadeMedidaEsperada = UnidadeMedidaUtils.createFakeEntity();
   	
  	assertAll(
  		"verifica a lista de Unidade de Medida, não pode estar vazia e deve conter a Unidade de Medida especificada"
  		,() -> assertThat(unidadesMedidaListadas).isNotEmpty()
  		,() -> assertThat(unidadesMedidaListadas.get(0)).isEqualTo(unidadeMedidaEsperada)
    );
  }
	
	double calculaConversao(UnidadeMedida unidade, UnidadeMedida unidade2) throws ImpossivelCalcularFatorConversaoException {
  	return UnidadeMedidaService.calculaConversao(unidade, unidade2);
  }
	
	void assertConversao(double esperado, double atual) {
		assertEquals(esperado, atual, 0.000000001);
	}
	
	@BeforeEach
	void setup() {
		unidade = new UnidadeMedidaBuilder().withQuantidadeUnidadeRerefencia(240).build();
		unidade2 = new UnidadeMedidaBuilder().withQuantidadeUnidadeRerefencia(10).build();
		unidadeFake = UnidadeMedidaUtils.createFakeEntity();
	}
	
	@Test
	@DisplayName("1. Deve calcular valor de conversão entre medidas com mesma unidade de referência")
	void deveCalcularValorDeConversaoEntreMedidasComMesmaUnidadeDeReferencia() throws Exception {
		//given
		unidade.setUnidadeReferencia(MILILITRO);
		unidade2.setUnidadeReferencia(MILILITRO);
		
		//then	
		assertConversao(24, calculaConversao(unidade, unidade2));		
		
	}
	
	@Test
	@DisplayName("1.2. Deve calcular valor de conversão entre medidas com unidade de referência diferentes, mas com mesmo tipo")
	void deveCalcularValorDeConversaoEntreMedidasComUnidadeDeReferenciaDiferentesMasComMesmoTipo() throws Exception {
		//given
		unidade.setUnidadeReferencia(MILILITRO);
		unidade2.setUnidadeReferencia(LITRO);
		
		//then
		assertAll(
				"verifica a conversão nos dois sentidos"
				,() -> assertConversao(24000, calculaConversao(unidade, unidade2))
				,() -> assertConversao(0.000041667, calculaConversao(unidade2, unidade))
		);
		
	}
	
	@Test
	@DisplayName("2. Quando tentar calcular conversão com tipos de medidas diferentes deve retornar ImpossivelCalcularFatorConversaoException")
	void quandoTentarCalcularConversaoComTiposDeMedidasDiferentesDeveRetornarImpossivelCalcularFatorConversaoException() throws Exception {
		//given
		unidade.setUnidadeReferencia(LITRO);
		unidade2.setUnidadeReferencia(UNIDADE);
		
		//then
		assertThrows(ImpossivelCalcularFatorConversaoException.class, () -> calculaConversao(unidade, unidade2));
		
	}
	
		
  @Test
  @DisplayName("3. Deve salvar Unidade de Medida")
	void shouldSaveUnidadeMedida() throws Exception {
    //given
  	UnidadeMedida unidadeSemId = UnidadeMedidaUtils.createFakeEntityWithoutId();
  	
  	// when
    when(unidadeMedidaRepository.save(unidadeSemId)).thenReturn(unidadeFake);

    //then
    assertUnidadeMedida(unidadeMedidaService.salvarUnidadeMedida(unidadeSemId));
	}

  @Test
  @DisplayName("4. Deve listar todas as Unidades de Medida")
	void shouldListAllUnidadeMedida() throws Exception {
    // when
    when(unidadeMedidaRepository.findAll()).thenReturn(Collections.singletonList(unidadeFake));

    //then
    assertListaUnidadeMedida(unidadeMedidaService.listarUnidadesMedida(null));
	}
  
  @Test
  @DisplayName("4.1. Deve listar Unidades de Medida por Nome")
	void shouldListUnidadeMedidaByNome() throws Exception {
    // when
    when(unidadeMedidaRepository.findByNomeContains(unidadeFake.getNome()))
    							.thenReturn(Collections.singletonList(unidadeFake)
    );

    //then
    assertListaUnidadeMedida(unidadeMedidaService.listarUnidadesMedida(unidadeFake.getNome()));
	}
  
  
  @Test
  @DisplayName("5. Quando vai buscar e o Id de Unidade de Medida dado é válido, então deve retornar Unidade de Medida")
	void whenFetchAndTheValidUnidadeMedidaIdIsGivenThenShouldReturnUnidadeMedida() throws Exception {
  	// when
    when(unidadeMedidaRepository.findById(unidadeFake.getId())).thenReturn(Optional.of(unidadeFake));

    //then
    assertUnidadeMedida(unidadeMedidaService.obterUnidadeMedida(unidadeFake.getId()));
  }
    
  @Test
  @DisplayName("5.1. Quando um Id Inválido de Unidade de Medida é dado, então deve lançar UnidadeMedidaNaoEncontradaException")
	void whenFetchAndTheInvalidUnidadeMedidaIdIsGivenThenShouldThrowUnidadeMedidaNaoEncontradaException() throws Exception {
    	// when
      when(unidadeMedidaRepository.findById(ID_INVALIDO)).thenReturn(Optional.empty());

      //then
      assertThrows(UnidadeMedidaNaoEncontradaException.class, () -> unidadeMedidaService.obterUnidadeMedida(ID_INVALIDO));
	}
	
  @Test
  @DisplayName("6. Quando vai excluir e o Id de Unidade de Medida dado é válido, então deve excluir a Unidade de Medida")
	void whenWillDeleteAndValidUnidadeMedidaIdIsGivenThenShouldDeleteUnidadeMedida() throws Exception {
  	// when
    when(unidadeMedidaRepository.findById(unidadeFake.getId())).thenReturn(Optional.of(unidadeFake));

    //then
    unidadeMedidaService.excluirUnidadeMedida(unidadeFake.getId());
    
    assertAll(
    	"verifica se a busca e o delete de unidadeMedidaRepository executou"
    	,() -> verify(unidadeMedidaRepository, times(1)).findById(unidadeFake.getId())
    	,() -> verify(unidadeMedidaRepository, times(1)).deleteById(unidadeFake.getId())
    );
  }
    
  @Test
  @DisplayName("6.1. Quando vai excluir e um Id inválido de Unidade de Medida é dado, então deve lançar UnidadeMedidaNaoEncontradaException")
	void whenWillDeleteAndInvalidUnidadeMedidaIdIsGivenThenShouldThrowUnidadeMedidaNaoEncontradaException() throws Exception {
  	// when
		when(unidadeMedidaRepository.findById(ID_INVALIDO)).thenReturn(Optional.empty());

		//then
		assertThrows(UnidadeMedidaNaoEncontradaException.class, () -> unidadeMedidaService.excluirUnidadeMedida(ID_INVALIDO));
	}

}
