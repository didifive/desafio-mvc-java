package com.gft.atr.entities.builders;

import com.gft.atr.entities.ModoPreparo;
import com.gft.atr.entities.Receita;

public class ModoPreparoBuilder {

	private ModoPreparo instancia;
	
	public ModoPreparoBuilder() {
		instancia = new ModoPreparo();
	}
	
	public ModoPreparoBuilder withId(Long id) {
		instancia.setId(id);
		return this;
	}
	
	public ModoPreparoBuilder withOrdem(int ordem) {
		instancia.setOrdem(ordem);
		return this;
	}
	
	public ModoPreparoBuilder withDescricao(String descricao) {
		instancia.setDescricao(descricao);
		return this;
	}
	
	public ModoPreparoBuilder withReceita(Receita receita) {
		instancia.setReceita(receita);
		return this;
	}
	
	public ModoPreparo build() {
		return instancia;
	}
	
}
