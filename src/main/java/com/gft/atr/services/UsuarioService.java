package com.gft.atr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gft.atr.entities.Usuario;
import com.gft.atr.exceptions.UsuarioNaoEncontradoException;
import com.gft.atr.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	BCryptPasswordEncoder criptografia;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	/**
	 * CRUD - Create / Update
	 * @param Usuario
	 * @return Usuario
	 */
	public Usuario salvarUsuario(Usuario usuario) {
		String senhaCriptografia = criptografia.encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografia);
		
		return usuarioRepository.save(usuario);
		
	}
	
	/**
	 * CRUD - Read
	 * @param String 
	 * @return List<Usuario>
	 */
	public List<Usuario> listarUsuarios(String nome) {
		
		if(nome!=null)
			return listarUsuariosPorNome(nome);

		return listarTodosUsuarios();
	
	}
	
	private List<Usuario> listarUsuariosPorNome(String nome) {
		
		return usuarioRepository.findByNomeContainsAndNomeUsuarioContains(nome, nome);
		
	}
	
	private List<Usuario> listarTodosUsuarios() {
		
		return usuarioRepository.findAll();
	
	}
	
	/**
	 * CRUD - Read
	 * @param Long
	 * @return Usuario
	 * @throws UsuarioNaoEncontradoException
	 */
	public Usuario obterUsuario(Long id) throws UsuarioNaoEncontradoException {
		
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if(usuario.isEmpty()) {
			throw new UsuarioNaoEncontradoException(id);
		}
		
		return usuario.get();
		
	}
	
	public Usuario obterUsuario(String nomeUsuario) throws UsuarioNaoEncontradoException {
		
		Optional<Usuario> usuario = Optional.of(usuarioRepository.findByNomeUsuario(nomeUsuario));
		if(usuario.isEmpty()) {
			throw new UsuarioNaoEncontradoException(nomeUsuario);
		}
		
		return usuario.get();
		
	}

	/**
	 * CRUD - Update
	 * @param Long
	 * @throws UsuarioNaoEncontradoException
	 */
	public void ativarUsuario(Long id) throws UsuarioNaoEncontradoException {
		
		Usuario usuario = obterUsuario(id);
		
		if(!usuario.isAtivo()) {
			usuario.setAtivo(true);
			salvarUsuario(usuario);
		}
		
	}
	
	/**
	 * CRUD - Update
	 * @param Long
	 * @throws UsuarioNaoEncontradoException
	 */
	public void desativarUsuario(Long id) throws UsuarioNaoEncontradoException {
		
		Usuario usuario = obterUsuario(id);
		
		if(usuario.isAtivo()) {
			usuario.setAtivo(false);
			salvarUsuario(usuario);
		}
		
	}
		
	/**
	 * CRUD - Delete
	 * @param Long
	 * @throws UsuarioNaoEncontradoException
	 */
	public void excluirUsuario(Long id) throws UsuarioNaoEncontradoException {
		
		obterUsuario(id);

		usuarioRepository.deleteById(id);
		
	}

}
