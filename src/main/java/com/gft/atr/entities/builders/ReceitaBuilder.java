package com.gft.atr.entities.builders;

import java.util.List;

import com.gft.atr.entities.Avaliacao;
import com.gft.atr.entities.ModoPreparo;
import com.gft.atr.entities.Receita;
import com.gft.atr.entities.ReceitaIngrediente;
import com.gft.atr.entities.Usuario;

public class ReceitaBuilder {

	private Receita instancia;
	
	public ReceitaBuilder() {
		instancia = new Receita();
	}
	
	public ReceitaBuilder withId(Long id) {
		instancia.setId(id);
		return this;
	}
	
	public ReceitaBuilder withNome(String nome) {
		instancia.setNome(nome);
		return this;
	}
	
	public ReceitaBuilder withDescricao(String descricao) {
		instancia.setDescricao(descricao);
		return this;
	}
	
	public ReceitaBuilder withImagem(String imagem) {
		instancia.setImagem(imagem);
		return this;
	}
	
	public ReceitaBuilder withTempoPreparo(int tempo) {
		instancia.setTempoPreparo(tempo);
		return this;
	}
	
	public ReceitaBuilder withRendimento(int rendimento) {
		instancia.setRendimento(rendimento);
		return this;
	}
	
	public ReceitaBuilder withIngredientes(List<ReceitaIngrediente> ingredientes) {
		instancia.setIngredientes(ingredientes);
		return this;
	}
	
	public ReceitaBuilder withModosPreparo(List<ModoPreparo> modosPreparo) {
		instancia.setModosPreparo(modosPreparo);
		return this;
	}
	
	public ReceitaBuilder withAvaliacoes(List<Avaliacao> avaliacoes) {
		instancia.setAvaliacoes(avaliacoes);
		return this;
	}
	
	public ReceitaBuilder withUsuario(Usuario usuario) {
		instancia.setUsuario(usuario);
		return this;
	}
	
	public Receita build() {
		return instancia;
	}
}
