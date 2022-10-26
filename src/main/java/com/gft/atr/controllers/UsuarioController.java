package com.gft.atr.controllers;

import static com.gft.atr.enums.ModelAndViewFileObject.DETALHE;
import static com.gft.atr.enums.ModelAndViewFileObject.FORM;
import static com.gft.atr.enums.ModelAndViewFileObject.LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_BUSCA_NOME;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_MENSAGEM;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_MENSAGEM_ERRO;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_PERMISSAO_LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_USUARIO;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_USUARIO_LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_CADASTRO;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_DETALHE;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_EXCLUIR;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_USUARIO;
import static com.gft.atr.enums.ModelAndViewFileObject.REDIRECT;
import static com.gft.atr.enums.ModelAndViewFileObject.USUARIO;

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

import com.gft.atr.entities.Usuario;
import com.gft.atr.exceptions.UsuarioNaoEncontradoException;
import com.gft.atr.repositories.PermissaoRepository;
import com.gft.atr.services.UsuarioService;

@Controller
@RequestMapping(PATH_USUARIO)
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	PermissaoRepository permissaoRepository;
	
	/**
	 * CRUD - Create/Update -  cadastroUsuario
	 * @param Long (pode ser ignorado)
	 * @return ModelAndView
	 */
	@GetMapping(PATH_CADASTRO)
	public ModelAndView cadastrarUsuario(@RequestParam(required = false) Long id) {
		
		ModelAndView mv = new ModelAndView(USUARIO+FORM);
		
		Usuario usuario;
		
		usuario = obterUsuario(id, mv);
		
		mv.addObject(OBJETO_USUARIO, usuario);
		
		mv.addObject(OBJETO_PERMISSAO_LISTA, permissaoRepository.findAll());
		
		return mv;
	
	}
	
	

	/**
	 * CRUD - Create/Update -  salvarUsuario
	 * @param UnidadeMedida
	 * @return ModelAndView
	 * 
	 * Este método utiliza o view "redirect:/" para redirecionar para página de cadastro.
	 */
	@PostMapping(PATH_CADASTRO)
	public ModelAndView salvarUsuario(
			@Valid Usuario usuario
			, BindingResult bindingResult
			, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView();
		
		if(bindingResult.hasErrors()) {
			mv.setViewName(USUARIO+FORM);
  		mv.addObject(OBJETO_USUARIO, usuario);
  		return mv;
		}
		
		Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);
		
		redirectAttributes.addFlashAttribute(OBJETO_USUARIO, usuarioSalvo);
		
		redirectAttributes.addFlashAttribute(
				OBJETO_MENSAGEM
				, String.format("Usuário #%d - %s salvo com sucesso.", usuarioSalvo.getId(), usuarioSalvo.getNome()));
		
		mv.setViewName(REDIRECT
    									+PATH_USUARIO
    									+PATH_CADASTRO
    									+"?id="+usuarioSalvo.getId());
		
		return mv;
	
	}

	/**
	 * CRUD - Read - detalheUsuario
	 * @param Long
	 * @return ModelAndView
	 */
	@GetMapping(PATH_DETALHE)
	public ModelAndView detalheUsuario(@RequestParam Long id) {
		
		ModelAndView mv = new ModelAndView(USUARIO+DETALHE);
		
		Usuario usuario = obterUsuario(id, mv);
		
		mv.addObject(OBJETO_USUARIO, usuario);
		
		return mv;
	
	}
	
	/**
	 * CRUD - Read - listarUsuarios
	 * @param String
	 * @return ModelAndView
	 */
	@GetMapping()
	public ModelAndView listarUsuarios(String nome) {
		
		ModelAndView mv = new ModelAndView(USUARIO+LISTA);
		
		mv.addObject(OBJETO_USUARIO_LISTA, usuarioService.listarUsuarios(nome));
		
		mv.addObject(OBJETO_BUSCA_NOME, nome);
		
		return mv;
	
	}
	
	/**
	 * CRUD - Delete -  excluirUsuario
	 * @param Long
	 * @return void
	 * 
	 * Este método utiliza o view "redirect:/" para redirecionar para lista.
	 */
	@GetMapping(PATH_EXCLUIR)
	public ModelAndView excluirUsuario(
			@RequestParam Long id
			, RedirectAttributes redirectAttributes
	){
		
		ModelAndView mv = new ModelAndView(REDIRECT+PATH_USUARIO);
		
		try {
			usuarioService.excluirUsuario(id);
			redirectAttributes.addFlashAttribute(
					OBJETO_MENSAGEM
					, String.format("Usuário com id %d foi EXCLUÍDO com sucesso.", id));
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(
					OBJETO_MENSAGEM_ERRO
					, "Problema ao excluir: " + e.getMessage());
		}
		
		return mv;
		
	}
	
	
	/**
	 * Private method obterUsuario
	 * @param Long
	 * @param ModelAndView
	 * @return Usuario
	 */
	private Usuario obterUsuario(Long id, ModelAndView mv) {
		Usuario usuario;
		if(id == null) {
			usuario = new Usuario();
		} else {
  		try {
  			usuario = usuarioService.obterUsuario(id);
  		} catch (UsuarioNaoEncontradoException e) {
  			usuario = new Usuario();
  			mv.addObject(OBJETO_MENSAGEM_ERRO,e.getMessage());
  		}
		}
		return usuario;
	}

}
