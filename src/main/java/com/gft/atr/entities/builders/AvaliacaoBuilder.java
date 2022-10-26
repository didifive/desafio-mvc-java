package com.gft.atr.entities.builders;

import com.gft.atr.entities.Avaliacao;
import com.gft.atr.entities.Receita;
import com.gft.atr.entities.Usuario;

public class AvaliacaoBuilder {

	private Avaliacao instancia;
	
	public AvaliacaoBuilder() {
		instancia = new Avaliacao();
	}
	
	public AvaliacaoBuilder withId(Long id) {
		instancia.setId(id);
		return this;
	}
	
	public AvaliacaoBuilder withReceita(Receita receita) {
		instancia.setReceita(receita);
		return this;
	}
	
	public AvaliacaoBuilder withNota(double nota) {
		instancia.setNota(nota);
		return this;
	}
	
	public AvaliacaoBuilder withUsuario(Usuario usuario) {
		instancia.setUsuario(usuario);
		return this;
	}
	
	public Avaliacao build() {
		return instancia;
	}
	
}
