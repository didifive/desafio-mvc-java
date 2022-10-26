package com.gft.atr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.atr.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Usuario findByNomeUsuario(String nome);
	
	List<Usuario> findByNomeContainsAndNomeUsuarioContains(String nome, String nomeUsuario);
	
}
