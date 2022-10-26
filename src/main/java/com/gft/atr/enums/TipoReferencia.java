package com.gft.atr.enums;

public enum TipoReferencia {
	
	VOLUME("Volume", "vol"),
	MASSA("Massa", "m"),
	GERAL("Geral","g");
	
	String nome;
	String abreviacao;
	
	TipoReferencia(String nome
											, String abreviacao
	){
		this.nome = nome;
		this.abreviacao = abreviacao;
	}

	public String getNome() {
		return nome;
	}

	public String getAbreviacao() {
		return abreviacao;
	}
	
	

}
