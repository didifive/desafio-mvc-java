package com.gft.atr.utils;

import org.mockito.Mock;

import com.gft.atr.entities.Avaliacao;
import com.gft.atr.entities.Permissao;
import com.gft.atr.entities.Receita;
import com.gft.atr.entities.Usuario;
import com.gft.atr.entities.builders.UsuarioBuilder;

public class UsuarioUtils {
	
	@Mock
	private static Permissao permissao;
	
	@Mock
	private static Receita receita;
	
	@Mock
	private static Avaliacao avaliacao;
	
	private static final Long ID = 1L;
	private static final String NOME = "Administrador";
	private static final String NOME_USUARIO = "admin";
	private static final String SENHA = "1234";
	private static final String EMAIL = "admin@gft.com";
	
	
 public static Usuario createFakeEntity() {
		return new UsuarioBuilder()
							.withId(ID)
							.withNome(NOME)
							.withNomeUsuario(NOME_USUARIO)
							.withSenha(SENHA)
							.withEmail(EMAIL)
							.build();
	}

	public static Usuario createFakeEntityWithoutId() {
		return new UsuarioBuilder()
      				.withNome(NOME)
      				.withNomeUsuario(NOME_USUARIO)
      				.withSenha(SENHA)
      				.withEmail(EMAIL)
      				.build();
	}
	
}
