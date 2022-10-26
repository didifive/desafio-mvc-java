package com.gft.atr.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class ReceitaIngredientePK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7704034927380841052L;

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
	private Receita receita;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private Ingrediente ingrediente;
	
	public ReceitaIngredientePK() {	}

	public ReceitaIngredientePK(Receita receita, Ingrediente ingrediente) {
		this.receita = receita;
		this.ingrediente = ingrediente;
	}

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ingrediente, receita);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReceitaIngredientePK other = (ReceitaIngredientePK) obj;
		return Objects.equals(ingrediente, other.ingrediente) && Objects.equals(receita, other.receita);
	}

}
