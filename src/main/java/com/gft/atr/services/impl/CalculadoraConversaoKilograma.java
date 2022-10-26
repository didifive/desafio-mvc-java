package com.gft.atr.services.impl;

import com.gft.atr.entities.UnidadeMedida;
import com.gft.atr.exceptions.ImpossivelCalcularFatorConversaoException;
import com.gft.atr.services.CalculadoraConversao;

import static com.gft.atr.enums.UnidadeReferencia.GRAMA;

public class CalculadoraConversaoKilograma implements CalculadoraConversao{
	
	public double calculaFatorConversao(UnidadeMedida outraUnidade) throws ImpossivelCalcularFatorConversaoException{
		
		return GRAMA.getFator().calculaFatorConversao(outraUnidade)/1000;
		
	}

}
