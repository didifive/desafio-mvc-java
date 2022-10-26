package com.gft.atr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.atr.entities.UnidadeMedida;
import com.gft.atr.exceptions.ImpossivelCalcularFatorConversaoException;
import com.gft.atr.exceptions.UnidadeMedidaNaoEncontradaException;
import com.gft.atr.repositories.UnidadeMedidaRepository;

@Service
public class UnidadeMedidaService {
	
	@Autowired
	private UnidadeMedidaRepository unidadeMedidaRepository;
	
	/**
	 * CRUD - Create / Update
	 * @param UnidadeMedida
	 * @return UnidadeMedida
	 */
	public UnidadeMedida salvarUnidadeMedida(UnidadeMedida unidadeMedida) {
		return unidadeMedidaRepository.save(unidadeMedida);
		
	}
	
	/**
	 * CRUD - Read
	 * @param String 
	 * @return List<UnidadeMedida>
	 */
	public List<UnidadeMedida> listarUnidadesMedida(String nome) {
		
		if(nome!=null)
			return listarUnidadesMedidaPorNome(nome);

		return listarTodasUnidadesMedidas();
	
	}
	
	private List<UnidadeMedida> listarUnidadesMedidaPorNome(String nome) {
		
		return unidadeMedidaRepository.findByNomeContains(nome);
		
	}
	
	private List<UnidadeMedida> listarTodasUnidadesMedidas() {
		
		return unidadeMedidaRepository.findAll();
	
	}
	
	/**
	 * CRUD - Read
	 * @param Long
	 * @return UnidadeMedida
	 * @throws UnidadeMedidaNaoEncontradaException
	 */
	public UnidadeMedida obterUnidadeMedida(Long id) throws UnidadeMedidaNaoEncontradaException {
		
		Optional<UnidadeMedida> unidadeMedida = unidadeMedidaRepository.findById(id);
		if(unidadeMedida.isEmpty()) {
			throw new UnidadeMedidaNaoEncontradaException(id);
		}
		
		return unidadeMedida.get();
		
	}

	/**
	 * CRUD - Delete
	 * @param Long
	 * @throws UnidadeMedidaNaoEncontradaException
	 */
	public void excluirUnidadeMedida(Long id) throws UnidadeMedidaNaoEncontradaException {
		
		obterUnidadeMedida(id);

		unidadeMedidaRepository.deleteById(id);
		
	}
	
	/**
	 * 
	 * @param UnidadeMedida
	 * @param UnidadeMedida
	 * @return double
	 * @throws ImpossivelCalcularFatorConversaoException
	 */
	public static double calculaConversao(UnidadeMedida unidade1, UnidadeMedida unidade2) throws ImpossivelCalcularFatorConversaoException {
		double fatorConversao = unidade1.getUnidadeReferencia().getFator().calculaFatorConversao(unidade2);
		
		return unidade1.getQuantidadeUnidadeReferencia() / unidade2.getQuantidadeUnidadeReferencia() * fatorConversao;
		
	}

}
