package com.gft.atr.exceptions;

public class ReceitaNaoEncontradaException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReceitaNaoEncontradaException(Long id) {
		super(String.format("Receita com Id %d não foi encontrada!", id));
	}

}
