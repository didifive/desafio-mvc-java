package com.gft.atr.exceptions;

public class UsuarioNaoEncontradoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsuarioNaoEncontradoException(Long id) {
		super(String.format("Usuário com Id %d não foi encontrado!", id));
	}
	
	public UsuarioNaoEncontradoException(String userName) {
		super(String.format("Usuário com nome %s não foi encontrado!", userName));
	}
}
