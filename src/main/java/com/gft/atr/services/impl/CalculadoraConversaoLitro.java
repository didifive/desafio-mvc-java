package com.gft.atr.services.impl;

import static com.gft.atr.enums.UnidadeReferencia.MILILITRO;

import com.gft.atr.entities.UnidadeMedida;
import com.gft.atr.exceptions.ImpossivelCalcularFatorConversaoException;
import com.gft.atr.services.CalculadoraConversao;

public class CalculadoraConversaoLitro implements CalculadoraConversao{
	
	public double calculaFatorConversao(UnidadeMedida outraUnidade) throws ImpossivelCalcularFatorConversaoException{
	
		return MILILITRO.getFator().calculaFatorConversao(outraUnidade)/1000;
		
	}

}
