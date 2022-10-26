package com.gft.atr.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "ingrediente")
public class Ingrediente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3914992211787468180L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "O nome deve ser preenchido!")
	private String nome;
	
	@Lob
	private String imagem;
	
	@DecimalMin(value = "0.0001", message = "Deve ser informado a quantidade com mínimo de 0,0001 para a densidade (g/cm³)!")
	private double densidade;
	
	@OneToMany(mappedBy = "receitaIngredientePK.ingrediente"
								, fetch=FetchType.LAZY
								, cascade = { CascadeType.ALL })
	private List<ReceitaIngrediente> receitas;
	
	public Ingrediente() {}

	public Ingrediente(Long id, @NotEmpty(message = "O nome deve ser preenchido!") String nome, String imagem,
			double densidade, List<ReceitaIngrediente> receitas) {
		this.id = id;
		this.nome = nome;
		this.imagem = imagem;
		this.densidade = densidade;
		this.receitas = receitas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public double getDensidade() {
		return densidade;
	}

	public void setDensidade(double densidade) {
		this.densidade = densidade;
	}

	public List<ReceitaIngrediente> getReceitas() {
		return receitas;
	}

	public void setReceitas(List<ReceitaIngrediente> receitas) {
		this.receitas = receitas;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(densidade, id, imagem, nome, receitas);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingrediente other = (Ingrediente) obj;
		return Double.doubleToLongBits(densidade) == Double.doubleToLongBits(other.densidade)
				&& Objects.equals(id, other.id) && Objects.equals(imagem, other.imagem) && Objects.equals(nome, other.nome)
				&& Objects.equals(receitas, other.receitas);
	}
	
}
