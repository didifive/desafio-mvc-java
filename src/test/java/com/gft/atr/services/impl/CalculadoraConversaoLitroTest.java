package com.gft.atr.services.impl;

import static com.gft.atr.enums.UnidadeReferencia.CENTIMETRO_CUBICO;
import static com.gft.atr.enums.UnidadeReferencia.LITRO;
import static com.gft.atr.enums.UnidadeReferencia.METRO_CUBICO;
import static com.gft.atr.enums.UnidadeReferencia.MILILITRO;
import static com.gft.atr.enums.UnidadeReferencia.UNIDADE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.gft.atr.entities.UnidadeMedida;
import com.gft.atr.entities.builders.UnidadeMedidaBuilder;
import com.gft.atr.exceptions.ImpossivelCalcularFatorConversaoException;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes de CalculadoraConversaoLitro")
class CalculadoraConversaoLitroTest {
	
	private UnidadeMedida unidade;

  double calculaFatorConversao(UnidadeMedida unidade) throws ImpossivelCalcularFatorConversaoException {
  	return LITRO.getFator().calculaFatorConversao(unidade);
  }
	
	void assertFatorConversao(double esperado, double atual) {
		assertEquals(esperado, atual, 0.000000001);
	}
	
	@BeforeEach
	void setup() {
		unidade = new UnidadeMedidaBuilder().build();
	}
	
	@Test
	@DisplayName("1.1. Quando Converter Unidade de Medida com ReferĂȘncia de Mililitro para Litro Deve Retornar 0001")
	void quandoConverterUnidadeDeMedidaComReferenciaDeMililitroParaLitroDeveRetornar0001() throws Exception {
		//given
		unidade.setUnidadeReferencia(MILILITRO);
		
		//then
		assertFatorConversao(0.001, calculaFatorConversao(unidade));
		
	}
	
	@Test
	@DisplayName("1.2. Quando Converter Unidade de Medida com ReferĂȘncia Igual Deve Retornar 1")
	void quandoConverterUnidadeDeMedidaComReferenciaIgualDeveRetornar1() throws Exception {
		//given
		unidade.setUnidadeReferencia(LITRO);
		
		//then
		assertFatorConversao(1, calculaFatorConversao(unidade));
		
	}
	
	@Test
	@DisplayName("1.3. Quando Converter Unidade de Medida com ReferĂȘncia de Centimetro CĂșbico para Litro Deve Retornar 0001")
	void quandoConverterUnidadeDeMedidaComReferenciaDeCentimetroCubicoParaLitroDeveRetornar0001() throws Exception {
		//given
		unidade.setUnidadeReferencia(CENTIMETRO_CUBICO);
		
		//then
		assertFatorConversao(0.001, calculaFatorConversao(unidade));
		
	}
	
	@Test
	@DisplayName("1.4. Quando Converter Unidade de Medida com ReferĂȘncia de Metro CĂșbico para Litro Deve Retornar 1000")
	void quandoConverterUnidadeDeMedidaComReferenciaDeMetroCubicoParaLitroDeveRetornar1000() throws Exception {
		//given
		unidade.setUnidadeReferencia(METRO_CUBICO);
		
		//then
		assertFatorConversao(1000, calculaFatorConversao(unidade));
		
	}
	
	@Test
	@DisplayName("2. Quando Tentar Converter Unidade com Tipo Referencia diferente, deve retornar ImpossivelCalcularFatorConversaoException")
	void quandoTentarConverterUnidadeComTipoReferenciaDiferenteDeveRetornarImpossivelCalcularFatorConversaoException() throws Exception {
		//given
		unidade.setUnidadeReferencia(UNIDADE);
		
		//then
		assertThrows(ImpossivelCalcularFatorConversaoException.class, () -> calculaFatorConversao(unidade));
		
	}

}
