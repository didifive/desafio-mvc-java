package com.gft.atr.services;

import com.gft.atr.entities.UnidadeMedida;
import com.gft.atr.exceptions.ImpossivelCalcularFatorConversaoException;

public interface CalculadoraConversao {

	public double calculaFatorConversao(UnidadeMedida outraUnidade) throws ImpossivelCalcularFatorConversaoException;
	
}
