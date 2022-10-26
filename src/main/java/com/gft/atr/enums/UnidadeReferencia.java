package com.gft.atr.enums;

import com.gft.atr.services.CalculadoraConversao;
import com.gft.atr.services.impl.CalculadoraConversaoCentimetroCubico;
import com.gft.atr.services.impl.CalculadoraConversaoGrama;
import com.gft.atr.services.impl.CalculadoraConversaoKilograma;
import com.gft.atr.services.impl.CalculadoraConversaoLitro;
import com.gft.atr.services.impl.CalculadoraConversaoMetroCubico;
import com.gft.atr.services.impl.CalculadoraConversaoMililitro;

public enum UnidadeReferencia {
	/**
   * Tipo Volume
   */
	MILILITRO("mililitro", "ml", new CalculadoraConversaoMililitro(), TipoReferencia.VOLUME)
	, LITRO("litro", "l", new CalculadoraConversaoLitro(), TipoReferencia.VOLUME)
	, CENTIMETRO_CUBICO("centímetro cúbico", "cm³", new CalculadoraConversaoCentimetroCubico(), TipoReferencia.VOLUME)
	, METRO_CUBICO("metro cúbico", "m³", new CalculadoraConversaoMetroCubico(), TipoReferencia.VOLUME)
	
	/**
   * Tipo Massa
   */
	,GRAMA("grama", "g", new CalculadoraConversaoGrama(), TipoReferencia.MASSA)
	,KILOGRAMA("kilograma", "Kg", new CalculadoraConversaoKilograma(), TipoReferencia.MASSA)
  
	/**
   * Tipo Geral não calcula fator de conversão
   */
	, UNIDADE ("unidade", "unid", null, TipoReferencia.GERAL);
		
	
	String nome;
	String abreviacao;
	CalculadoraConversao calculadoraConversao;
	TipoReferencia tipoReferencia;
	
	UnidadeReferencia(String nome
											, String abreviacao
											, CalculadoraConversao calculadoraConversao
											, TipoReferencia tipoReferencia
	){
		this.nome = nome;
		this.abreviacao = abreviacao;
		this.calculadoraConversao = calculadoraConversao;
		this.tipoReferencia = tipoReferencia;
	}
	
	public String getNome() {
		return nome;
	}

	public String getAbreviacao() {
		return abreviacao;
	}

	public TipoReferencia getTipoReferencia() {
		return tipoReferencia;
	}

	public CalculadoraConversao getFator() {
		return calculadoraConversao;
	}

}
