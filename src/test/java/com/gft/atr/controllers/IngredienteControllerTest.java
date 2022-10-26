package com.gft.atr.controllers;

import static com.gft.atr.enums.ModelAndViewFileObject.DETALHE;
import static com.gft.atr.enums.ModelAndViewFileObject.FORM;
import static com.gft.atr.enums.ModelAndViewFileObject.INGREDIENTE;
import static com.gft.atr.enums.ModelAndViewFileObject.LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_INGREDIENTE;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_INGREDIENTE_LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_MENSAGEM;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_MENSAGEM_ERRO;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_CADASTRO;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_DETALHE;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_EXCLUIR;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_INGREDIENTE;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_ROOT;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;

import com.gft.atr.entities.Ingrediente;
import com.gft.atr.exceptions.IngredienteNaoEncontradoException;
import com.gft.atr.services.IngredienteService;
import com.gft.atr.utils.IngredienteUtils;



@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do IngredienteController")
class IngredienteControllerTest {
	
	private static final Long ID_INVALIDO = 2L;
	
	private MockMvc mockMvc;
	
	@Mock
	IngredienteService ingredienteService;
	
	@InjectMocks
  private IngredienteController ingredienteController;
	
	@BeforeEach
  void setup() {
      mockMvc = MockMvcBuilders.standaloneSetup(ingredienteController).build();
  }
	
  @Test
  @DisplayName("1. Quando GET é chamado em cadastro então status ok e arquivo correto são retornados")
  void whenGETIsCalledInCadastroThenStatusOkAndCorrectFileAreReturned() throws Exception {
  
  	//then
  	mockMvc.perform(get(PATH_ROOT+PATH_INGREDIENTE+PATH_CADASTRO))
  			.andExpectAll(
  					status().isOk(),
  					view().name(INGREDIENTE+FORM)
  			);
  	
  }

  @Test
  @DisplayName("1.2. Quando GET é chamado em cadastro sem uma id então um novo Ingrediente é retornado")
  void whenGETIsCalledInCadastroWithoutIdThenANewIngredienteIsReturned() throws Exception {
  
  	//then
  	ModelAndView mv = mockMvc.perform(get(PATH_ROOT+PATH_INGREDIENTE+PATH_CADASTRO))
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeValue(mv, OBJETO_INGREDIENTE, new Ingrediente());
  
  }
  
  @Test
  @DisplayName("1.3. Quando GET é chamado em cadastro com id válido então respectivo Ingrediente é retornado")
  void whenGETIsCalledInCadastroWithValidIdThenRespectiveIngredienteIsReturned() throws Exception {
  	//given
  	Ingrediente ingrediente = IngredienteUtils.createFakeEntity();
  	String caminho = PATH_ROOT + PATH_INGREDIENTE + PATH_CADASTRO + "?id=" + ingrediente.getId();
  	
  	//when
  	when(ingredienteService.obterIngrediente(ingrediente.getId())).thenReturn(ingrediente);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeValue(mv, OBJETO_INGREDIENTE, ingrediente);
  	
  }

  @Test
  @DisplayName("1.4. Quando GET é chamado em cadastro com id inválido então um novo Ingrediente e mensagem de erro são retornados")
  void whenGETIsCalledInCadastroWithInvalidIdThenANewIngredienteAndErrorMessageAreReturned() throws Exception {
  	//given
  	String caminho = PATH_ROOT + PATH_INGREDIENTE + PATH_CADASTRO + "?id=" + ID_INVALIDO;
  	
  	//when
  	when(ingredienteService.obterIngrediente(ID_INVALIDO)).thenThrow(IngredienteNaoEncontradoException.class);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getModelAndView();
  	
  	assertAll(
  		"Verificar se retornou Ingrediente novo e existe mensagem de erro"
  		,() ->	assertModelAttributeValue(mv, OBJETO_INGREDIENTE, new Ingrediente())
  		,() ->	assertModelAttributeAvailable(mv, OBJETO_MENSAGEM_ERRO)
  	);
  			
  }

  @Test
  @DisplayName("2. Quando POST é chamado em cadastro com um novo Ingrediente válido então deve salvar")
  void whenPOSTIsCalledInCadastroWithANewValidIngredienteThenShouldSave() throws Exception {
  	//given
  	Ingrediente ingredienteNovo = IngredienteUtils.createFakeEntityWithoutId();
  	Ingrediente ingredienteSalvo = IngredienteUtils.createFakeEntity();
  	
  	String caminho = REDIRECT + PATH_INGREDIENTE + PATH_CADASTRO + "?id=" + ingredienteSalvo.getId();
  	
  	//when
  	when(ingredienteService.salvarIngrediente(ingredienteNovo)).thenReturn(ingredienteSalvo);
  	
		//then
  	mockMvc.perform(post(PATH_ROOT+PATH_INGREDIENTE+PATH_CADASTRO)
  					.contentType(MediaType.MULTIPART_FORM_DATA)
  					.param("nome", ingredienteNovo.getNome())
  					.param("imagem", ingredienteNovo.getImagem())
  					.param("densidade", String.valueOf(ingredienteNovo.getDensidade()))
  				)
  				.andExpectAll(
  						status().isFound()
    					,view().name(caminho)
    					,flash().attributeExists(OBJETO_INGREDIENTE)
    					,flash().attributeExists(OBJETO_MENSAGEM)
  				);
 
  }
  
  @Test
  @DisplayName("2.2. Quando POST é chamado em cadastro com Ingrediente já existente então deve atualizar")
  void whenPOSTIsCalledInCadastroWithExistingIngredienteThenShouldUpdate() throws Exception {
  	//given
  	Ingrediente ingredienteSalvo = IngredienteUtils.createFakeEntity();
  	String caminho = REDIRECT + PATH_INGREDIENTE + PATH_CADASTRO + "?id=" + ingredienteSalvo.getId();
  			
  	//when
  	when(ingredienteService.salvarIngrediente(ingredienteSalvo)).thenReturn(ingredienteSalvo);
		
		//then
  	mockMvc.perform(post(PATH_ROOT+PATH_INGREDIENTE+PATH_CADASTRO)
  					.contentType(MediaType.MULTIPART_FORM_DATA)
  					.param("id", String.valueOf(ingredienteSalvo.getId()))
  					.param("nome", ingredienteSalvo.getNome())
  					.param("imagem", ingredienteSalvo.getImagem())
  					.param("densidade", String.valueOf(ingredienteSalvo.getDensidade()))
  				)
  				.andExpectAll(
  						status().isFound()
    					,view().name(caminho)
    					,flash().attributeExists(OBJETO_INGREDIENTE)
    					,flash().attributeExists(OBJETO_MENSAGEM)
  				);
 
  }
  
  @Test
  @DisplayName("2.3. Quando POST é chamado em cadastro com um Ingrediente inválido então deve retornar erro")
  void whenPOSTIsCalledInCadastroWithAInvalidIngredienteThenShouldReturnError() throws Exception {
  	//then
  	ModelAndView mv = mockMvc.perform(post(PATH_ROOT+PATH_INGREDIENTE+PATH_CADASTRO)
  						.contentType(MediaType.MULTIPART_FORM_DATA))
  				.andReturn()
  				.getModelAndView();
  	
  	boolean containError = BindingResultUtils
  								.getRequiredBindingResult(mv.getModelMap(), OBJETO_INGREDIENTE)
  								.hasErrors();
  	
  	assertTrue(containError);

  }
  
  @Test
  @DisplayName("3. Quando GET é chamado em detalhe com id válido então Ingrediente válido é retornado")
  void whenGETIsCalledInDetalheWithValidIdThenValidIngredienteIsReturned() throws Exception {
  	//given
  	Ingrediente ingrediente = IngredienteUtils.createFakeEntity();
  	String caminho = PATH_ROOT + PATH_INGREDIENTE + PATH_DETALHE + "?id=" + ingrediente.getId();
  	
  	//when
  	when(ingredienteService.obterIngrediente(ingrediente.getId())).thenReturn(ingrediente);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andExpectAll(
  					status().isOk(),
  					view().name(INGREDIENTE+DETALHE)
  			)
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeValue(mv, OBJETO_INGREDIENTE, ingrediente);
  
  }
  
  @Test
  @DisplayName("3.2. Quando GET é chamado em detalhe com id inválido então mensagem de erro é retornada")
  void whenGETIsCalledInDetalheWithInvalidIdThenErrorMensagemIsReturned() throws Exception {
  	//given
  	String caminho = PATH_ROOT + PATH_INGREDIENTE + PATH_DETALHE + "?id=" + ID_INVALIDO;
  	
  	//when
  	when(ingredienteService.obterIngrediente(ID_INVALIDO)).thenThrow(IngredienteNaoEncontradoException.class);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeAvailable(mv, OBJETO_MENSAGEM_ERRO);
  			
  }
  
	@Test
  @DisplayName("4. Quando GET é chamado então status ok, arquivo correto e lista de Ingredientes são retornados")
  void whenGETIsCalledThenStatusOkCorrectFileAndListOfIngredienteAreReturned() throws Exception {
		//given
  	List<Ingrediente> ingredientesLista = Collections.singletonList(IngredienteUtils.createFakeEntity());
  	
  	//when
  	when(ingredienteService.listarIngredientes(null)).thenReturn(ingredientesLista);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(PATH_ROOT+PATH_INGREDIENTE))
  			.andExpectAll(
  					status().isOk(),
  					view().name(INGREDIENTE+LISTA)
  			)
      	.andReturn()
  			.getModelAndView();
  	
  	assertAll(
  		"verifica se retorna lista de Ingredientes"
  		,() -> assertModelAttributeAvailable(mv, OBJETO_INGREDIENTE_LISTA)
  		,() -> assertCompareListModelAttribute(mv, OBJETO_INGREDIENTE_LISTA, ingredientesLista)
  	);
  	
  }
	
	@Test
  @DisplayName("5. Quando GET é chamado em excluir com id válido então uma mensagem de sucesso é retornada")
  void whenGETIsCalledInExcluirWithValidIdThenASucessMensagemIsReturned() throws Exception {
  	//given
  	Ingrediente unidadeMedida = IngredienteUtils.createFakeEntity();
  	String caminho = PATH_ROOT + PATH_INGREDIENTE + PATH_EXCLUIR +  "?id=" + unidadeMedida.getId();
  	
  	//when
  	doNothing().when(ingredienteService).excluirIngrediente(unidadeMedida.getId());

  	//then
  	FlashMap flashMap = mockMvc.perform(get(caminho))
  			.andExpectAll(
  					status().isFound(), //HTTP 302
  					view().name(REDIRECT+PATH_INGREDIENTE)
  			)
  			.andReturn()
  			.getFlashMap();
  	
  	assertAll(
  		"verifica se serviço excluirIngrediente executou e retornou mensagem de sucesso"
  		,() -> verify(ingredienteService, times(1)).excluirIngrediente(unidadeMedida.getId())
    	,() -> assertEquals(
    						String.format("Ingrediente com id %d foi EXCLUÍDO com sucesso.", unidadeMedida.getId())
    						, flashMap.get(OBJETO_MENSAGEM))
    );
  
  }
  
  @Test
  @DisplayName("5.2. Quando GET é chamado em excluir com id inválido então uma mensagem de erro é retornada")
  void whenGETIsCalledInExcluirWithInvalidIdThenAErrorMensagemIsReturned() throws Exception {
  	//given
  	String caminho = PATH_ROOT + PATH_INGREDIENTE + PATH_EXCLUIR +  "?id=" + ID_INVALIDO;
  	
  	//when
  	doThrow(new IngredienteNaoEncontradoException(ID_INVALIDO)).when(ingredienteService).excluirIngrediente(ID_INVALIDO);

  	
  	//then
  	FlashMap flashMap = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getFlashMap();
  	
  	assertAll(
  		"verifica se serviço excluirIngrediente executou e retornou mensagem de erro"
  		,() -> verify(ingredienteService, times(1)).excluirIngrediente(ID_INVALIDO)
  		,() -> assertEquals(String.format("Problema ao excluir: Ingrediente com Id %d não foi encontrado!", ID_INVALIDO)
  													,flashMap.get(OBJETO_MENSAGEM_ERRO))
    );
  
  }

}
