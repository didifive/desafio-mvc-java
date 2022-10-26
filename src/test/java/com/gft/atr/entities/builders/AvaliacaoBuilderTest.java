package com.gft.atr.entities.builders;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;

import com.gft.atr.entities.Avaliacao;
import com.gft.atr.entities.Receita;
import com.gft.atr.entities.Usuario;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do Builder para Avaliacao")
class AvaliacaoBuilderTest {

	@Mock
	private static Receita receita;
	
	@Mock
	private static Usuario usuario;
	
	private static final Long ID = 1L;
	private static final Receita RECEITA = receita;
	private static final double NOTA = 4.5;
	private static final Usuario USUARIO = usuario;
	
	@Test
	@DisplayName("1. Deve criar novo objeto Avaliacao")
	void shouldCreateNewAvaliacaoObject() throws Exception {
		Avaliacao avaliacao = new AvaliacaoBuilder()
                        				.withId(ID)
                        				.withReceita(RECEITA)
                        				.withNota(NOTA)
																.withUsuario(USUARIO)
                        				.build();
	
		assertAll( 
				"verifica se os atributos do objeto retornam corretamente."		
				,() -> assertEquals(ID, 			avaliacao.getId())
				,() -> assertEquals(RECEITA,	avaliacao.getReceita())
				,() -> assertEquals(NOTA, 		avaliacao.getNota())
				,() -> assertEquals(USUARIO, 	avaliacao.getUsuario())
		);
	
	}

}
