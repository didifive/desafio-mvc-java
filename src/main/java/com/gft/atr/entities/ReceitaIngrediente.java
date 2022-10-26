package com.gft.atr.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "receita_ingrediente")
public class ReceitaIngrediente implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7770906255088092385L;

	@EmbeddedId
	ReceitaIngredientePK receitaIngredientePK;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private UnidadeMedida unidadeMedida;
	
	private double quantidade;
	
	private boolean aGosto;
	
	public ReceitaIngrediente() {}

	public ReceitaIngrediente(ReceitaIngredientePK receitaIngredientePK, UnidadeMedida unidadeMedida, double quantidade,
			boolean aGosto) {
		this.receitaIngredientePK = receitaIngredientePK;
		this.unidadeMedida = unidadeMedida;
		this.quantidade = quantidade;
		this.aGosto = aGosto;
	}

	public ReceitaIngredientePK getReceitaIngredientePK() {
		return receitaIngredientePK;
	}

	public void setReceitaIngredientePK(ReceitaIngredientePK receitaIngredientePK) {
		this.receitaIngredientePK = receitaIngredientePK;
	}

	public UnidadeMedida getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public boolean isaGosto() {
		return aGosto;
	}

	public void setaGosto(boolean aGosto) {
		this.aGosto = aGosto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
