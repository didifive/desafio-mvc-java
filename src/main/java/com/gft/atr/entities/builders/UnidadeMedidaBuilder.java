package com.gft.atr.entities.builders;

import java.util.List;

import com.gft.atr.entities.ReceitaIngrediente;
import com.gft.atr.entities.UnidadeMedida;
import com.gft.atr.enums.UnidadeReferencia;

public class UnidadeMedidaBuilder {
	
	private UnidadeMedida instancia;
	
	public UnidadeMedidaBuilder() {
		instancia = new UnidadeMedida();
	}
	
	public UnidadeMedidaBuilder withId(Long id) {
		instancia.setId(id);
		return this;
	}
	
	public UnidadeMedidaBuilder withNome(String nome) {
		instancia.setNome(nome);
		return this;
	}
	
	public UnidadeMedidaBuilder withAbreciacao(String abreviacao) {
		instancia.setAbreviacao(abreviacao);
		return this;
	}
	
	public UnidadeMedidaBuilder withUnidadeReferencia(UnidadeReferencia unidadeReferencia) {
		instancia.setUnidadeReferencia(unidadeReferencia);
		return this;
	}
	
	public UnidadeMedidaBuilder withQuantidadeUnidadeRerefencia(double quantidade) {
		instancia.setQuantidadeUnidadeReferencia(quantidade);
		return this;
	}
	
	public UnidadeMedidaBuilder withIngredientes(List<ReceitaIngrediente> ingredientes) {
		instancia.setIngredientes(ingredientes);
		return this;
	}
	
	public UnidadeMedida build() {
		return instancia;
	}

}