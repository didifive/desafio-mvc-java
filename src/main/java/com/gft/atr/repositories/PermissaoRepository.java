package com.gft.atr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.atr.entities.Permissao;
import com.gft.atr.entities.Usuario;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long>{
	
	List<Permissao> findByNome(String nome);
	
	List<Permissao> findByUsuariosContains(Usuario usuario);
	
}
