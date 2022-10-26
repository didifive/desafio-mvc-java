package com.gft.atr.entities.builders;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;

import com.gft.atr.entities.Ingrediente;
import com.gft.atr.entities.ReceitaIngrediente;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do Builder para Ingrediente")
class IngredienteBuilderTest {

	@Mock
	private static ReceitaIngrediente receitaIngrediente;
	
	private static final Long ID = 1L;
	private static final String NOME = "Farinha";
	private static final String IMAGEM = "FotoFarinha";
	private static final double DENSIDADE = 1.5;
	private static final List<ReceitaIngrediente> RECEITAS = Collections.singletonList(receitaIngrediente);
	
	@Test
	@DisplayName("1. Deve criar novo objeto Ingrediente")
	void shouldCreateNewIngredienteObject() throws Exception {
		Ingrediente ingrediente = new IngredienteBuilder()
                                				.withId(ID)
                                				.withNome(NOME)
                                				.withImagem(IMAGEM)
                                				.withDensidade(DENSIDADE)
                                				.withReceitas(RECEITAS)
                                				.build();
	
		assertAll( 
				"verifica se os atributos do objeto retornam corretamente."		
				,() -> assertEquals(ID, 				ingrediente.getId())
				,() -> assertEquals(NOME, 			ingrediente.getNome())
				,() -> assertEquals(IMAGEM, 		ingrediente.getImagem())
				,() -> assertEquals(DENSIDADE, 	ingrediente.getDensidade())
				,() -> assertEquals(RECEITAS, 	ingrediente.getReceitas())
		);
	
	}

}
