package com.gft.atr.services.impl;

import com.gft.atr.entities.UnidadeMedida;
import com.gft.atr.enums.TipoReferencia;
import com.gft.atr.exceptions.ImpossivelCalcularFatorConversaoException;
import com.gft.atr.services.CalculadoraConversao;

public class CalculadoraConversaoMililitro implements CalculadoraConversao{
	
	public double calculaFatorConversao(UnidadeMedida outraUnidade) throws ImpossivelCalcularFatorConversaoException {
		if (outraUnidade.getUnidadeReferencia().getTipoReferencia() != TipoReferencia.VOLUME) {
			throw new ImpossivelCalcularFatorConversaoException(outraUnidade.getUnidadeReferencia().getTipoReferencia());
		}
		
		return switch (outraUnidade.getUnidadeReferencia()) {
  		case MILILITRO, CENTIMETRO_CUBICO:
  			yield 1;
  		case LITRO:
  			yield 1000;
  		case METRO_CUBICO:
  			yield 1000000;
  		default:
  			throw new IllegalArgumentException("Unexpected value: " + outraUnidade.getUnidadeReferencia());
		};
	}

}
