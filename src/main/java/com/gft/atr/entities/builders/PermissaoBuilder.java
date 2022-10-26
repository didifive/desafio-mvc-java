package com.gft.atr.entities.builders;

import java.util.List;

import com.gft.atr.entities.Permissao;
import com.gft.atr.entities.Usuario;

public class PermissaoBuilder {

	private Permissao instancia;
	
	public PermissaoBuilder() {
		instancia = new Permissao();
	}
	
	public PermissaoBuilder withId(Long id) {
		instancia.setId(id);
		return this;
	}
	
	public PermissaoBuilder withNome(String nome) {
		instancia.setNome(nome);
		return this;
	}
	
	public PermissaoBuilder withUsuarios(List<Usuario> usuarios) {
		instancia.setUsuarios(usuarios);
		return this;
	}
	
	public Permissao build() {
		return instancia;
	}
	
}
