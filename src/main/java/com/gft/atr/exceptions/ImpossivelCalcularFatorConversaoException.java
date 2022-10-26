package com.gft.atr.exceptions;

import com.gft.atr.enums.TipoReferencia;

public class ImpossivelCalcularFatorConversaoException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ImpossivelCalcularFatorConversaoException(TipoReferencia tipoReferencia) {
		super(String.format("Impossível calcular fator de conversão de unidade do tipo %s para outro tipo!", tipoReferencia.getNome()));
	}

}
