package com.gft.atr.controllers;

import static com.gft.atr.enums.ModelAndViewFileObject.DETALHE;
import static com.gft.atr.enums.ModelAndViewFileObject.FORM;
import static com.gft.atr.enums.ModelAndViewFileObject.LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_BUSCA_NOME;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_MENSAGEM;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_MENSAGEM_ERRO;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_UNIDADE_MEDIDA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_UNIDADE_MEDIDA_LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_UNIDADE_REFERENCIA_LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_CADASTRO;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_DETALHE;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_EXCLUIR;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_UNIDADE_MEDIDA;
import static com.gft.atr.enums.ModelAndViewFileObject.REDIRECT;
import static com.gft.atr.enums.ModelAndViewFileObject.UNIDADE_MEDIDA;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gft.atr.entities.UnidadeMedida;
import com.gft.atr.enums.UnidadeReferencia;
import com.gft.atr.exceptions.UnidadeMedidaNaoEncontradaException;
import com.gft.atr.services.UnidadeMedidaService;

@Controller
@RequestMapping(PATH_UNIDADE_MEDIDA)
public class UnidadeMedidaController {
	
	@Autowired
	UnidadeMedidaService unidadeMedidaService;
	
	/**
	 * CRUD - Create/Update -  cadastroUnidadeMedida
	 * @param Long (pode ser ignorado)
	 * @return ModelAndView
	 */
	@GetMapping(PATH_CADASTRO)
	public ModelAndView cadastroUnidadeMedida(@RequestParam(required = false) Long id) {
		
		ModelAndView mv = new ModelAndView(UNIDADE_MEDIDA+FORM);
		
		UnidadeMedida unidadeMedida;
		
		unidadeMedida = obterUnidadeMedida(id, mv);
		
		mv.addObject(OBJETO_UNIDADE_MEDIDA, unidadeMedida);
		
		mv.addObject(OBJETO_UNIDADE_REFERENCIA_LISTA, UnidadeReferencia.values());
		
		return mv;
	
	}
	
	/**
	 * CRUD - Create/Update -  salvarUnidadeMedida
	 * @param UnidadeMedida
	 * @return ModelAndView
	 * 
	 * Este método utiliza o view "redirect:/" para redirecionar para página de cadastro.
	 */
	@PostMapping(PATH_CADASTRO)
	public ModelAndView salvarUnidadeMedida(
			@Valid UnidadeMedida unidadeMedida
			, BindingResult bindingResult
			, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView();
		
		if(bindingResult.hasErrors()) {
			mv.setViewName(UNIDADE_MEDIDA+FORM);
			mv.addObject(OBJETO_UNIDADE_MEDIDA, unidadeMedida);
			mv.addObject(OBJETO_UNIDADE_REFERENCIA_LISTA, UnidadeReferencia.values());
			return mv;
		}
		
		UnidadeMedida unidadeMedidaSalva;
		
		unidadeMedidaSalva = unidadeMedidaService.salvarUnidadeMedida(unidadeMedida);
		
		redirectAttributes.addFlashAttribute(OBJETO_UNIDADE_MEDIDA, unidadeMedidaSalva);
		
		redirectAttributes.addFlashAttribute(OBJETO_UNIDADE_REFERENCIA_LISTA, UnidadeReferencia.values());
		
		redirectAttributes.addFlashAttribute(
				OBJETO_MENSAGEM
				, String.format("Unidade de Medida #%d - %s salva com sucesso.", unidadeMedidaSalva.getId(), unidadeMedidaSalva.getNome()));
		
		mv.setViewName(REDIRECT
    									+PATH_UNIDADE_MEDIDA
    									+PATH_CADASTRO
    									+"?id="+unidadeMedidaSalva.getId());
		
		return mv;
	
	}
	
	/**
	 * CRUD - Read - detalheUnidadeMedida
	 * @param Long
	 * @return ModelAndView
	 */
	@GetMapping(PATH_DETALHE)
	public ModelAndView detalheUnidadeMedida(@RequestParam Long id) {
		
		ModelAndView mv = new ModelAndView(UNIDADE_MEDIDA+DETALHE);
		
		UnidadeMedida unidadeMedida = obterUnidadeMedida(id, mv);
		
		mv.addObject(OBJETO_UNIDADE_MEDIDA, unidadeMedida);
		
		return mv;
	
	}
	
	/**
	 * CRUD - Read - listarUnidadesMedida
	 * @param String (pode ser nulo)
	 * @return ModelAndView
	 */
	@GetMapping()
	public ModelAndView listarUnidadesMedida(String nome) {
		
		ModelAndView mv = new ModelAndView(UNIDADE_MEDIDA+LISTA);
		
		mv.addObject(OBJETO_UNIDADE_MEDIDA_LISTA, unidadeMedidaService.listarUnidadesMedida(nome));
		
		mv.addObject(OBJETO_BUSCA_NOME, nome);
		
		return mv;
	
	}
	
	/**
	 * CRUD - Delete -  excluirUnidadeMedida
	 * @param Long
	 * @return void
	 * 
	 * Este método utiliza o view "redirect:/" para redirecionar para lista.
	 */
	@GetMapping(PATH_EXCLUIR)
	public ModelAndView excluirUnidadeMedida(
			@RequestParam Long id
			, RedirectAttributes redirectAttributes
	){
		
		ModelAndView mv = new ModelAndView(REDIRECT+PATH_UNIDADE_MEDIDA);
		
		try {
			unidadeMedidaService.excluirUnidadeMedida(id);
			redirectAttributes.addFlashAttribute(
					OBJETO_MENSAGEM
					, String.format("Unidade de Medida com id %d foi EXCLUÍDA com sucesso.", id));
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(
					OBJETO_MENSAGEM_ERRO
					, "Problema ao excluir: " + e.getMessage());
		}
		
		return mv;
		
	}
	
	
	/**
	 * Private method obterUnidadeMedida
	 * @param Long id
	 * @param ModelAndView mv
	 * @return UnidadeMedida
	 */
	private UnidadeMedida obterUnidadeMedida(Long id, ModelAndView mv) {
		UnidadeMedida unidadeMedida;
		if(id == null) {
			unidadeMedida = new UnidadeMedida();
		} else {
  		try {
  			unidadeMedida = unidadeMedidaService.obterUnidadeMedida(id);
  		} catch (UnidadeMedidaNaoEncontradaException e) {
  			unidadeMedida = new UnidadeMedida();
  			mv.addObject(OBJETO_MENSAGEM_ERRO,e.getMessage());
  		}
		}
		return unidadeMedida;
	}

}
	
