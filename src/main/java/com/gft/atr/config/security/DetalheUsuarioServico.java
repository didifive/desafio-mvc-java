package com.gft.atr.config.security;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gft.atr.entities.Usuario;
import com.gft.atr.repositories.UsuarioRepository;

@Service
@Transactional
public class DetalheUsuarioServico implements UserDetailsService {
	
	private UsuarioRepository usuarioRepository;
	
	public DetalheUsuarioServico(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepository.findByNomeUsuario(username);
		
		if(usuario != null && usuario.isAtivo()) {
			Set<GrantedAuthority> papeisDoUsuario = new HashSet<>();
			usuario.getPermissoes().forEach(p -> {
				GrantedAuthority pp = new SimpleGrantedAuthority(p.getNome());
				papeisDoUsuario.add(pp);
			});
			return new User(usuario.getNomeUsuario(), usuario.getSenha(), papeisDoUsuario);
		} else {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
	}

}
