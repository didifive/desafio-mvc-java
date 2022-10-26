package com.gft.atr.exceptions;

public class ImagemComTamanhoMaiorQuePermitidoException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ImagemComTamanhoMaiorQuePermitidoException() {
		super("Arquivo de imagem selecionado tem tamanho maior que o suportado!");
	}

}
