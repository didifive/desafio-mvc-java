package com.gft.atr.controllers;

import static com.gft.atr.enums.ModelAndViewFileObject.*;
import static com.gft.atr.enums.ModelAndViewFileObject.INDEX;
import static com.gft.atr.enums.ModelAndViewFileObject.INSTALACAO;
import static com.gft.atr.enums.ModelAndViewFileObject.LOGIN;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_INGREDIENTE_QUANTIDADE;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_MENSAGEM;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_MENSAGEM_ERRO;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_RECEITA_LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_RECEITA_MEDIA_AVALIACAO;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_RECEITA_QUANTIDADE;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_UNIDADE_MEDIDA_QUANTIDADE;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_USUARIO;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_USUARIO_LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_USUARIO_QUANTIDADE;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_CADASTRAR_USUARIO;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_INSTALACAO;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_LOGIN;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_ROOT;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_SOBRE;
import static com.gft.atr.enums.ModelAndViewFileObject.REDIRECT;
import static com.gft.atr.enums.ModelAndViewFileObject.SOBRE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gft.atr.entities.Avaliacao;
import com.gft.atr.entities.Ingrediente;
import com.gft.atr.entities.Permissao;
import com.gft.atr.entities.Receita;
import com.gft.atr.entities.UnidadeMedida;
import com.gft.atr.entities.Usuario;
import com.gft.atr.enums.TipoReferencia;
import com.gft.atr.exceptions.ImpossivelCalcularFatorConversaoException;
import com.gft.atr.exceptions.IngredienteNaoEncontradoException;
import com.gft.atr.exceptions.UnidadeMedidaNaoEncontradaException;
import com.gft.atr.exceptions.UsuarioNaoEncontradoException;
import com.gft.atr.repositories.PermissaoRepository;
import com.gft.atr.services.ConversaoUnidadeMedidaService;
import com.gft.atr.services.EmailSenderService;
import com.gft.atr.services.IngredienteService;
import com.gft.atr.services.PrincipalService;
import com.gft.atr.services.ReceitaService;
import com.gft.atr.services.UnidadeMedidaService;
import com.gft.atr.services.UsuarioService;
import com.gft.atr.services.dbpopulate.ReceitaDBPopulateService;

@Controller
@RequestMapping(PATH_ROOT)
public class PrincipalController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ConversaoUnidadeMedidaService conversaoUnidadeMedidaService;
	
	@Autowired
	PrincipalService principalService;
	
	@Autowired
	ReceitaService receitaService;
	
	@Autowired
	IngredienteService ingredienteService;
	
	@Autowired
	UnidadeMedidaService unidadeMedidaService;
	
	@Autowired
	PermissaoRepository permissaoRepository;
	
	@Autowired
	ReceitaDBPopulateService receitaDBPopulateService;
	
	@Autowired
	EmailSenderService emailSenderService;
	
	@GetMapping()
	public ModelAndView viewIndex() {
		
		ModelAndView mv = new ModelAndView(INDEX);
		
		if (!principalService.verificaSeInstalacaoFoiEfetuada()) {
			mv.setViewName(REDIRECT+PATH_INSTALACAO);
			return mv;
		}
		
		listaReceitasParaMV(mv);
		
		return mv;
	
	}
	
	@GetMapping(PATH_INSTALACAO)
	public ModelAndView viewInstalacao() {
		
		ModelAndView mv = new ModelAndView(INSTALACAO);
		
		if (principalService.verificaSeInstalacaoFoiEfetuada()) {
			mv.setViewName(REDIRECT);
			return mv;
		}
		
		return mv;
	
	}
	
	@PostMapping(PATH_INSTALACAO)
	public ModelAndView realizaInstalacao() {
		
		ModelAndView mv = new ModelAndView(REDIRECT);
		
		if (principalService.verificaSeInstalacaoFoiEfetuada()) {
			return mv;
		}
		
		receitaDBPopulateService.popularReceita();
		
		return mv;
	
	}
	

	@GetMapping(PATH_SOBRE)
	public ModelAndView viewSobre() {
		
		ModelAndView mv = new ModelAndView(SOBRE);
		
		if (!principalService.verificaSeInstalacaoFoiEfetuada()) {
			mv.setViewName(REDIRECT+PATH_INSTALACAO);
			return mv;
		}
		
		mv.addObject(OBJETO_USUARIO_QUANTIDADE, principalService.quantidadeListas().get("usuarios"));
		mv.addObject(OBJETO_UNIDADE_MEDIDA_QUANTIDADE, principalService.quantidadeListas().get("unidadesMedida"));
		mv.addObject(OBJETO_INGREDIENTE_QUANTIDADE, principalService.quantidadeListas().get("ingredientes"));
		mv.addObject(OBJETO_RECEITA_QUANTIDADE, principalService.quantidadeListas().get("receitas"));
		
		return mv;
	
	}
	
	@GetMapping(PATH_LOGIN)
	public ModelAndView login() {
		
		ModelAndView mv = new ModelAndView(LOGIN);
		
		return mv;
	
	}
	
	@GetMapping(PATH_CADASTRAR_USUARIO)
	public ModelAndView cadastrarUsuario() {
		
		ModelAndView mv = new ModelAndView(CADASTRO_USUARIO);
		
		mv.addObject(OBJETO_USUARIO, new Usuario());
		
		return mv;
	
	}
	
	@PostMapping(PATH_CADASTRAR_USUARIO)
	public ModelAndView salvarUsuario(
			@Valid Usuario usuario
			, BindingResult bindingResult
			, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView();
		
		if(bindingResult.hasErrors()) {
			mv.setViewName(CADASTRO_USUARIO);
  		mv.addObject(OBJETO_USUARIO, usuario);
  		return mv;
		}
		
		Optional<Permissao> permissao = permissaoRepository.findById(2L);
		if(permissao.isPresent()) {
			usuario.setPermissoes(Arrays.asList(permissao.get()));
			usuario.setAtivo(Boolean.TRUE);
		}
		
		Usuario usuarioSalvo = null;
		try {
			usuarioSalvo = usuarioService.salvarUsuario(usuario);
		} catch (Exception e) {
			mv.setViewName(CADASTRO_USUARIO);
			mv.addObject(OBJETO_MENSAGEM_ERRO
							, "Problema ao cadastrar usuário, tente um nome de usuário e senha diferentes!");
			mv.addObject(OBJETO_USUARIO, usuario);
			return mv;
		}
		
		try {
			emailSenderService.sendEmailConfirmacaoCadastroTo(usuarioSalvo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		redirectAttributes.addFlashAttribute(
				OBJETO_MENSAGEM
				, String.format("Usuário #%d - %s salvo com sucesso.", usuarioSalvo.getId(), usuarioSalvo.getNome()));
		
		mv.setViewName(REDIRECT
    									+PATH_LOGIN);
		
		return mv;
	}
	
	private void listaReceitasParaMV(ModelAndView mv) {
		List<Receita> receitas = new ArrayList<>();
		try {
			receitas = receitaService.listarReceitas(null, null, null);
		} catch (UsuarioNaoEncontradoException e) {
			mv.addObject(OBJETO_MENSAGEM_ERRO, e.getMessage());
		}
		
		Map<Long, Double> receitaMediaAvaliacao = new HashMap<>();
				
		receitas.forEach(r -> {
			receitaMediaAvaliacao.put(
					r.getId()
					,r.getAvaliacoes().stream()
									.mapToDouble(Avaliacao::getNota)
									.average().orElse(0)
			);
		});
		
		mv.addObject(OBJETO_RECEITA_LISTA, receitas);
		mv.addObject(OBJETO_RECEITA_MEDIA_AVALIACAO, receitaMediaAvaliacao);
		mv.addObject(OBJETO_USUARIO_LISTA, usuarioService.listarUsuarios(null));
	}
	
	@GetMapping(PATH_CONVERSOR)
	public ModelAndView conversorMedidas() {
		
		ModelAndView mv = new ModelAndView(CONVERSOR);
		
		List<UnidadeMedida> listaUnidadesMedidas = unidadeMedidaService.listarUnidadesMedida(null).stream()
																									.filter(u -> !u.getUnidadeReferencia().getTipoReferencia().equals(TipoReferencia.GERAL))
																									.toList();
		
		mv.addObject(OBJETO_INGREDIENTE_LISTA, ingredienteService.listarIngredientes(null));
		mv.addObject(OBJETO_UNIDADE_MEDIDA_LISTA, listaUnidadesMedidas);
		
		return mv;
	
	}
	
	@PostMapping(PATH_CONVERSOR)
	public ModelAndView conversorMedidas(Long ingredienteId
																				, Long unidadeMedida1Id
																				, double quantidadeUnidadeMedida1
																				, Long unidadeMedida2Id
																				, RedirectAttributes redirectAttributes
																				) {
		
		ModelAndView mv = new ModelAndView(REDIRECT+PATH_CONVERSOR);
		
		UnidadeMedida unidadeMedida1;
		UnidadeMedida unidadeMedida2;
		Ingrediente ingrediente;
		try {
			unidadeMedida1 = unidadeMedidaService.obterUnidadeMedida(unidadeMedida1Id);
			unidadeMedida2 = unidadeMedidaService.obterUnidadeMedida(unidadeMedida2Id);
			ingrediente = ingredienteService.obterIngrediente(ingredienteId);
		} catch (UnidadeMedidaNaoEncontradaException | IngredienteNaoEncontradoException e1) {
			redirectAttributes.addFlashAttribute(OBJETO_MENSAGEM_ERRO, e1.getMessage());
			return mv;
		}
		
		double valorConvertido;
		try {
			valorConvertido = conversaoUnidadeMedidaService.converterMedidas(ingrediente, unidadeMedida1, quantidadeUnidadeMedida1, unidadeMedida2);
		} catch (ImpossivelCalcularFatorConversaoException e) {
			redirectAttributes.addFlashAttribute(OBJETO_MENSAGEM_ERRO, e.getMessage());
			return mv;
		}
		
		redirectAttributes.addFlashAttribute(OBJETO_INGREDIENTE_REFERENCIA, ingrediente);
		redirectAttributes.addFlashAttribute(OBJETO_UNIDADE_MEDIDA_REFERENCIA_QUANTIDADE, quantidadeUnidadeMedida1);
		redirectAttributes.addFlashAttribute(OBJETO_UNIDADE_MEDIDA_REFERENCIA, unidadeMedida1);
		redirectAttributes.addFlashAttribute(OBJETO_VALOR_CONVERTIDO, valorConvertido);
		redirectAttributes.addFlashAttribute(OBJETO_UNIDADE_MEDIDA_CONVERTIDA, unidadeMedida2);
		
		return mv;
	
	}
	
}
