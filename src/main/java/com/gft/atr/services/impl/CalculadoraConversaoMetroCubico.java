package com.gft.atr.services.impl;

import static com.gft.atr.enums.UnidadeReferencia.LITRO;

import com.gft.atr.entities.UnidadeMedida;
import com.gft.atr.exceptions.ImpossivelCalcularFatorConversaoException;
import com.gft.atr.services.CalculadoraConversao;

public class CalculadoraConversaoMetroCubico implements CalculadoraConversao{
	
	public double calculaFatorConversao(UnidadeMedida outraUnidade) throws ImpossivelCalcularFatorConversaoException{
		
		return LITRO.getFator().calculaFatorConversao(outraUnidade)/1000;
		
	}

}
