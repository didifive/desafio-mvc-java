package com.gft.atr.entities.builders;

import com.gft.atr.entities.ReceitaIngrediente;
import com.gft.atr.entities.ReceitaIngredientePK;
import com.gft.atr.entities.UnidadeMedida;

public class ReceitaIngredienteBuilder {

	private ReceitaIngrediente instancia;
	
	public ReceitaIngredienteBuilder() {
		instancia = new ReceitaIngrediente();
	}
	
	public ReceitaIngredienteBuilder withReceitaIngredientePK(ReceitaIngredientePK receitaIngredientePK) {
		instancia.setReceitaIngredientePK(receitaIngredientePK);
		return this;
	}
	
	public ReceitaIngredienteBuilder withUnidadeMedida(UnidadeMedida unidadeMedida) {
		instancia.setUnidadeMedida(unidadeMedida);
		return this;
	}
	
	public ReceitaIngredienteBuilder withQuantidade(double quantidade) {
		instancia.setQuantidade(quantidade);
		return this;
	}
	
	public ReceitaIngredienteBuilder withAGosto(boolean aGosto) {
		instancia.setaGosto(aGosto);
		return this;
	}
	
	public ReceitaIngrediente build() {
		return instancia;
	}
	
}
