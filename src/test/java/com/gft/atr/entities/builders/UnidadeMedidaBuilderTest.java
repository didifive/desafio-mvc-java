package com.gft.atr.entities.builders;

import static com.gft.atr.enums.UnidadeReferencia.MILILITRO;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;

import com.gft.atr.entities.ReceitaIngrediente;
import com.gft.atr.entities.UnidadeMedida;
import com.gft.atr.enums.UnidadeReferencia;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do Builder para Unidade Medida")
class UnidadeMedidaBuilderTest {
	
	@Mock
	private static ReceitaIngrediente ingrediente;

  private static final Long ID = 1L;
	private static final String NOME = "Xícara";
	private static final String ABREVIACAO = "xic.";
	private static final UnidadeReferencia UNIDADE_DE_REFERENCIA = MILILITRO;
	private static final double QUANTIDADE_UNIDADE_REFERENCIA = 240;
	private static final List<ReceitaIngrediente> INGREDIENTES = Collections.singletonList(ingrediente);
	
	@Test
	@DisplayName("1. Deve criar novo objeto Unidade Medida")
	void shouldCreateNewUnidadeMedidaObject() throws Exception {
		UnidadeMedida unidadeMedida = new UnidadeMedidaBuilder()
                          								.withId(ID)
                          								.withNome(NOME)
                          								.withAbreciacao(ABREVIACAO)
                          								.withUnidadeReferencia(UNIDADE_DE_REFERENCIA)
                          								.withQuantidadeUnidadeRerefencia(QUANTIDADE_UNIDADE_REFERENCIA)
                          								.withIngredientes(INGREDIENTES)
                          								.build();
	
		assertAll( 
				"verifica se os atributos do objeto retornam corretamente."		
				,() -> assertEquals(ID, 														unidadeMedida.getId())
				,() -> assertEquals(NOME, 													unidadeMedida.getNome())
				,() -> assertEquals(ABREVIACAO, 										unidadeMedida.getAbreviacao())
				,() -> assertEquals(UNIDADE_DE_REFERENCIA, 					unidadeMedida.getUnidadeReferencia())
				,() -> assertEquals(QUANTIDADE_UNIDADE_REFERENCIA, 	unidadeMedida.getQuantidadeUnidadeReferencia())
				,() -> assertEquals(INGREDIENTES, 									unidadeMedida.getIngredientes())
		);
	
	}

}
