package com.gft.atr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.atr.entities.Receita;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long>{
	
	List<Receita> findAllByNomeContains(String nome);
	
	List<Receita> findAllByNomeContainsAndUsuarioId(String nome, Long idUsuario);
	
	List<Receita> findAllByNomeContainsAndIngredientesReceitaIngredientePKIngredienteNomeContains(String nome, String nomeIngrediente);
	
	List<Receita>
		findAllByNomeContainsAndIngredientesReceitaIngredientePKIngredienteNomeContainsAndUsuarioId(
				String nome
				, String nomeIngrediente
				, Long idUsuario);
}
