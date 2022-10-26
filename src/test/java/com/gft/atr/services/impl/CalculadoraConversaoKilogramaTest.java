package com.gft.atr.services.impl;

import static com.gft.atr.enums.UnidadeReferencia.GRAMA;
import static com.gft.atr.enums.UnidadeReferencia.KILOGRAMA;
import static com.gft.atr.enums.UnidadeReferencia.UNIDADE;
import static com.gft.atr.enums.UnidadeReferencia.MILILITRO;
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
@DisplayName("Testes de CalculadoraConversaoKilograma")
class CalculadoraConversaoKilogramaTest {
	
	private UnidadeMedida unidade;

  double calculaFatorConversao(UnidadeMedida unidade) throws ImpossivelCalcularFatorConversaoException {
  	return KILOGRAMA.getFator().calculaFatorConversao(unidade);
  }
	
	void assertFatorConversao(double esperado, double atual) {
		assertEquals(esperado, atual, 0.000000001);
	}
	
	@BeforeEach
	void setup() {
		unidade = new UnidadeMedidaBuilder().build();
	}
	
	@Test
	@DisplayName("1.1. Quando Converter Unidade de Medida com Referência de Grama para Kilograma Deve Retornar 0001")
	void quandoConverterUnidadeDeMedidaComReferenciaDeGramaParaKilogramaDeveRetornar1000() throws Exception {
		//given
		unidade.setUnidadeReferencia(GRAMA);
		
		//then
		assertFatorConversao(0.001, calculaFatorConversao(unidade));
		
	}
	
	@Test
	@DisplayName("1.2. Quando Converter Unidade de Medida com Referência Igual Deve Retornar 1")
	void quandoConverterUnidadeDeMedidaComReferenciaIgualDeveRetornar1() throws Exception {
		//given
		unidade.setUnidadeReferencia(KILOGRAMA);
		
		//then
		assertFatorConversao(1, calculaFatorConversao(unidade));
		
	}
	
	@Test
	@DisplayName("2. Quando Tentar Converter Unidade com Tipo Referencia Geral, deve retornar ImpossivelCalcularFatorConversaoException")
	void quandoTentarConverterUnidadeComTipoReferenciaGeralDeveRetornarImpossivelCalcularFatorConversaoException() throws Exception {
		//given
		unidade.setUnidadeReferencia(UNIDADE);
		
		//then
		assertThrows(ImpossivelCalcularFatorConversaoException.class, () -> calculaFatorConversao(unidade));
		
	}
	
	@Test
	@DisplayName("2.1. Quando Tentar Converter Unidade com Tipo Referencia Volume, deve retornar ImpossivelCalcularFatorConversaoException")
	void quandoTentarConverterUnidadeComTipoReferenciaVolumeDeveRetornarImpossivelCalcularFatorConversaoException() throws Exception {
		//given
		unidade.setUnidadeReferencia(MILILITRO);
		
		//then
		assertThrows(ImpossivelCalcularFatorConversaoException.class, () -> calculaFatorConversao(unidade));
		
	}

}
