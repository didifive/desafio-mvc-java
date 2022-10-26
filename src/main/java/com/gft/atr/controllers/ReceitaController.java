package com.gft.atr.controllers;

import static com.gft.atr.enums.ModelAndViewFileObject.*;
import static com.gft.atr.enums.ModelAndViewFileObject.FORM;
import static com.gft.atr.enums.ModelAndViewFileObject.LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_BUSCA_NOME;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_BUSCA_NOME_INGREDIENTE;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_BUSCA_USUARIO;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_ERRO_UPLOAD_IMAGEM;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_INGREDIENTE_LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_MENSAGEM;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_MENSAGEM_ERRO;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_RECEITA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_RECEITA_LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_RECEITA_MEDIA_AVALIACAO;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_UNIDADE_MEDIDA_LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_USUARIO_LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_CADASTRO;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_DETALHE;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_EXCLUIR;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_RECEITA;
import static com.gft.atr.enums.ModelAndViewFileObject.RECEITA;
import static com.gft.atr.enums.ModelAndViewFileObject.REDIRECT;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.gft.atr.entities.Avaliacao;
import com.gft.atr.entities.Ingrediente;
import com.gft.atr.entities.ModoPreparo;
import com.gft.atr.entities.Receita;
import com.gft.atr.entities.ReceitaIngrediente;
import com.gft.atr.entities.ReceitaIngredientePK;
import com.gft.atr.entities.UnidadeMedida;
import com.gft.atr.entities.builders.AvaliacaoBuilder;
import com.gft.atr.entities.builders.ModoPreparoBuilder;
import com.gft.atr.entities.builders.ReceitaIngredienteBuilder;
import com.gft.atr.exceptions.ImagemComFormatoIncompativelException;
import com.gft.atr.exceptions.ImagemComTamanhoMaiorQuePermitidoException;
import com.gft.atr.exceptions.ReceitaNaoEncontradaException;
import com.gft.atr.exceptions.UsuarioNaoEncontradoException;
import com.gft.atr.services.IngredienteService;
import com.gft.atr.services.ReceitaService;
import com.gft.atr.services.UnidadeMedidaService;
import com.gft.atr.services.UsuarioService;

@Controller
@RequestMapping(PATH_RECEITA)
public class ReceitaController {
	
	@Autowired
	ReceitaService receitaService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	IngredienteService ingredienteService;
	
	@Autowired
	UnidadeMedidaService unidadeMedidaService;
	
	/**
	 * CRUD - Create/Update -  cadastroReceita
	 * @param Long
	 * @return ModelAndView
	 */
	@GetMapping(PATH_CADASTRO)
	public ModelAndView cadastroReceita(@RequestParam(required = false) Long id) {
		
		ModelAndView mv = new ModelAndView(RECEITA+FORM);
		
		Receita receita;
		
		receita = obterReceita(id, mv);
		
		mv.addObject(OBJETO_RECEITA, receita);
		
		mv.addObject(OBJETO_INGREDIENTE_LISTA, ingredienteService.listarIngredientes(null));
		mv.addObject(OBJETO_UNIDADE_MEDIDA_LISTA, unidadeMedidaService.listarUnidadesMedida(null));
		
		return mv;
	
	}
	
	/**
	 * CRUD - Create/Update - addRowIngrediente
	 * @param addRowIngrediente
	 * @param Receita
	 * @return ModelAndView
	 */
	@PostMapping(value=PATH_CADASTRO, params={"addRowIngrediente"})
	public ModelAndView addRowIngrediente(
					final Receita receita, final BindingResult bindingResult
					,RedirectAttributes redirectAttributes) {
		
			List<Ingrediente> ingredientes = ingredienteService.listarIngredientes(null);
			List<UnidadeMedida> unidadesMedida = unidadeMedidaService.listarUnidadesMedida(null);
		
			ModelAndView mv = new ModelAndView(RECEITA+FORM);
			
			ReceitaIngrediente item = new ReceitaIngredienteBuilder()
																							.withReceitaIngredientePK(new ReceitaIngredientePK(receita, ingredientes.get(0)))
																							.withUnidadeMedida(unidadesMedida.get(0))
																							.withQuantidade(1).build();
		
	    receita.getIngredientes().add(item);			
	    
	    mv.addObject(OBJETO_RECEITA, receita);
			mv.addObject(OBJETO_INGREDIENTE_LISTA, ingredientes);
			mv.addObject(OBJETO_UNIDADE_MEDIDA_LISTA, unidadesMedida);
	    
	    return mv;
	}

	/**
	 * CRUD - Create/Update - removeRowIngrediente
	 * @param removeRowIngrediente
	 * @param Receita
	 * @return ModelAndView
	 */
	@PostMapping(value=PATH_CADASTRO, params={"removeRowIngrediente"})
	public ModelAndView removeRowIngrediente(
	        final Receita receita, final BindingResult bindingResult
	        ,final HttpServletRequest req) {
	    
			ModelAndView mv = new ModelAndView(RECEITA+FORM);
		
			final Integer rowId = Integer.parseInt(req.getParameter("removeRowIngrediente"));
	    receita.getIngredientes().remove(rowId.intValue());		
	    
	    mv.addObject(OBJETO_RECEITA, receita);
			mv.addObject(OBJETO_INGREDIENTE_LISTA, ingredienteService.listarIngredientes(null));
			mv.addObject(OBJETO_UNIDADE_MEDIDA_LISTA, unidadeMedidaService.listarUnidadesMedida(null));
	    
	    return mv;
	    
	}
	
	/**
	 * CRUD - Create/Update - addRowModoPreparo
	 * @param addRowModoPreparo
	 * @param Receita
	 * @return ModelAndView
	 */
	@PostMapping(value=PATH_CADASTRO, params={"addRowModoPreparo"})
	public ModelAndView addRowModoPreparo(
					final Receita receita, final BindingResult bindingResult
					,RedirectAttributes redirectAttributes) {
		
			ModelAndView mv = new ModelAndView(RECEITA+FORM);
			
			ModoPreparo modoPreparo = new ModoPreparoBuilder()
																							.withReceita(receita)
																							.build();
		
	    receita.getModosPreparo().add(modoPreparo);
	    
	    mv.addObject(OBJETO_RECEITA, receita);
			mv.addObject(OBJETO_INGREDIENTE_LISTA, ingredienteService.listarIngredientes(null));
			mv.addObject(OBJETO_UNIDADE_MEDIDA_LISTA, unidadeMedidaService.listarUnidadesMedida(null));
	    
	    return mv;
	}

	/**
	 * CRUD - Create/Update - removeRowModoPreparo
	 * @param removeRowModoPreparo
	 * @param Receita
	 * @return ModelAndView
	 */
	@PostMapping(value=PATH_CADASTRO, params={"removeRowModoPreparo"})
	public ModelAndView removeRowModoPreparo(
	        final Receita receita, final BindingResult bindingResult
	        ,final HttpServletRequest req) {
	    
			ModelAndView mv = new ModelAndView(RECEITA+FORM);
		
			final Integer rowId = Integer.parseInt(req.getParameter("removeRowModoPreparo"));
			receita.getModosPreparo().remove(rowId.intValue());		
	    
	    mv.addObject(OBJETO_RECEITA, receita);
			mv.addObject(OBJETO_INGREDIENTE_LISTA, ingredienteService.listarIngredientes(null));
			mv.addObject(OBJETO_UNIDADE_MEDIDA_LISTA, unidadeMedidaService.listarUnidadesMedida(null));
	    
	    return mv;
	    
	}
	
	/**
	 * CRUD - Create/Update -  salvarReceita
	 * @param Receita
	 * @param BindingResult
	 * @param RedirectAttributes
	 * @param String
	 * @param MultipartFile
	 * @param String
	 * @return ModelAndView
	 * 
	 * Este método utiliza o view "redirect:/" para redirecionar para página de cadastro.
	 */
	@PostMapping(PATH_CADASTRO)
	public ModelAndView salvarReceita(
			@Valid Receita receita
			, BindingResult bindingResult
			, RedirectAttributes redirectAttributes
			, @RequestParam(required = false) String receitaImagemUrl
			, @RequestParam(required = false) MultipartFile receitaImagem
			, @RequestParam(required = false) String nomeUsuario) {
		
		ModelAndView mv = new ModelAndView();
		
		if(bindingResult.hasErrors()) {
			return erroCadastro(receita, redirectAttributes, mv, null);
		}
		
		if(receitaImagemUrl != null && !receitaImagemUrl.isBlank()) {
			try {
				receitaService.adicionarImagemURL(receita, receitaImagemUrl);
			} catch (ImagemComFormatoIncompativelException e) {
				return erroCadastro(receita, redirectAttributes, mv, e.getMessage());
			}
  	}
  	
  	if (receitaImagem != null && !receitaImagem.isEmpty()) {
  		try {
				receitaService.adicionarImagemArquivo(receita, receitaImagem);
			} catch (ImagemComTamanhoMaiorQuePermitidoException | ImagemComFormatoIncompativelException e) {
				return erroCadastro(receita, redirectAttributes, mv, e.getMessage());
			}
		
  	}
  	
  	if(receita.getId() == null) {
			try {
				receita.setUsuario(usuarioService.obterUsuario(nomeUsuario));
			} catch (UsuarioNaoEncontradoException e) {
				return erroCadastro(receita, redirectAttributes, mv, e.getMessage());
			}
		}
		
  	Receita receitaSalva = new Receita();
		try {
			receitaSalva = receitaService.salvarReceita(receita);
		} catch (Exception e) {
			return erroCadastro(receita, redirectAttributes, mv, e.getMessage());
		}
		
		redirectAttributes.addFlashAttribute(OBJETO_RECEITA, receitaSalva);
		redirectAttributes.addFlashAttribute(OBJETO_INGREDIENTE_LISTA, ingredienteService.listarIngredientes(null));
		redirectAttributes.addFlashAttribute(OBJETO_UNIDADE_MEDIDA_LISTA, unidadeMedidaService.listarUnidadesMedida(null));
		
		redirectAttributes.addFlashAttribute(
				OBJETO_MENSAGEM
				, String.format("Receita #%d - %s salvo com sucesso.", receitaSalva.getId(), receitaSalva.getNome()));
		
		mv.setViewName(REDIRECT
    									+PATH_RECEITA
    									+PATH_CADASTRO
    									+"?id="+receitaSalva.getId());
		
		return mv;
	
	}
	
	/**
	 * CRUD - Read - detalheReceita
	 * @param Long
	 * @return ModelAndView
	 */
	@GetMapping(PATH_DETALHE)
	public ModelAndView detalheReceita(@RequestParam Long id) {
		
		ModelAndView mv = new ModelAndView(RECEITA+DETALHE);
		
		Receita receita = obterReceita(id, mv);
		
		double receitaMediaAvaliacao = 0;
		if (receita.getAvaliacoes() != null) {
			receitaMediaAvaliacao = receita.getAvaliacoes().stream()
                          				.mapToDouble(Avaliacao::getNota)
                          				.average().orElse(0);
		}
		
		mv.addObject(OBJETO_RECEITA_MEDIA_AVALIACAO, receitaMediaAvaliacao);
		
		mv.addObject(OBJETO_RECEITA, receita);
		
		return mv;
	
	}
	
	/**
	 * CRUD - Read - listarReceitas
	 * @param String
	 * @param String
	 * @param Long
	 * @return ModelAndView
	 */
	@GetMapping()
	public ModelAndView listarReceitas(String nome
																			, String nomeIngrediente
																			, Long idUsuario) {
		
		ModelAndView mv = new ModelAndView(RECEITA+LISTA);
		
		try {
			mv.addObject(OBJETO_RECEITA_LISTA, receitaService.listarReceitas(nome, nomeIngrediente, idUsuario));
		} catch (UsuarioNaoEncontradoException e) {
			mv.addObject(OBJETO_MENSAGEM_ERRO, e.getMessage());
		}
		
		mv.addObject(OBJETO_INGREDIENTE_LISTA, ingredienteService.listarIngredientes(null));
		mv.addObject(OBJETO_USUARIO_LISTA, usuarioService.listarUsuarios(null));
		
		mv.addObject(OBJETO_BUSCA_NOME, nome);
		mv.addObject(OBJETO_BUSCA_NOME_INGREDIENTE, nomeIngrediente);
		mv.addObject(OBJETO_BUSCA_USUARIO, idUsuario);
		
		return mv;
	
	}
	
	/**
	 * CRUD - Delete -  excluirReceita
	 * @param Long
	 * @return void
	 * 
	 * Este método utiliza o view "redirect:/" para redirecionar para lista.
	 */
	@GetMapping(PATH_EXCLUIR)
	public ModelAndView excluirReceita(
			@RequestParam Long id
			, RedirectAttributes redirectAttributes
	){
		
		ModelAndView mv = new ModelAndView(REDIRECT+PATH_RECEITA);
		
		try {
			receitaService.excluirReceita(id);
			redirectAttributes.addFlashAttribute(
					OBJETO_MENSAGEM
					, String.format("Receita com id %d foi EXCLUÍDA com sucesso.", id));
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(
					OBJETO_MENSAGEM_ERRO
					, "Problema ao excluir: " + e.getMessage());
		}
		
		return mv;
		
	}
	
	
	/**
	 * Private method obterReceita
	 * @param Long
	 * @param ModelAndView
	 * @return Receita
	 */
	private Receita obterReceita(Long id, ModelAndView mv) {
		Receita receita;
		if(id == null) {
			receita = new Receita();
		} else {
  		try {
  			receita = receitaService.obterReceita(id);
  		} catch (ReceitaNaoEncontradaException e) {
  			receita = new Receita();
  			mv.addObject(OBJETO_MENSAGEM_ERRO,e.getMessage());
  		}
		}
		return receita;
	}

	/**
	 * Private method erroCadastro
	 * @param Receita
	 * @param RedirectAttributes
	 * @param ModelAndView
	 * @param String
	 * @return ModelAndView
	 */
	private ModelAndView erroCadastro(
			Receita receita
			, RedirectAttributes redirectAttributes
			, ModelAndView mv
			, String errorMessage) {
		
		String objetoParaErro = OBJETO_ERRO_UPLOAD_IMAGEM;
		if(errorMessage!=null && errorMessage.startsWith("Multiple representations of the same entity")) {
			objetoParaErro = OBJETO_MENSAGEM_ERRO;
			errorMessage = "A receita não foi salva. Não é possível adicionar duas vezes o mesmo ingrediente!";
		}
	
		if(receita.getId() != null) {
			mv.setViewName(REDIRECT
					+PATH_RECEITA
					+PATH_CADASTRO
					+"?id="+receita.getId());
			redirectAttributes.addFlashAttribute(OBJETO_RECEITA, receita);
			redirectAttributes.addFlashAttribute(objetoParaErro, errorMessage);			
		} else {
  		mv.setViewName(RECEITA+FORM);
  		mv.addObject(OBJETO_RECEITA, receita);
  		mv.addObject(objetoParaErro, errorMessage);
		}
		return mv;
	}
	
	@PostMapping(PATH_AVALIACAO)
	public ModelAndView avaliacaoReceita(Long receitaId
																				, Double score
																				, String scoreRatyUser) {
		
		ModelAndView mv = new ModelAndView(REDIRECT+
														PATH_RECEITA
														+PATH_DETALHE
														+"?id="+receitaId);
		
		Receita receita = new Receita();
		try {
			receita = receitaService.obterReceita(receitaId);
		} catch (ReceitaNaoEncontradaException e1) {
			e1.printStackTrace();
		}
		
		if(receita.getUsuario().getNomeUsuario().equals(scoreRatyUser))
			return mv;
		
		if (!receita.getAvaliacoes().stream()
												.filter(a -> a.getUsuario().getNomeUsuario().equals(scoreRatyUser))
												.toList()
												.isEmpty())
		{
			receita.getAvaliacoes().forEach(a -> {
				if (a.getUsuario().getNomeUsuario().equals(scoreRatyUser)) {
					a.setNota(score);
				}
			});
			
			receitaService.salvarReceita(receita);
			
			return mv;									
		}
		
		Avaliacao avaliacao = new Avaliacao();
		try {
			avaliacao = new AvaliacaoBuilder()
																		.withNota(score)
																		.withUsuario(usuarioService.obterUsuario(scoreRatyUser))
																		.withReceita(receita)
																		.build();
		} catch (UsuarioNaoEncontradoException e) {
			e.printStackTrace();
		}
		receita.getAvaliacoes().add(avaliacao);
		
		receitaService.salvarReceita(receita);
		
		return mv;
	
	}

	
}
	
