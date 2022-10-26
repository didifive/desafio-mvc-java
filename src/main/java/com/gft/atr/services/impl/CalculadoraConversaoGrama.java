package com.gft.atr.services.impl;

import com.gft.atr.entities.UnidadeMedida;
import com.gft.atr.enums.TipoReferencia;
import com.gft.atr.exceptions.ImpossivelCalcularFatorConversaoException;
import com.gft.atr.services.CalculadoraConversao;

public class CalculadoraConversaoGrama implements CalculadoraConversao{
	
	public double calculaFatorConversao(UnidadeMedida outraUnidade) throws ImpossivelCalcularFatorConversaoException {
		if (outraUnidade.getUnidadeReferencia().getTipoReferencia() != TipoReferencia.MASSA) {
			throw new ImpossivelCalcularFatorConversaoException(outraUnidade.getUnidadeReferencia().getTipoReferencia());
		}
		
		return switch (outraUnidade.getUnidadeReferencia()) {
  		case GRAMA:
  			yield 1;
  		case KILOGRAMA:
  			yield 1000;
  		default:
  			throw new IllegalArgumentException("Unexpected value: " + outraUnidade.getUnidadeReferencia());
		};
	}

}
