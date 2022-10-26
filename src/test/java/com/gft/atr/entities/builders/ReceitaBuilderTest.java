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

import com.gft.atr.entities.Avaliacao;
import com.gft.atr.entities.ModoPreparo;
import com.gft.atr.entities.Receita;
import com.gft.atr.entities.ReceitaIngrediente;
import com.gft.atr.entities.Usuario;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do Builder para Receita")
class ReceitaBuilderTest {

	@Mock
	private static ReceitaIngrediente receitaIngrediente;
	
	@Mock
	private static ModoPreparo modoPreparo;
	
	@Mock
	private static Avaliacao avaliacao;
	
	@Mock
	private static Usuario usuario;
	
	private static final Long ID = 1L;
	private static final String NOME = "Bolo de Cenoura";
	private static final String IMAGEM = "Foto";
	private static final String DESCRICAO = "Um bolo muito gostoso e fácil de fazer.";
	private static final int TEMPO = 50;
	private static final int RENDIMENTO = 15;
	private static final List<ReceitaIngrediente> INGREDIENTES = Collections.singletonList(receitaIngrediente);
	private static final List<ModoPreparo> MODOS_PREPARO = Collections.singletonList(modoPreparo);
	private static final List<Avaliacao> AVALIACOES = Collections.singletonList(avaliacao);
	private static final Usuario USUARIO = usuario;
	
	@Test
	@DisplayName("1. Deve criar novo objeto Receita")
	void shouldCreateNewReceitaObject() throws Exception {
		Receita receita = new ReceitaBuilder()
                        				.withId(ID)
                        				.withNome(NOME)
                        				.withImagem(IMAGEM)
                        				.withDescricao(DESCRICAO)
                        				.withTempoPreparo(TEMPO)
                        				.withRendimento(RENDIMENTO)
																.withIngredientes(INGREDIENTES)
																.withModosPreparo(MODOS_PREPARO)
																.withAvaliacoes(AVALIACOES)
																.withUsuario(USUARIO)
                        				.build();
	
		assertAll( 
				"verifica se os atributos do objeto retornam corretamente."		
				,() -> assertEquals(ID, 						receita.getId())
				,() -> assertEquals(NOME, 					receita.getNome())
				,() -> assertEquals(IMAGEM, 				receita.getImagem())
				,() -> assertEquals(DESCRICAO,			receita.getDescricao())
				,() -> assertEquals(TEMPO, 					receita.getTempoPreparo())
				,() -> assertEquals(RENDIMENTO, 		receita.getRendimento())
				,() -> assertEquals(INGREDIENTES, 	receita.getIngredientes())
				,() -> assertEquals(MODOS_PREPARO,	receita.getModosPreparo())
				,() -> assertEquals(AVALIACOES, 		receita.getAvaliacoes())
				,() -> assertEquals(USUARIO, 				receita.getUsuario())
		);
	
	}

}
