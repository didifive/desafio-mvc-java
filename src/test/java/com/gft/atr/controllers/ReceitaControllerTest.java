package com.gft.atr.controllers;

import static com.gft.atr.enums.ModelAndViewFileObject.DETALHE;
import static com.gft.atr.enums.ModelAndViewFileObject.FORM;
import static com.gft.atr.enums.ModelAndViewFileObject.LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_MENSAGEM;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_MENSAGEM_ERRO;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_RECEITA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_RECEITA_LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_RECEITA_MEDIA_AVALIACAO;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_CADASTRO;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_DETALHE;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_EXCLUIR;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_RECEITA;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_ROOT;
import static com.gft.atr.enums.ModelAndViewFileObject.RECEITA;
import static com.gft.atr.enums.ModelAndViewFileObject.REDIRECT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertCompareListModelAttribute;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;

import com.gft.atr.entities.Avaliacao;
import com.gft.atr.entities.Ingrediente;
import com.gft.atr.entities.Receita;
import com.gft.atr.entities.Usuario;
import com.gft.atr.exceptions.ReceitaNaoEncontradaException;
import com.gft.atr.services.IngredienteService;
import com.gft.atr.services.ReceitaService;
import com.gft.atr.services.UnidadeMedidaService;
import com.gft.atr.services.UsuarioService;
import com.gft.atr.utils.IngredienteUtils;
import com.gft.atr.utils.ReceitaUtils;
import com.gft.atr.utils.UsuarioUtils;



@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do ReceitaController")
class ReceitaControllerTest {
	
	private static final Long ID_INVALIDO = 2L;
	
	private MockMvc mockMvc;
	
	@Mock
	Avaliacao avaliacao;
	
	@Mock
	ReceitaService receitaService;
	
	@Mock
	UsuarioService usuarioService;
	
	@Mock
	IngredienteService ingredienteService;
	
	@Mock
	UnidadeMedidaService unidadeMedidaService;
	
	@InjectMocks
  private ReceitaController receitaController;
	
	@BeforeEach
  void setup() {
      mockMvc = MockMvcBuilders.standaloneSetup(receitaController).build();
  }
	
  @Test
  @DisplayName("1. Quando GET ?? chamado em cadastro ent??o status ok e arquivo correto s??o retornados")
  void whenGETIsCalledInCadastroThenStatusOkAndCorrectFileAreReturned() throws Exception {
  
  	//then
  	mockMvc.perform(get(PATH_ROOT+PATH_RECEITA+PATH_CADASTRO))
  			.andExpectAll(
  					status().isOk(),
  					view().name(RECEITA+FORM)
  			);
  	
  }

	
  @Test
  @DisplayName("1.2. Quando GET ?? chamado em cadastro sem uma id ent??o uma nova Receita ?? retornada")
  void whenGETIsCalledInCadastroWithoutIdThenANewReceitaIsReturned() throws Exception {
  
  	//then
  	ModelAndView mv = mockMvc.perform(get(PATH_ROOT+PATH_RECEITA+PATH_CADASTRO))
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeValue(mv, OBJETO_RECEITA, new Receita());
  
  }
  
  @Test
  @DisplayName("1.3. Quando GET ?? chamado em cadastro com id v??lido ent??o a respectiva Receita ?? retornada")
  void whenGETIsCalledInCadastroWithValidIdThenTheReceitaIsReturned() throws Exception {
  	//given
  	Receita receita = ReceitaUtils.createFakeEntity();
  	String caminho = PATH_ROOT + PATH_RECEITA + PATH_CADASTRO + "?id=" + receita.getId();
  	
  	//when
  	when(receitaService.obterReceita(receita.getId())).thenReturn(receita);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeValue(mv, OBJETO_RECEITA, receita);
  }

  @Test
  @DisplayName("1.4. Quando GET ?? chamado em cadastro com id inv??lido ent??o uma nova Receita e mensagem de erro s??o retornados")
  void whenGETIsCalledInCadastroWithInvalidIdThenANewReceitaAndErrorMessageAreReturned() throws Exception {
  	//given
  	String caminho = PATH_ROOT + PATH_RECEITA + PATH_CADASTRO + "?id=" + ID_INVALIDO;
  	
  	//when
  	when(receitaService.obterReceita(ID_INVALIDO)).thenThrow(ReceitaNaoEncontradaException.class);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getModelAndView();
  	
  	assertAll(
  		"Verificar se retornou Unidade de Medida nova e existe mensagem de erro"
  		,() ->	assertModelAttributeValue(mv, OBJETO_RECEITA, new Receita())
  		,() ->	assertModelAttributeAvailable(mv, OBJETO_MENSAGEM_ERRO)
  	);
  			
  }

  @Test
  @DisplayName("2. Quando POST ?? chamado em cadastro com uma nova Receita v??lida ent??o deve salvar")
  void whenPOSTIsCalledInCadastroWithANewValidReceitaThenShouldSave() throws Exception {
  	//given
  	Receita receitaNova = ReceitaUtils.createFakeEntityWithoutId();
  	Receita receitaSalva = ReceitaUtils.createFakeEntity();
  	Usuario usuario = UsuarioUtils.createFakeEntity();
  	receitaNova.setUsuario(usuario);
  	String caminho = REDIRECT + PATH_RECEITA + PATH_CADASTRO + "?id=" + receitaSalva.getId();
  			
  	//when
  	when(usuarioService.obterUsuario(usuario.getNomeUsuario())).thenReturn(usuario);
  	when(receitaService.salvarReceita(receitaNova)).thenReturn(receitaSalva);
		
		//then
  	mockMvc.perform(post(PATH_ROOT+PATH_RECEITA+PATH_CADASTRO)
  					.param("nome", receitaNova.getNome())
  					.param("imagem", receitaNova.getImagem())
  					.param("descricao", receitaNova.getDescricao())
  					.param("nomeUsuario", usuario.getNomeUsuario())
  				)
  				.andExpectAll(
  						status().isFound()
    					,view().name(caminho)
    					,flash().attributeExists(OBJETO_RECEITA)
    					,flash().attributeExists(OBJETO_MENSAGEM)
  				)
  				.andReturn()
  				.getFlashMap();
 
  }
  
  @Test
  @DisplayName("2.2. Quando POST ?? chamado em cadastro com Receita j?? existente ent??o deve atualizar")
  void whenPOSTIsCalledInCadastroWithExistingReceitaThenShouldAtualizar() throws Exception {
  	//given
  	Receita receitaSalva = ReceitaUtils.createFakeEntity();
  	Usuario usuario = UsuarioUtils.createFakeEntity();
  	String caminho = REDIRECT + PATH_RECEITA + PATH_CADASTRO + "?id=" + receitaSalva.getId();
  			
  	//when
  	when(receitaService.salvarReceita(receitaSalva)).thenReturn(receitaSalva);
		
		//then
  	mockMvc.perform(post(PATH_ROOT+PATH_RECEITA+PATH_CADASTRO)
  					.param("id", String.valueOf(receitaSalva.getId()))
  					.param("nome", receitaSalva.getNome())
  					.param("imagem", receitaSalva.getImagem())
  					.param("descricao", receitaSalva.getDescricao())
  					.param("nomeUsuario", usuario.getNomeUsuario())
  					

  				)
  				.andExpectAll(
  						status().isFound()
    					,view().name(caminho)
    					,flash().attributeExists(OBJETO_RECEITA)
    					,flash().attributeExists(OBJETO_MENSAGEM)
  				);
 
  }
  
  @Test
  @DisplayName("2.3. Quando POST ?? chamado em cadastro com uma Receita inv??lida ent??o deve retornar erro")
  void whenPOSTIsCalledInCadastroWithAInvalidReceitaThenShouldReturnError() throws Exception {
		//then
  	ModelAndView mv = mockMvc.perform(post(PATH_ROOT+PATH_RECEITA+PATH_CADASTRO))
  				.andReturn()
  				.getModelAndView();
  	
  	System.out.println(mv);
  	
  	boolean containError = BindingResultUtils
  								.getRequiredBindingResult(mv.getModelMap(), OBJETO_RECEITA)
  								.hasErrors();
  	
  	assertTrue(containError);

  }
  
  @Test
  @DisplayName("3. Quando GET ?? chamado em detalhe com id v??lido ent??o Receita v??lida ?? retornada")
  void whenGETIsCalledInDetalheWithValidIdThenValidReceitaIsReturned() throws Exception {
  	//given
  	Receita receita = ReceitaUtils.createFakeEntity();
  	receita.setAvaliacoes(Collections.singletonList(avaliacao));
  	String caminho = PATH_ROOT + PATH_RECEITA + PATH_DETALHE + "?id=" + receita.getId();
  	
  	//when
  	when(receitaService.obterReceita(receita.getId())).thenReturn(receita);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andExpectAll(
  					status().isOk(),
  					view().name(RECEITA+DETALHE)
  			)
  			.andReturn()
  			.getModelAndView();
  	
  	assertAll(
  			"verifica se retornou a Receita e M??dia de Avalia????es"
  			, () ->	assertModelAttributeValue(mv, OBJETO_RECEITA, receita)
  			, () ->	assertModelAttributeAvailable(mv, OBJETO_RECEITA_MEDIA_AVALIACAO)
  	);
  
  }
  
  @Test
  @DisplayName("3.2. Quando GET ?? chamado em detalhe com id inv??lido ent??o mensagem de erro ?? retornada")
  void whenGETIsCalledInDetalheWithInvalidIdThenErrorMensagemIsReturned() throws Exception {
  	//given
  	String caminho = PATH_ROOT + PATH_RECEITA + PATH_DETALHE + "?id=" + ID_INVALIDO;
  	
  	//when
  	when(receitaService.obterReceita(ID_INVALIDO)).thenThrow(ReceitaNaoEncontradaException.class);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeAvailable(mv, OBJETO_MENSAGEM_ERRO);
  			
  }

	@Test
  @DisplayName("4. Quando GET ?? chamado ent??o status ok, arquivo correto e lista de Receitas s??o retornados")
  void whenGETIsCalledThenStatusOkCorrectFileAndListOfReceitaAreReturned() throws Exception {
		//given
		List<Receita> receitasLista = Collections.singletonList(ReceitaUtils.createFakeEntity());
		List<Usuario> usuariosLista = Collections.singletonList(UsuarioUtils.createFakeEntity());
		List<Ingrediente> ingredientesLista = Collections.singletonList(IngredienteUtils.createFakeEntity());
  	
  	//when
  	when(receitaService.listarReceitas(null, null, null)).thenReturn(receitasLista);
  	when(usuarioService.listarUsuarios(null)).thenReturn(usuariosLista);
  	when(ingredienteService.listarIngredientes(null)).thenReturn(ingredientesLista);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(PATH_ROOT+PATH_RECEITA))
  			.andExpectAll(
  					status().isOk(),
  					view().name(RECEITA+LISTA)
  			)
      	.andReturn()
  			.getModelAndView();
  	
  	assertAll(
  		"verifica se retorna lista de Receitas"
  		,() -> assertModelAttributeAvailable(mv, OBJETO_RECEITA_LISTA)
  		,() -> assertCompareListModelAttribute(mv, OBJETO_RECEITA_LISTA, receitasLista)
  	);
  	
  }
	
	@Test
  @DisplayName("5. Quando GET ?? chamado em excluir com id v??lido ent??o uma mensagem de sucesso ?? retornada")
  void whenGETIsCalledInExcluirWithValidIdThenASucessMensagemIsReturned() throws Exception {
  	//given
  	Receita receita = ReceitaUtils.createFakeEntity();
  	String caminho = PATH_ROOT + PATH_RECEITA + PATH_EXCLUIR +  "?id=" + receita.getId();
  	
  	//when
  	doNothing().when(receitaService).excluirReceita(receita.getId());

  	//then
  	FlashMap flashMap = mockMvc.perform(get(caminho))
  			.andExpectAll(
  					status().isFound(), //HTTP 302
  					view().name(REDIRECT+PATH_RECEITA)
  			)
  			.andReturn()
  			.getFlashMap();
  	
  	assertAll(
  		"verifica se servi??o excluirReceita executou e mensagem de sucesso"
  		,() -> verify(receitaService, times(1)).excluirReceita(receita.getId())
    	,() -> assertEquals(
    						String.format("Receita com id %d foi EXCLU??DA com sucesso.", receita.getId())
    						, flashMap.get(OBJETO_MENSAGEM))
    );
  
  }
  
  @Test
  @DisplayName("5.2. Quando GET ?? chamado em excluir com id inv??lido ent??o uma mensagem de erro ?? retornada")
  void whenGETIsCalledInExcluirWithInvalidIdThenAErrorMensagemIsReturned() throws Exception {
  	//given
  	String caminho = PATH_ROOT + PATH_RECEITA + PATH_EXCLUIR +  "?id=" + ID_INVALIDO;
  	
  	//when
  	doThrow(new ReceitaNaoEncontradaException(ID_INVALIDO)).when(receitaService).excluirReceita(ID_INVALIDO);

  	
  	//then
  	FlashMap flashMap = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getFlashMap();
  	
  	assertAll(
  		"verifica se servi??o excluirReceita executou e retornou mensagem de erro"
  		,() -> verify(receitaService, times(1)).excluirReceita(ID_INVALIDO)
  		,() -> assertEquals(String.format("Problema ao excluir: Receita com Id %d n??o foi encontrada!", ID_INVALIDO)
  													,flashMap.get(OBJETO_MENSAGEM_ERRO))
    );
  
  }

}
