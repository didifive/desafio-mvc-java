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
import com.gft.atr.entities.Permissao;
import com.gft.atr.entities.Receita;
import com.gft.atr.entities.Usuario;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do Builder para Usuario")
class UsuarioBuilderTest {

	@Mock
	private static Permissao permissao;
	
	@Mock
	private static Receita receita;
	
	@Mock
	private static Avaliacao avaliacao;
	
	private static final Long ID = 1L;
	private static final String NOME = "Administrador";
	private static final String NOME_USUARIO = "admin";
	private static final String SENHA = "123";
	private static final String EMAIL = "admin@gft.com";
	private static final List<Permissao> PERMISSOES = Collections.singletonList(permissao);
	private static final boolean ATIVO = true;
	private static final List<Receita> RECEITAS = Collections.singletonList(receita);
	private static final List<Avaliacao> AVALIACOES = Collections.singletonList(avaliacao);
	
	@Test
	@DisplayName("1. Deve criar novo objeto Usuario")
	void shouldCreateNewUsuarioObject() throws Exception {
		Usuario usuario = new UsuarioBuilder()
                        				.withId(ID)
                        				.withNome(NOME)
                        				.withNomeUsuario(NOME_USUARIO)
                        				.withSenha(SENHA)
                        				.withEmail(EMAIL)
                        				.withPermissoes(PERMISSOES)
                        				.isAtivo(ATIVO)
                        				.withReceitas(RECEITAS)
                        				.withAvaliacoes(AVALIACOES)
                        				.build();
	
		assertAll( 
				"verifica se os atributos do objeto retornam corretamente."		
				,() -> assertEquals(ID, 					usuario.getId())
				,() -> assertEquals(NOME, 				usuario.getNome())
				,() -> assertEquals(NOME_USUARIO,	usuario.getNomeUsuario())
				,() -> assertEquals(SENHA, 				usuario.getSenha())
				,() -> assertEquals(EMAIL, 				usuario.getEmail())
				,() -> assertEquals(PERMISSOES, 	usuario.getPermissoes())
				,() -> assertEquals(ATIVO, 				usuario.isAtivo())
				,() -> assertEquals(RECEITAS, 		usuario.getReceitas())
				,() -> assertEquals(AVALIACOES, 	usuario.getAvaliacoes())
		);
	
	}

}
