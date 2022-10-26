package com.gft.atr.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "receita")
public class Receita implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3137986883143018631L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "O nome deve ser preenchido!")
	private String nome;
	
	private String descricao;
	
	private int tempoPreparo;
	
	private int rendimento;
	
	@Lob
	private String imagem;
	
	@OneToMany(mappedBy = "receitaIngredientePK.receita"
			, fetch=FetchType.LAZY
			, cascade = { CascadeType.ALL }
			, orphanRemoval = true)
	private List<ReceitaIngrediente> ingredientes = new ArrayList<>();;
	
	@OneToMany(mappedBy = "receita", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<ModoPreparo> modosPreparo = new ArrayList<>();
	
	@OneToMany(mappedBy = "receita", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private List<Avaliacao> avaliacoes = new ArrayList<>();
	
	@ManyToOne
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
	private Usuario usuario;
	
	public Receita() {}

	public Receita(Long id, @NotEmpty(message = "O nome deve ser preenchido!") String nome, String descricao,
			int tempoPreparo, int rendimento, String imagem, List<ReceitaIngrediente> ingredientes,
			List<ModoPreparo> modosPreparo, List<Avaliacao> avaliacoes, Usuario usuario) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.tempoPreparo = tempoPreparo;
		this.rendimento = rendimento;
		this.imagem = imagem;
		this.ingredientes = ingredientes;
		this.modosPreparo = modosPreparo;
		this.avaliacoes = avaliacoes;
		this.usuario = usuario;
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

	public int getTempoPreparo() {
		return tempoPreparo;
	}

	public void setTempoPreparo(int tempoPreparo) {
		this.tempoPreparo = tempoPreparo;
	}

	public int getRendimento() {
		return rendimento;
	}

	public void setRendimento(int rendimento) {
		this.rendimento = rendimento;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public List<ReceitaIngrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<ReceitaIngrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public List<ModoPreparo> getModosPreparo() {
		return modosPreparo;
	}

	public void setModosPreparo(List<ModoPreparo> modosPreparo) {
		this.modosPreparo = modosPreparo;
	}

	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(avaliacoes, descricao, id, imagem, ingredientes, modosPreparo, nome, rendimento, tempoPreparo,
				usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Receita other = (Receita) obj;
		return Objects.equals(avaliacoes, other.avaliacoes) && Objects.equals(descricao, other.descricao)
				&& Objects.equals(id, other.id) && Objects.equals(imagem, other.imagem)
				&& Objects.equals(ingredientes, other.ingredientes) && Objects.equals(modosPreparo, other.modosPreparo)
				&& Objects.equals(nome, other.nome) && rendimento == other.rendimento && tempoPreparo == other.tempoPreparo
				&& Objects.equals(usuario, other.usuario);
	}



}
