package com.gft.atr.entities.builders;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;

import com.gft.atr.entities.Ingrediente;
import com.gft.atr.entities.Receita;
import com.gft.atr.entities.ReceitaIngrediente;
import com.gft.atr.entities.ReceitaIngredientePK;
import com.gft.atr.entities.UnidadeMedida;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do Builder para a relação Receita x Ingrediente")
class ReceitaIngredienteBuilderTest {

	@Mock
	private static Receita receita;
	
	@Mock
	private static Ingrediente ingrediente;
	
	@Mock
	private static UnidadeMedida unidadeMedida;
	
	private static final Receita RECEITA = receita;
	private static final Ingrediente INGREDIENTE = ingrediente;
	private static final ReceitaIngredientePK RECEITA_INGREDIENTE_PK = new ReceitaIngredientePK(RECEITA,INGREDIENTE);
	private static final UnidadeMedida UNIDADE_MEDIDA = unidadeMedida;
	private static final double QUANTIDADE = 50.5;
	private static final boolean A_GOSTO = false;
	
	@Test
	@DisplayName("1. Deve criar novo objeto ReceitaIngrediente")
	void shouldCreateNewReceitaIngredienteObject() throws Exception {
		ReceitaIngrediente receitaIngrediente = new ReceitaIngredienteBuilder()
																									.withReceitaIngredientePK(RECEITA_INGREDIENTE_PK)
                                          				.withUnidadeMedida(UNIDADE_MEDIDA)
                                          				.withQuantidade(QUANTIDADE)
                                          				.build();
	
		assertAll( 
				"verifica se os atributos do objeto retornam corretamente."		
				,() -> assertEquals(RECEITA_INGREDIENTE_PK, receitaIngrediente.getReceitaIngredientePK())
				,() -> assertEquals(UNIDADE_MEDIDA,					receitaIngrediente.getUnidadeMedida())
				,() -> assertEquals(QUANTIDADE, 						receitaIngrediente.getQuantidade())
				,() -> assertEquals(A_GOSTO, 								receitaIngrediente.isaGosto())
		);
	
	}

}
