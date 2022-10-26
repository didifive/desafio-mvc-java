package com.gft.atr.exceptions;

public class ImagemComFormatoIncompativelException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ImagemComFormatoIncompativelException() {
		super("Formato da imagem não é compatível. Enviar somente arquivos ou URLs de arquivos com extensões png, jpg ou jpeg!");
	}

}
