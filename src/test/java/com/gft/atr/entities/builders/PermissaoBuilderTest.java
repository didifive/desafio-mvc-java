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

import com.gft.atr.entities.Permissao;
import com.gft.atr.entities.Usuario;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do Builder para Permissao")
class PermissaoBuilderTest {

	@Mock
	private static Usuario usuario;
	
	private static final Long ID = 1L;
	private static final String NOME = "ROLE_ADMINISTRATOR";
	private static final List<Usuario> USUARIOS = Collections.singletonList(usuario);
	
	@Test
	@DisplayName("1. Deve criar novo objeto Permissao")
	void shouldCreateNewPermissaObject() throws Exception {
		Permissao permissao = new PermissaoBuilder()
                        				.withId(ID)
                        				.withNome(NOME)
                        				.withUsuarios(USUARIOS)
                        				.build();
	
		assertAll( 
				"verifica se os atributos do objeto retornam corretamente."		
				,() -> assertEquals(ID, 			permissao.getId())
				,() -> assertEquals(NOME,			permissao.getNome())
				,() -> assertEquals(USUARIOS,	permissao.getUsuarios())
		);
	
	}

}
