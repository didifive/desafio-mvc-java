package com.gft.atr.controllers;

import static com.gft.atr.enums.ModelAndViewFileObject.DETALHE;
import static com.gft.atr.enums.ModelAndViewFileObject.FORM;
import static com.gft.atr.enums.ModelAndViewFileObject.LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_MENSAGEM;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_MENSAGEM_ERRO;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_UNIDADE_MEDIDA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_UNIDADE_MEDIDA_LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_UNIDADE_REFERENCIA_LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_CADASTRO;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_DETALHE;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_EXCLUIR;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_ROOT;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_UNIDADE_MEDIDA;
import static com.gft.atr.enums.ModelAndViewFileObject.REDIRECT;
import static com.gft.atr.enums.ModelAndViewFileObject.UNIDADE_MEDIDA;
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

import com.gft.atr.entities.UnidadeMedida;
import com.gft.atr.exceptions.UnidadeMedidaNaoEncontradaException;
import com.gft.atr.services.UnidadeMedidaService;
import com.gft.atr.utils.UnidadeMedidaUtils;



@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do UnidadeMedidaController")
class UnidadeMedidaControllerTest {
	
	private static final Long ID_INVALIDO = 2L;
	
	private MockMvc mockMvc;
	
	@Mock
	UnidadeMedidaService unidadeMedidaService;
	
	@InjectMocks
  private UnidadeMedidaController unidadeMedidaController;
	
	@BeforeEach
  void setup() {
      mockMvc = MockMvcBuilders.standaloneSetup(unidadeMedidaController).build();
  }
	
  @Test
  @DisplayName("1. Quando GET é chamado em cadastro então status ok e arquivo correto são retornados")
  void whenGETIsCalledInCadastroThenStatusOkAndCorrectFileAreReturned() throws Exception {
  
  	//then
  	mockMvc.perform(get(PATH_ROOT+PATH_UNIDADE_MEDIDA+PATH_CADASTRO))
  			.andExpectAll(
  					status().isOk(),
  					view().name(UNIDADE_MEDIDA+FORM)
  			);
  	
  }

	
  @Test
  @DisplayName("1.2. Quando GET é chamado em cadastro sem uma id então uma nova Unidade de Medida é retornada")
  void whenGETIsCalledInCadastroWithoutIdThenANewUnidadeMedidaIsReturned() throws Exception {
  
  	//then
  	ModelAndView mv = mockMvc.perform(get(PATH_ROOT+PATH_UNIDADE_MEDIDA+PATH_CADASTRO))
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeValue(mv, OBJETO_UNIDADE_MEDIDA, new UnidadeMedida());
  
  }
  
  @Test
  @DisplayName("1.3. Quando GET é chamado em cadastro com id válido então a respectiva Unidade de Medida e Lista de Unidades de Referência são retornadas")
  void whenGETIsCalledInCadastroWithValidIdThenTheRespectiveUnidadeMedidaAndListOfUnidadeReferenciaAreReturned() throws Exception {
  	//given
  	UnidadeMedida unidadeMedida = UnidadeMedidaUtils.createFakeEntity();
  	String caminho = PATH_ROOT + PATH_UNIDADE_MEDIDA + PATH_CADASTRO + "?id=" + unidadeMedida.getId();
  	
  	//when
  	when(unidadeMedidaService.obterUnidadeMedida(unidadeMedida.getId())).thenReturn(unidadeMedida);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getModelAndView();
  	
  	assertAll(
  			"Verifica se retornou a respectiva UnidadeMedida e Lista de Unidades de Referências"
  			,() -> assertModelAttributeValue(mv, OBJETO_UNIDADE_MEDIDA, unidadeMedida)
  			,() -> assertModelAttributeAvailable(mv, OBJETO_UNIDADE_REFERENCIA_LISTA)
  	);
  }

  @Test
  @DisplayName("1.4. Quando GET é chamado em cadastro com id inválido então uma nova Unidade de Medida e mensagem de erro são retornados")
  void whenGETIsCalledInCadastroWithInvalidIdThenANewUnidadeMedidaAndErrorMessageAreReturned() throws Exception {
  	//given
  	String caminho = PATH_ROOT + PATH_UNIDADE_MEDIDA + PATH_CADASTRO + "?id=" + ID_INVALIDO;
  	
  	//when
  	when(unidadeMedidaService.obterUnidadeMedida(ID_INVALIDO)).thenThrow(UnidadeMedidaNaoEncontradaException.class);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getModelAndView();
  	
  	assertAll(
  		"Verificar se retornou Unidade de Medida nova e existe mensagem de erro"
  		,() ->	assertModelAttributeValue(mv, OBJETO_UNIDADE_MEDIDA, new UnidadeMedida())
  		,() ->	assertModelAttributeAvailable(mv, OBJETO_MENSAGEM_ERRO)
  	);
  			
  }

  @Test
  @DisplayName("2. Quando POST é chamado em cadastro com uma nova Unidade Medida válida então deve salvar")
  void whenPOSTIsCalledInCadastroWithANewValidUnidadeMedidaThenShouldSave() throws Exception {
  	//given
  	UnidadeMedida unidadeMedidaNova = UnidadeMedidaUtils.createFakeEntityWithoutId();
  	UnidadeMedida unidadeMedidaSalva = UnidadeMedidaUtils.createFakeEntity();
  	String caminho = REDIRECT + PATH_UNIDADE_MEDIDA + PATH_CADASTRO + "?id=" + unidadeMedidaSalva.getId();
  			
  	//when
  	when(unidadeMedidaService.salvarUnidadeMedida(unidadeMedidaNova)).thenReturn(unidadeMedidaSalva);
		
		//then
  	mockMvc.perform(post(PATH_ROOT+PATH_UNIDADE_MEDIDA+PATH_CADASTRO)
  					.param("nome", unidadeMedidaNova.getNome())
  					.param("abreviacao", unidadeMedidaNova.getAbreviacao())
  					.param("unidadeReferencia", String.valueOf(unidadeMedidaNova.getUnidadeReferencia()))
  					.param("quantidadeUnidadeReferencia", String.valueOf(unidadeMedidaNova.getQuantidadeUnidadeReferencia()))
  				)
  				.andExpectAll(
  						status().isFound()
    					,view().name(caminho)
    					,flash().attributeExists(OBJETO_UNIDADE_MEDIDA)
    					,flash().attributeExists(OBJETO_MENSAGEM)
  				)
  				.andReturn()
  				.getFlashMap();
 
  }
  
  @Test
  @DisplayName("2.2. Quando POST é chamado em cadastro com Unidade Medida já existente então deve atualizar")
  void whenPOSTIsCalledInCadastroWithExistingUnidadeMedidaThenShouldAtualizar() throws Exception {
  	//given
  	UnidadeMedida unidadeMedidaSalva = UnidadeMedidaUtils.createFakeEntity();
  	String caminho = REDIRECT + PATH_UNIDADE_MEDIDA + PATH_CADASTRO + "?id=" + unidadeMedidaSalva.getId();
  			
  	//when
  	when(unidadeMedidaService.salvarUnidadeMedida(unidadeMedidaSalva)).thenReturn(unidadeMedidaSalva);
		
		//then
  	mockMvc.perform(post(PATH_ROOT+PATH_UNIDADE_MEDIDA+PATH_CADASTRO)
  					.param("id", String.valueOf(unidadeMedidaSalva.getId()))
  					.param("nome", unidadeMedidaSalva.getNome())
  					.param("abreviacao", unidadeMedidaSalva.getAbreviacao())
  					.param("unidadeReferencia", String.valueOf(unidadeMedidaSalva.getUnidadeReferencia()))
  					.param("quantidadeUnidadeReferencia", String.valueOf(unidadeMedidaSalva.getQuantidadeUnidadeReferencia()))
  				)
  				.andExpectAll(
  						status().isFound()
    					,view().name(caminho)
    					,flash().attributeExists(OBJETO_UNIDADE_MEDIDA)
    					,flash().attributeExists(OBJETO_MENSAGEM)
  				);
 
  }
  
  @Test
  @DisplayName("2.3. Quando POST é chamado em cadastro com uma Unidade de Medida inválida então deve retornar erro")
  void whenPOSTIsCalledInCadastroWithAInvalidUnidadeMedidaThenShouldReturnError() throws Exception {
		//then
  	ModelAndView mv = mockMvc.perform(post(PATH_ROOT+PATH_UNIDADE_MEDIDA+PATH_CADASTRO))
  				.andReturn()
  				.getModelAndView();
  	
  	System.out.println(mv);
  	
  	boolean containError = BindingResultUtils
  								.getRequiredBindingResult(mv.getModelMap(), OBJETO_UNIDADE_MEDIDA)
  								.hasErrors();
  	
  	assertTrue(containError);

  }
  
  @Test
  @DisplayName("3. Quando GET é chamado em detalhe com id válido então Unidade de Medida válida é retornada")
  void whenGETIsCalledInDetalheWithValidIdThenValidUnidadeMedidaIsReturned() throws Exception {
  	//given
  	UnidadeMedida unidadeMedida = UnidadeMedidaUtils.createFakeEntity();
  	String caminho = PATH_ROOT + PATH_UNIDADE_MEDIDA + PATH_DETALHE + "?id=" + unidadeMedida.getId();
  	
  	//when
  	when(unidadeMedidaService.obterUnidadeMedida(unidadeMedida.getId())).thenReturn(unidadeMedida);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andExpectAll(
  					status().isOk(),
  					view().name(UNIDADE_MEDIDA+DETALHE)
  			)
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeValue(mv, OBJETO_UNIDADE_MEDIDA, unidadeMedida);
  
  }
  
  @Test
  @DisplayName("3.2. Quando GET é chamado em detalhe com id inválido então mensagem de erro é retornada")
  void whenGETIsCalledInDetalheWithInvalidIdThenErrorMensagemIsReturned() throws Exception {
  	//given
  	String caminho = PATH_ROOT + PATH_UNIDADE_MEDIDA + PATH_DETALHE + "?id=" + ID_INVALIDO;
  	
  	//when
  	when(unidadeMedidaService.obterUnidadeMedida(ID_INVALIDO)).thenThrow(UnidadeMedidaNaoEncontradaException.class);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getModelAndView();
  	
  	assertModelAttributeAvailable(mv, OBJETO_MENSAGEM_ERRO);
  			
  }
  
	@Test
  @DisplayName("4. Quando GET é chamado então status ok, arquivo correto e lista de Unidades de Medida são retornados")
  void whenGETIsCalledThenStatusOkCorrectFileAndListOfUnidadeMedidaAreReturned() throws Exception {
		//given
  	List<UnidadeMedida> unidadesMedidaLista = Collections.singletonList(UnidadeMedidaUtils.createFakeEntity());
  	
  	//when
  	when(unidadeMedidaService.listarUnidadesMedida(null)).thenReturn(unidadesMedidaLista);
  	
  	//then
  	ModelAndView mv = mockMvc.perform(get(PATH_ROOT+PATH_UNIDADE_MEDIDA))
  			.andExpectAll(
  					status().isOk(),
  					view().name(UNIDADE_MEDIDA+LISTA)
  			)
      	.andReturn()
  			.getModelAndView();
  	
  	assertAll(
  		"verifica se retorna lista de Unidades de Medida"
  		,() -> assertModelAttributeAvailable(mv, OBJETO_UNIDADE_MEDIDA_LISTA)
  		,() -> assertCompareListModelAttribute(mv, OBJETO_UNIDADE_MEDIDA_LISTA, unidadesMedidaLista)
  	);
  	
  }
	
	@Test
  @DisplayName("5. Quando GET é chamado em excluir com id válido então uma mensagem de sucesso é retornada")
  void whenGETIsCalledInExcluirWithValidIdThenASucessMensagemIsReturned() throws Exception {
  	//given
  	UnidadeMedida unidadeMedida = UnidadeMedidaUtils.createFakeEntity();
  	String caminho = PATH_ROOT + PATH_UNIDADE_MEDIDA + PATH_EXCLUIR +  "?id=" + unidadeMedida.getId();
  	
  	//when
  	doNothing().when(unidadeMedidaService).excluirUnidadeMedida(unidadeMedida.getId());

  	//then
  	FlashMap flashMap = mockMvc.perform(get(caminho))
  			.andExpectAll(
  					status().isFound(), //HTTP 302
  					view().name(REDIRECT+PATH_UNIDADE_MEDIDA)
  			)
  			.andReturn()
  			.getFlashMap();
  	
  	assertAll(
  		"verifica se serviço excluirUnidadeMedida executou e mensagem de sucesso"
  		,() -> verify(unidadeMedidaService, times(1)).excluirUnidadeMedida(unidadeMedida.getId())
    	,() -> assertEquals(
    						String.format("Unidade de Medida com id %d foi EXCLUÍDA com sucesso.", unidadeMedida.getId())
    						, flashMap.get(OBJETO_MENSAGEM))
    );
  
  }
  
  @Test
  @DisplayName("5.2. Quando GET é chamado em excluir com id inválido então uma mensagem de erro é retornada")
  void whenGETIsCalledInExcluirWithInvalidIdThenAErrorMensagemIsReturned() throws Exception {
  	//given
  	String caminho = PATH_ROOT + PATH_UNIDADE_MEDIDA + PATH_EXCLUIR +  "?id=" + ID_INVALIDO;
  	
  	//when
  	doThrow(new UnidadeMedidaNaoEncontradaException(ID_INVALIDO)).when(unidadeMedidaService).excluirUnidadeMedida(ID_INVALIDO);

  	
  	//then
  	FlashMap flashMap = mockMvc.perform(get(caminho))
  			.andReturn()
  			.getFlashMap();
  	
  	assertAll(
  		"verifica se serviço excluirUnidadeMedida executou e retornou mensagem de erro"
  		,() -> verify(unidadeMedidaService, times(1)).excluirUnidadeMedida(ID_INVALIDO)
  		,() -> assertEquals(String.format("Problema ao excluir: Unidade de Medida com Id %d não foi encontrada!", ID_INVALIDO)
  													,flashMap.get(OBJETO_MENSAGEM_ERRO))
    );
  
  }

}
