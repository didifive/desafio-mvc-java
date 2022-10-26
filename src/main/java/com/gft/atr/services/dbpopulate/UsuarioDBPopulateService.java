package com.gft.atr.services.dbpopulate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gft.atr.entities.Permissao;
import com.gft.atr.entities.Usuario;
import com.gft.atr.entities.builders.PermissaoBuilder;
import com.gft.atr.entities.builders.UsuarioBuilder;
import com.gft.atr.services.UsuarioService;

@Component
public class UsuarioDBPopulateService {
	
	@Autowired
	UsuarioService usuarioService;
	
	public List<Usuario> popularUsuario() {
		
		Permissao permissaoAdmin = new PermissaoBuilder()
											.withId(1L)
											.withNome("ROLE_ADMINISTRADOR")
											.build();
		Permissao permissaoUsuario = new PermissaoBuilder()
                            				.withId(2L)
                            				.withNome("ROLE_USUARIO")
                            				.build();
		
		List<Usuario> usuarios = new ArrayList<>();
		
		usuarios.add(new UsuarioBuilder()
									.withId(1L)
									.withNome("Administrador ATR")
									.withNomeUsuario("admin@email.com")
									.withEmail("admin@email.com")
									.withSenha("admin@1234")
									.isAtivo(true)
									.withPermissoes(Arrays.asList(permissaoAdmin, permissaoUsuario))
									.build());
		
		usuarios.add(new UsuarioBuilder()
                      				.withId(2L)
                      				.withNome("Usuario ATR")
                      				.withNomeUsuario("usuario@email.com")
                      				.withEmail("usuario@email.com")
                      				.withSenha("usuario@1234")
                      				.isAtivo(true)
                      				.withPermissoes(Arrays.asList(permissaoUsuario))
                      				.build());
		
		
		usuarios.forEach(u -> usuarioService.salvarUsuario(u));
		
		return usuarioService.listarUsuarios(null);
		
	}
	

}
