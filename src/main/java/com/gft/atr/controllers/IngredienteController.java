package com.gft.atr.controllers;

import static com.gft.atr.enums.ModelAndViewFileObject.DETALHE;
import static com.gft.atr.enums.ModelAndViewFileObject.FORM;
import static com.gft.atr.enums.ModelAndViewFileObject.INGREDIENTE;
import static com.gft.atr.enums.ModelAndViewFileObject.LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_BUSCA_NOME;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_INGREDIENTE;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_ERRO_UPLOAD_IMAGEM;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_INGREDIENTE_LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_MENSAGEM;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_MENSAGEM_ERRO;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_CADASTRO;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_DETALHE;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_EXCLUIR;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_INGREDIENTE;
import static com.gft.atr.enums.ModelAndViewFileObject.REDIRECT;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gft.atr.entities.Ingrediente;
import com.gft.atr.exceptions.ImagemComFormatoIncompativelException;
import com.gft.atr.exceptions.ImagemComTamanhoMaiorQuePermitidoException;
import com.gft.atr.exceptions.IngredienteNaoEncontradoException;
import com.gft.atr.services.IngredienteService;

@Controller
@RequestMapping(PATH_INGREDIENTE)
public class IngredienteController {
	
	@Autowired
	IngredienteService ingredienteService;
	
	/**
	 * CRUD - Create/Update -  cadastroUnidadeMedida
	 * @param Long (pode ser ignorado)
	 * @return ModelAndView
	 */
	@GetMapping(PATH_CADASTRO)
	public ModelAndView cadastrarIngrediente(@RequestParam(required = false) Long id) {
		
		ModelAndView mv = new ModelAndView(INGREDIENTE+FORM);
		
		Ingrediente ingrediente;
		
		ingrediente = obterIngrediente(id, mv);
		
		mv.addObject(OBJETO_INGREDIENTE, ingrediente);
		
		return mv;
	
	}
	
	/**
	 * CRUD - Create/Update -  salvarIngrediente
	 * @param UnidadeMedida
	 * @return ModelAndView
	 * 
	 * Este método utiliza o view "redirect:/" para redirecionar para página de cadastro.
	 */
	@PostMapping(PATH_CADASTRO)
	public ModelAndView salvarIngrediente(
			@Valid Ingrediente ingrediente
			, BindingResult bindingResult
			, RedirectAttributes redirectAttributes
			, @RequestParam(required = false) String ingredienteImagemUrl
			, @RequestParam(required = false) MultipartFile ingredienteImagem) {
		
		ModelAndView mv = new ModelAndView();
		
		if(bindingResult.hasErrors()) {
			return erroCadastro(ingrediente, redirectAttributes, mv, null);
		}
		
		if(ingredienteImagemUrl != null && !ingredienteImagemUrl.isBlank()) {
			try {
				ingredienteService.adicionarImagemURL(ingrediente, ingredienteImagemUrl);
			} catch (ImagemComFormatoIncompativelException e) {
				return erroCadastro(ingrediente, redirectAttributes, mv, e.getMessage());
			}
  	}
  	
  	if (ingredienteImagem != null && !ingredienteImagem.isEmpty()) {
  		try {
				ingredienteService.adicionarImagemArquivo(ingrediente, ingredienteImagem);
			} catch (ImagemComTamanhoMaiorQuePermitidoException | ImagemComFormatoIncompativelException e) {
				return erroCadastro(ingrediente, redirectAttributes, mv, e.getMessage());
			}
		
  	}
		
  	Ingrediente ingredienteSalvo = ingredienteService.salvarIngrediente(ingrediente);
		
		redirectAttributes.addFlashAttribute(OBJETO_INGREDIENTE, ingredienteSalvo);
		
		redirectAttributes.addFlashAttribute(
				OBJETO_MENSAGEM
				, String.format("Ingrediente #%d - %s salvo com sucesso.", ingredienteSalvo.getId(), ingredienteSalvo.getNome()));
		
		mv.setViewName(REDIRECT
    									+PATH_INGREDIENTE
    									+PATH_CADASTRO
    									+"?id="+ingredienteSalvo.getId());
		
		return mv;
	
	}

	private ModelAndView erroCadastro(
			Ingrediente ingrediente
			, RedirectAttributes redirectAttributes
			, ModelAndView mv
			, String errorMessage) {
	
		if(ingrediente.getId() != null) {
			mv.setViewName(REDIRECT
					+PATH_INGREDIENTE
					+PATH_CADASTRO
					+"?id="+ingrediente.getId());
			redirectAttributes.addFlashAttribute(OBJETO_INGREDIENTE, ingrediente);
			redirectAttributes.addFlashAttribute(OBJETO_ERRO_UPLOAD_IMAGEM, errorMessage);			
		} else {
  		mv.setViewName(INGREDIENTE+FORM);
  		mv.addObject(OBJETO_INGREDIENTE, ingrediente);
  		mv.addObject(OBJETO_ERRO_UPLOAD_IMAGEM, errorMessage);
		}
		return mv;
	}
	
	/**
	 * CRUD - Read - detalheIngrediente
	 * @param Long
	 * @return ModelAndView
	 */
	@GetMapping(PATH_DETALHE)
	public ModelAndView detalheIngrediente(@RequestParam Long id) {
		
		ModelAndView mv = new ModelAndView(INGREDIENTE+DETALHE);
		
		Ingrediente ingrediente = obterIngrediente(id, mv);
		
		mv.addObject(OBJETO_INGREDIENTE, ingrediente);
		
		return mv;
	
	}
	
	/**
	 * CRUD - Read - listarIngrediente
	 * @param String (pode ser nulo)
	 * @return ModelAndView
	 */
	@GetMapping()
	public ModelAndView listarIngrediente(String nome) {
		
		ModelAndView mv = new ModelAndView(INGREDIENTE+LISTA);
		
		mv.addObject(OBJETO_INGREDIENTE_LISTA, ingredienteService.listarIngredientes(nome));
		
		mv.addObject(OBJETO_BUSCA_NOME, nome);
		
		return mv;
	
	}
	
	/**
	 * CRUD - Delete -  excluirIngrediente
	 * @param Long
	 * @return void
	 * 
	 * Este método utiliza o view "redirect:/" para redirecionar para lista.
	 */
	@GetMapping(PATH_EXCLUIR)
	public ModelAndView excluirIngrediente(
			@RequestParam Long id
			, RedirectAttributes redirectAttributes
	){
		
		ModelAndView mv = new ModelAndView(REDIRECT+PATH_INGREDIENTE);
		
		try {
			ingredienteService.excluirIngrediente(id);
			redirectAttributes.addFlashAttribute(
					OBJETO_MENSAGEM
					, String.format("Ingrediente com id %d foi EXCLUÍDO com sucesso.", id));
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(
					OBJETO_MENSAGEM_ERRO
					, "Problema ao excluir: " + e.getMessage());
		}
		
		return mv;
		
	}
	
	
	/**
	 * Private method obterIngrediente
	 * @param Long
	 * @param ModelAndView
	 * @return Ingrediente
	 */
	private Ingrediente obterIngrediente(Long id, ModelAndView mv) {
		Ingrediente ingrediente;
		if(id == null) {
			ingrediente = new Ingrediente();
		} else {
  		try {
  			ingrediente = ingredienteService.obterIngrediente(id);
  		} catch (IngredienteNaoEncontradoException e) {
  			ingrediente = new Ingrediente();
  			mv.addObject(OBJETO_MENSAGEM_ERRO,e.getMessage());
  		}
		}
		return ingrediente;
	}

}
	
