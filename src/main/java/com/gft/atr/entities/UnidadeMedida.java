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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.gft.atr.enums.UnidadeReferencia;

@Entity
@Table(name = "unidadeMedida")
public class UnidadeMedida implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1206930868183961247L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "O nome deve ser preenchido!")
	private String nome;
	
	@NotEmpty(message = "A abreviação deve ser preenchida!")
	private String abreviacao;
	
	@NotNull(message = "Deve ser informado a unidade de referência!")
	private UnidadeReferencia unidadeReferencia;
	
	@DecimalMin(value = "0.0001", message = "Deve ser informado a quantidade com mínimo de 0,0001 para a unidade de referência!")
	private double quantidadeUnidadeReferencia;
	
	@OneToMany(mappedBy = "unidadeMedida", fetch=FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	private List<ReceitaIngrediente> ingredientes;

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

	public String getAbreviacao() {
		return abreviacao;
	}

	public void setAbreviacao(String abreviacao) {
		this.abreviacao = abreviacao;
	}

	public UnidadeReferencia getUnidadeReferencia() {
		return unidadeReferencia;
	}

	public void setUnidadeReferencia(UnidadeReferencia unidadeReferencia) {
		this.unidadeReferencia = unidadeReferencia;
	}

	public double getQuantidadeUnidadeReferencia() {
		return quantidadeUnidadeReferencia;
	}

	public void setQuantidadeUnidadeReferencia(double quantidadeUnidadeReferencia) {
		this.quantidadeUnidadeReferencia = quantidadeUnidadeReferencia;
	}

	public List<ReceitaIngrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<ReceitaIngrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(abreviacao, id, ingredientes, nome, quantidadeUnidadeReferencia, unidadeReferencia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnidadeMedida other = (UnidadeMedida) obj;
		return Objects.equals(abreviacao, other.abreviacao) && Objects.equals(id, other.id)
				&& Objects.equals(ingredientes, other.ingredientes) && Objects.equals(nome, other.nome) && Double
						.doubleToLongBits(quantidadeUnidadeReferencia) == Double.doubleToLongBits(other.quantidadeUnidadeReferencia)
				&& unidadeReferencia == other.unidadeReferencia;
	}

}
