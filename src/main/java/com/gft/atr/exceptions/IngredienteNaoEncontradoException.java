package com.gft.atr.exceptions;

public class IngredienteNaoEncontradoException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IngredienteNaoEncontradoException(Long id) {
		super(String.format("Ingrediente com Id %d não foi encontrado!", id));
	}

}
