package com.gft.atr.controllers;

import static com.gft.atr.enums.ModelAndViewFileObject.DETALHE;
import static com.gft.atr.enums.ModelAndViewFileObject.FORM;
import static com.gft.atr.enums.ModelAndViewFileObject.LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_MENSAGEM;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_MENSAGEM_ERRO;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_USUARIO;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_USUARIO_LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_CADASTRO;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_DETALHE;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_EXCLUIR;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_ROOT;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_USUARIO;
import static com.gft.atr.enums.ModelAndViewFileObject.REDIRECT;
import static com.gft.atr.enums.ModelAndViewFileObject.USUARIO;
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

import com.gft.atr.entities.Usuario;
import com.gft.atr.exceptions.UsuarioNaoEncontradoException;
import com.gft.atr.repositories.PermissaoRepository;
import com.gft.atr.services.UsuarioService;
import com.gft.atr.utils.UsuarioUtils;



@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do UsuarioController")
class UsuarioControllerTest {
	
	private static final Long ID_INVALIDO = 2L;
	
	private MockMvc mockMvc;
	
	@Mock
	UsuarioService usuarioService;
	
	@Mock
	PermissaoRepository permissaoRepository;
	
	@InjectMocks
  private UsuarioController usuarioController;
	
	@BeforeEach
  void setup() {
      mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
  }
	
  @Test
  @DisplayName("1. Quando GET é chamado em cadastro então status ok e arquivo correto são retornados")
  void whenGETIsCalledInCadastroThenStatusOkAndCorrectFileAreReturned() throws Exception {
  	//then
  	mockMvc.perform(get(PATH_ROOT+PATH_USUARIO+PATH_CADASTRO))
  			.andExpectAll(
  					status().isOk(),
  					view().name(USUARIO+FORM)
  			);
  	
  }

	
  @Test
  @DisplayName("1.2. Quando GET é chamado em cadastro sem uma id então um novo Usuario é retornado")
  void whenGETIsCalledInCadastroWithoutIdThenANewUsuarioIsReturned() throws Exception {
  
  	//then
  	ModelAndView mv = mockMvc.perform(get(PATH_ROOT+PATH_USUARIO+PATH_CADASTRO))
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeValue(mv, OBJETO_USUARIO, new Usuario());
  
  }
  
  @Test
  @DisplayName("1.3. Quando GET é chamado em cadastro com id válido então a respectivo Usuario é retornado")
  void whenGETIsCalledInCadastroWithValidIdThenTheUsuarioIsReturned() throws Exception {
  	//given
  	Usuario usuario = UsuarioUtils.createFakeEntity();
  	String caminho = PATH_ROOT + PATH_USUARIO + PATH_CADASTRO + "?id=" + usuario.getId();
  	
  	//when
  	when(usuarioService.obterUsuario(usuario.getId())).thenReturn(usuario);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeValue(mv, OBJETO_USUARIO, usuario);
  }

  @Test
  @DisplayName("1.4. Quando GET é chamado em cadastro com id inválido então novo Usuário e mensagem de erro são retornados")
  void whenGETIsCalledInCadastroWithInvalidIdThenANewUsuarioAndErrorMessageAreReturned() throws Exception {
  	//given
  	String caminho = PATH_ROOT + PATH_USUARIO + PATH_CADASTRO + "?id=" + ID_INVALIDO;
  	
  	//when
  	when(usuarioService.obterUsuario(ID_INVALIDO)).thenThrow(UsuarioNaoEncontradoException.class);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getModelAndView();
  	
  	assertAll(
  		"Verificar se retornou novo Usuario e existe mensagem de erro"
  		,() ->	assertModelAttributeValue(mv, OBJETO_USUARIO, new Usuario())
  		,() ->	assertModelAttributeAvailable(mv, OBJETO_MENSAGEM_ERRO)
  	);
  			
  }

  @Test
  @DisplayName("2. Quando POST é chamado em cadastro com novo Usuário válido então deve salvar")
  void whenPOSTIsCalledInCadastroWithANewValidUsuarioThenShouldSave() throws Exception {
  	//given
  	Usuario usuarioNovo = UsuarioUtils.createFakeEntityWithoutId();
  	Usuario usuarioSalvo = UsuarioUtils.createFakeEntity();
  	String caminho = REDIRECT + PATH_USUARIO + PATH_CADASTRO + "?id=" + usuarioSalvo.getId();
  			
  	//when
  	when(usuarioService.salvarUsuario(usuarioNovo)).thenReturn(usuarioSalvo);
		
		//then
  	mockMvc.perform(post(PATH_ROOT+PATH_USUARIO+PATH_CADASTRO)
  					.param("nome", usuarioNovo.getNome())
  					.param("nomeUsuario", usuarioNovo.getNomeUsuario())
  					.param("senha", usuarioNovo.getSenha())
  					.param("email", usuarioNovo.getEmail())
  				)
  				.andExpectAll(
  						status().isFound()
    					,view().name(caminho)
    					,flash().attributeExists(OBJETO_USUARIO)
    					,flash().attributeExists(OBJETO_MENSAGEM)
  				)
  				.andReturn()
  				.getFlashMap();
 
  }
  
  @Test
  @DisplayName("2.2. Quando POST é chamado em cadastro com Usuario já existente então deve atualizar")
  void whenPOSTIsCalledInCadastroWithExistingUnidadeMedidaThenShouldAtualizar() throws Exception {
  	//given
  	Usuario usuarioSalvo = UsuarioUtils.createFakeEntity();
  	String caminho = REDIRECT + PATH_USUARIO + PATH_CADASTRO + "?id=" + usuarioSalvo.getId();
  			
  	//when
  	when(usuarioService.salvarUsuario(usuarioSalvo)).thenReturn(usuarioSalvo);
		
		//then
  	mockMvc.perform(post(PATH_ROOT+PATH_USUARIO+PATH_CADASTRO)
  					.param("id", String.valueOf(usuarioSalvo.getId()))
  					.param("nome", usuarioSalvo.getNome())
  					.param("nomeUsuario", usuarioSalvo.getNomeUsuario())
  					.param("senha", usuarioSalvo.getSenha())
  					.param("email", usuarioSalvo.getEmail())

  				)
  				.andExpectAll(
  						status().isFound()
    					,view().name(caminho)
    					,flash().attributeExists(OBJETO_USUARIO)
    					,flash().attributeExists(OBJETO_MENSAGEM)
  				);
 
  }
  
  @Test
  @DisplayName("2.3. Quando POST é chamado em cadastro com Usuário inválido então deve retornar erro")
  void whenPOSTIsCalledInCadastroWithAInvalidUsuarioThenShouldReturnError() throws Exception {
		//then
  	ModelAndView mv = mockMvc.perform(post(PATH_ROOT+PATH_USUARIO+PATH_CADASTRO))
  				.andReturn()
  				.getModelAndView();
  	
  	System.out.println(mv);
  	
  	boolean containError = BindingResultUtils
  								.getRequiredBindingResult(mv.getModelMap(), OBJETO_USUARIO)
  								.hasErrors();
  	
  	assertTrue(containError);

  }
  
  @Test
  @DisplayName("3. Quando GET é chamado em detalhe com id válido então Usuário válido é retornado")
  void whenGETIsCalledInDetalheWithValidIdThenValidUsuarioIsReturned() throws Exception {
  	//given
  	Usuario usuario = UsuarioUtils.createFakeEntity();
  	String caminho = PATH_ROOT + PATH_USUARIO + PATH_DETALHE + "?id=" + usuario.getId();
  	
  	//when
  	when(usuarioService.obterUsuario(usuario.getId())).thenReturn(usuario);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andExpectAll(
  					status().isOk(),
  					view().name(USUARIO+DETALHE)
  			)
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeValue(mv, OBJETO_USUARIO, usuario);
  
  }
  
  @Test
  @DisplayName("3.2. Quando GET é chamado em detalhe com id inválido então mensagem de erro é retornada")
  void whenGETIsCalledInDetalheWithInvalidIdThenErrorMensagemIsReturned() throws Exception {
  	//given
  	String caminho = PATH_ROOT + PATH_USUARIO + PATH_DETALHE + "?id=" + ID_INVALIDO;
  	
  	//when
  	when(usuarioService.obterUsuario(ID_INVALIDO)).thenThrow(UsuarioNaoEncontradoException.class);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeAvailable(mv, OBJETO_MENSAGEM_ERRO);
  			
  }
  
	@Test
  @DisplayName("4. Quando GET é chamado então status ok, arquivo correto e lista de Usuários são retornados")
  void whenGETIsCalledThenStatusOkCorrectFileAndListOfUsuarioAreReturned() throws Exception {
		//given
  	List<Usuario> usuario = Collections.singletonList(UsuarioUtils.createFakeEntity());
  	
  	//when
  	when(usuarioService.listarUsuarios(null)).thenReturn(usuario);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(PATH_ROOT+PATH_USUARIO))
  			.andExpectAll(
  					status().isOk(),
  					view().name(USUARIO+LISTA)
  			)
      	.andReturn()
  			.getModelAndView();
  	
  	assertAll(
  		"verifica se retorna lista de Unidades de Medida"
  		,() -> assertModelAttributeAvailable(mv, OBJETO_USUARIO_LISTA)
  		,() -> assertCompareListModelAttribute(mv, OBJETO_USUARIO_LISTA, usuario)
  	);
  	
  }
	
	@Test
  @DisplayName("5. Quando GET é chamado em excluir com id válido então uma mensagem de sucesso é retornada")
  void whenGETIsCalledInExcluirWithValidIdThenASucessMensagemIsReturned() throws Exception {
  	//given
  	Usuario usuario = UsuarioUtils.createFakeEntity();
  	String caminho = PATH_ROOT + PATH_USUARIO + PATH_EXCLUIR +  "?id=" + usuario.getId();
  	
  	//when
  	doNothing().when(usuarioService).excluirUsuario(usuario.getId());

  	//then
  	FlashMap flashMap = mockMvc.perform(get(caminho))
  			.andExpectAll(
  					status().isFound(), //HTTP 302
  					view().name(REDIRECT+PATH_USUARIO)
  			)
  			.andReturn()
  			.getFlashMap();
  	
  	assertAll(
  		"verifica se serviço excluirUsuario executou e mensagem de sucesso"
  		,() -> verify(usuarioService, times(1)).excluirUsuario(usuario.getId())
    	,() -> assertEquals(
    						String.format("Usuário com id %d foi EXCLUÍDO com sucesso.", usuario.getId())
    						, flashMap.get(OBJETO_MENSAGEM))
    );
  
  }
  
  @Test
  @DisplayName("5.2. Quando GET é chamado em excluir com id inválido então uma mensagem de erro é retornada")
  void whenGETIsCalledInExcluirWithInvalidIdThenAErrorMensagemIsReturned() throws Exception {
  	//given
  	String caminho = PATH_ROOT + PATH_USUARIO + PATH_EXCLUIR +  "?id=" + ID_INVALIDO;
  	
  	//when
  	doThrow(new UsuarioNaoEncontradoException(ID_INVALIDO)).when(usuarioService).excluirUsuario(ID_INVALIDO);

  	
  	//then
  	FlashMap flashMap = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getFlashMap();
  	
  	assertAll(
  		"verifica se serviço excluirUsuario executou e retornou mensagem de erro"
  		,() -> verify(usuarioService, times(1)).excluirUsuario(ID_INVALIDO)
  		,() -> assertEquals(String.format("Problema ao excluir: Usuário com Id %d não foi encontrado!", ID_INVALIDO)
  													,flashMap.get(OBJETO_MENSAGEM_ERRO))
    );
  
  }

}
