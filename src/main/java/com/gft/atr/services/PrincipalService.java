package com.gft.atr.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.atr.entities.Ingrediente;
import com.gft.atr.entities.Receita;
import com.gft.atr.entities.UnidadeMedida;
import com.gft.atr.entities.Usuario;
import com.gft.atr.exceptions.UsuarioNaoEncontradoException;

@Service
public class PrincipalService {
	
	@Autowired
	ReceitaService receitaService;
	
	@Autowired
	IngredienteService ingredienteService;
	
	@Autowired
	UnidadeMedidaService unidadeMedidaService;
	
	@Autowired
	UsuarioService usuarioService;

	public boolean verificaSeInstalacaoFoiEfetuada() {
		List<Receita> receitas = new ArrayList<>();
		List<Ingrediente> ingredientes = new ArrayList<>();
		List<UnidadeMedida> unidadesMedida = new ArrayList<>();
		List<Usuario> usuarios = new ArrayList<>();
		
		try {
			receitas = receitaService.listarReceitas(null, null, null);
			ingredientes = ingredienteService.listarIngredientes(null);
			unidadesMedida = unidadeMedidaService.listarUnidadesMedida(null);
			usuarios = usuarioService.listarUsuarios(null);
		} catch (UsuarioNaoEncontradoException e) {
			System.out.println("Erro fatal, alguém desconfigurou the system...");
		} 
		
		if (usuarios.isEmpty()
				&& unidadesMedida.isEmpty()
				&& ingredientes.isEmpty()
				&& receitas.isEmpty()){
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}
	
	public Map<String, Integer> quantidadeListas() {
		Map<String, Integer> quantidadeListas = new HashMap<>();
		
		Integer usuarioQuantidade = 0;
		Integer unidadeMedidaQuantidade = 0;
		Integer ingredienteQuantidade = 0;
		Integer receitaQuantidade = 0;
		
		try {
			usuarioQuantidade = usuarioService.listarUsuarios(null).size();
			unidadeMedidaQuantidade = unidadeMedidaService.listarUnidadesMedida(null).size();
			ingredienteQuantidade = ingredienteService.listarIngredientes(null).size();
			receitaQuantidade = receitaService.listarReceitas(null, null, null).size();
		} catch (UsuarioNaoEncontradoException e) {
			System.out.println("Erro fatal, alguém desconfigurou the system...");
		} 
		
		quantidadeListas.put("usuarios", usuarioQuantidade);
		quantidadeListas.put("unidadesMedida", unidadeMedidaQuantidade);
		quantidadeListas.put("ingredientes", ingredienteQuantidade);
		quantidadeListas.put("receitas", receitaQuantidade);
		
		return quantidadeListas;
		
	}

}
