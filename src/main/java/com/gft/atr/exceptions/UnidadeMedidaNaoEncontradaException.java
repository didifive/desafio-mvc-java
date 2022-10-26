package com.gft.atr.exceptions;

public class UnidadeMedidaNaoEncontradaException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnidadeMedidaNaoEncontradaException(Long id) {
		super(String.format("Unidade de Medida com Id %d não foi encontrada!", id));
	}

}
