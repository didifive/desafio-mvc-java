package com.gft.atr.entities.builders;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;

import com.gft.atr.entities.ModoPreparo;
import com.gft.atr.entities.Receita;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do Builder para ModoPreparo")
class ModoPreparoBuilderTest {

	@Mock
	private static Receita receita;
	
	private static final Long ID = 1L;
	private static final int ORDEM = 1;
	private static final String DESCRICAO = "Jogue tudo no liquidificador e bata";
	private static final Receita RECEITA = receita;

	
	@Test
	@DisplayName("1. Deve criar novo objeto ModoPreparo")
	void shouldCreateNewModoPreparoObject() throws Exception {
		ModoPreparo modoPreparo = new ModoPreparoBuilder()
                        				.withId(ID)
                        				.withReceita(RECEITA)
                        				.withOrdem(ORDEM)
                        				.withDescricao(DESCRICAO)
                        				.build();
	
		assertAll( 
				"verifica se os atributos do objeto retornam corretamente."		
				,() -> assertEquals(ID, 				modoPreparo.getId())
				,() -> assertEquals(RECEITA,		modoPreparo.getReceita())
				,() -> assertEquals(ORDEM, 			modoPreparo.getOrdem())
				,() -> assertEquals(DESCRICAO,	modoPreparo.getDescricao())
		);
	
	}

}
