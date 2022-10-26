package com.gft.atr.entities.builders;

import java.util.List;

import com.gft.atr.entities.Avaliacao;
import com.gft.atr.entities.Permissao;
import com.gft.atr.entities.Receita;
import com.gft.atr.entities.Usuario;

public class UsuarioBuilder {

	private Usuario instancia;
	
	public UsuarioBuilder() {
		instancia = new Usuario();
	}
	
	public UsuarioBuilder withId(Long id) {
		instancia.setId(id);
		return this;
	}
	
	public UsuarioBuilder withNome(String nome) {
		instancia.setNome(nome);
		return this;
	}
	
	public UsuarioBuilder withNomeUsuario(String nomeUsuario) {
		instancia.setNomeUsuario(nomeUsuario);
		return this;
	}
	
	public UsuarioBuilder withSenha(String senha) {
		instancia.setSenha(senha);
		return this;
	}
	
	public UsuarioBuilder withEmail(String email) {
		instancia.setEmail(email);
		return this;
	}
	
	public UsuarioBuilder withPermissoes(List<Permissao> permissoes) {
		instancia.setPermissoes(permissoes);
		return this;
	}
	
	public UsuarioBuilder isAtivo(boolean ativo) {
		instancia.setAtivo(ativo);
		return this;
	}
	
	public UsuarioBuilder withReceitas(List<Receita> receitas) {
		instancia.setReceitas(receitas);
		return this;
	}
	
	public UsuarioBuilder withAvaliacoes(List<Avaliacao> avaliacoes) {
		instancia.setAvaliacoes(avaliacoes);
		return this;
	}
	
	public Usuario build() {
		return instancia;
	}
}
