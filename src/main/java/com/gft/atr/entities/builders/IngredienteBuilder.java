package com.gft.atr.entities.builders;

import java.util.List;

import com.gft.atr.entities.Ingrediente;
import com.gft.atr.entities.ReceitaIngrediente;

public class IngredienteBuilder {
	
	private Ingrediente instancia;
	
	public IngredienteBuilder() {
		instancia = new Ingrediente();
	}
	
	public IngredienteBuilder withId(Long id) {
		instancia.setId(id);
		return this;
	}
	
	public IngredienteBuilder withNome(String nome) {
		instancia.setNome(nome);
		return this;
	}
	
	public IngredienteBuilder withImagem(String imagem) {
		instancia.setImagem(imagem);
		return this;
	}
	
	public IngredienteBuilder withDensidade(double densidade) {
		instancia.setDensidade(densidade);
		return this;
	}
	
	public IngredienteBuilder withReceitas(List<ReceitaIngrediente> receitas) {
		instancia.setReceitas(receitas);
		return this;
	}
	
	public Ingrediente build() {
		return instancia;
	}

}