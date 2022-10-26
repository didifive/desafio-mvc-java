package com.gft.atr.controllers;

import static com.gft.atr.enums.ModelAndViewFileObject.INDEX;
import static com.gft.atr.enums.ModelAndViewFileObject.INSTALACAO;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_INGREDIENTE_QUANTIDADE;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_RECEITA_LISTA;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_RECEITA_MEDIA_AVALIACAO;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_RECEITA_QUANTIDADE;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_UNIDADE_MEDIDA_QUANTIDADE;
import static com.gft.atr.enums.ModelAndViewFileObject.OBJETO_USUARIO_QUANTIDADE;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_INSTALACAO;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_ROOT;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_SOBRE;
import static com.gft.atr.enums.ModelAndViewFileObject.REDIRECT;
import static com.gft.atr.enums.ModelAndViewFileObject.SOBRE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertCompareListModelAttribute;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import com.gft.atr.entities.Avaliacao;
import com.gft.atr.entities.Receita;
import com.gft.atr.entities.builders.AvaliacaoBuilder;
import com.gft.atr.services.PrincipalService;
import com.gft.atr.services.ReceitaService;
import com.gft.atr.services.UsuarioService;
import com.gft.atr.services.dbpopulate.ReceitaDBPopulateService;
import com.gft.atr.utils.ReceitaUtils;
import com.gft.atr.utils.UsuarioUtils;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do PrincipalController")
class PrincipalControllerTest {

	private MockMvc mockMvc;
	
	private List<Receita> receitaLista;
	
	@Mock
	PrincipalService principalService;
	
	@Mock
	ReceitaService receitaService;
	
	@Mock
	UsuarioService usuarioService;
	
	@Mock
	ReceitaDBPopulateService receitaDBPopulateService;
	
	@InjectMocks
  private PrincipalController principalController;
	
	@BeforeEach
  void setup() {
      mockMvc = MockMvcBuilders.standaloneSetup(principalController).build();
      
  		receitaLista = Collections.singletonList(ReceitaUtils.createFakeEntity());
  		Avaliacao avaliacao = new AvaliacaoBuilder()
  																	.withId(1L)
  																	.withNota(3)
  																	.withReceita(receitaLista.get(0))
  																	.withUsuario(UsuarioUtils.createFakeEntity())
  																	.build();
  		receitaLista.get(0).setAvaliacoes(Collections.singletonList(avaliacao));
  		
  }
	
	@Test
  @DisplayName("1. Quando GET é chamado e existe informacao no banco de dados então status ok, página index, lista de receitas e média de avaliação são retornados")
  void whenGETIsCalledInPathSobreAndThereIsInformationInTheDatabaseThenStatusOkIndexPageListOfReceitaAndAverageOfAvaliacaoAreReturned() throws Exception {
		//when
		when(receitaService.listarReceitas(null, null, null)).thenReturn(receitaLista);
		when(principalService.verificaSeInstalacaoFoiEfetuada()).thenReturn(Boolean.TRUE);
		
		//then
  	ModelAndView mv = mockMvc.perform(get(PATH_ROOT))
  			.andExpectAll(
  					status().isOk(),
  					view().name(INDEX)
  			)
  			.andReturn()
  			.getModelAndView();
  	
  	assertAll(
  		"verifica se retorna lista de Receitas e se existe Map de média de Avaliações"
  			,() -> assertCompareListModelAttribute(mv, OBJETO_RECEITA_LISTA, receitaLista)
  			,() -> assertModelAttributeAvailable(mv, OBJETO_RECEITA_MEDIA_AVALIACAO)
  		
  	);
	}
	
	@Test
  @DisplayName("1.2 Quando GET é chamado e NÃO existe informação no banco de dados então status found e redireciona para instalação")
  void whenGETIsCalledAndThereIsNoInformationInTheDatabaseThenStatusFoundAndRedirectToInstalacaoPath() throws Exception {
  	//when
		when(principalService.verificaSeInstalacaoFoiEfetuada()).thenReturn(Boolean.FALSE);
		
		//then
  	mockMvc.perform(get(PATH_ROOT))
  			.andExpectAll(
  					status().isFound(),
  					view().name(REDIRECT+PATH_INSTALACAO)
  			);
  	
	}
	
	@Test
  @DisplayName("2. Quando GET é chamado em PATH_INSTALACAO e existe informação no banco de dados então status found e redireciona para index")
  void whenGETIsCalledInPathInstalacaoAndThereIsNoInformationInTheDatabaseThenStatusFoundAndRedirectToIndexPath() throws Exception {
  	//when
		when(principalService.verificaSeInstalacaoFoiEfetuada()).thenReturn(Boolean.TRUE);
		
		//then
  	mockMvc.perform(get(PATH_ROOT+PATH_INSTALACAO))
  			.andExpectAll(
  					status().isFound(),
  					view().name(REDIRECT)
  			);
  	
	}
	
	@Test
  @DisplayName("2.2. Quando GET é chamado em PATH_INSTALACAO e NÃO existe informação no banco de dados então status oK e página instalacao são retornados")
  void whenGETIsCalledInPathInstalacaoAndThereIsInformationInTheDatabaseThenStatusOkAndInstalacaoPageAreReturned() throws Exception {
  	//when
		when(principalService.verificaSeInstalacaoFoiEfetuada()).thenReturn(Boolean.FALSE);
		
		//then
  	mockMvc.perform(get(PATH_ROOT+PATH_INSTALACAO))
  			.andExpectAll(
  					status().isOk(),
  					view().name(INSTALACAO)
  			);
  	
	}
	
	@Test
  @DisplayName("3. Quando POST é chamado em PATH_INSTALACAO e existe informação no banco de dados então status Found e redireciona para index sem fazer ação")
  void whenPOSTIsCalledInPathInstalacaoAndThereIsInformationInTheDatabaseThenStatusFoundAndRedirectToIndexPathWithoutTakingAction() throws Exception {
  	//when
		when(principalService.verificaSeInstalacaoFoiEfetuada()).thenReturn(Boolean.TRUE);
		
		//then
  	mockMvc.perform(post(PATH_ROOT+PATH_INSTALACAO))
  			.andExpectAll(
  					status().isFound(),
  					view().name(REDIRECT)
  			);
  	
  	verify(receitaDBPopulateService,times(0)).popularReceita();
	}
	
	@Test
  @DisplayName("3.2. Quando POST é chamado em PATH_INSTALACAO e NÃO existe informação no banco de dados então popula o banco de dados, status Found e redireciona para index")
  void whenPOSTIsCalledInPathInstalacaoAndThereIsNOInformationInTheDatabaseThenPopulaesTheDatabaseStatusFoundAndRedirectToIndexPath() throws Exception {
  	//when
		when(principalService.verificaSeInstalacaoFoiEfetuada()).thenReturn(Boolean.FALSE);
		
		//then
  	mockMvc.perform(post(PATH_ROOT+PATH_INSTALACAO))
  			.andExpectAll(
  					status().isFound(),
  					view().name(REDIRECT)
  			);
  	
  	verify(receitaDBPopulateService,times(1)).popularReceita();
  	
	}
	
	@Test
  @DisplayName("4. Quando GET é chamado em PATH_SOBRE e existe informacao no banco de dados então status ok, página index, listas de receitas, ingredientes, unidades de medidas e usuários são retornados")
  void whenGETIsCalledInPathSobreAndThereIsInformationInTheDatabaseThenStatusOkIndexPageListsOfReceitaIngredienteUnidadeMedidaUsuarioAreReturned() throws Exception {
		//given
		Map<String, Integer> quantidadeListas = new HashMap<>();
		quantidadeListas.put("usuarios", 1);
		quantidadeListas.put("unidadesMedida", 2);
		quantidadeListas.put("ingredientes", 3);
		quantidadeListas.put("receitas", 4);
		
		//when
		when(principalService.quantidadeListas()).thenReturn(quantidadeListas);
		when(principalService.verificaSeInstalacaoFoiEfetuada()).thenReturn(Boolean.TRUE);
		
		//then
  	ModelAndView mv = mockMvc.perform(get(PATH_ROOT+PATH_SOBRE))
  			.andExpectAll(
  					status().isOk(),
  					view().name(SOBRE)
  			)
  			.andReturn()
  			.getModelAndView();
  	
  	assertAll(
  		"verifica se retorna lista de Receitas e se existe Map de média de Avaliações"
  			,() -> assertModelAttributeValue(mv, OBJETO_USUARIO_QUANTIDADE, 1)
  			,() -> assertModelAttributeValue(mv, OBJETO_UNIDADE_MEDIDA_QUANTIDADE, 2)
  			,() -> assertModelAttributeValue(mv, OBJETO_INGREDIENTE_QUANTIDADE, 3)
  			,() -> assertModelAttributeValue(mv, OBJETO_RECEITA_QUANTIDADE, 4)
  		
  	);
  	
	}
	
	@Test
  @DisplayName("4.2 Quando GET é chamado em PATH_SOBRE e NÃO existe informação no banco de dados então status found e redireciona para instalação")
  void whenGETIsCalledInPathSobreAndThereIsNoInformationInTheDatabaseThenStatusFoundAndRedirectToInstalacaoPath() throws Exception {
  	//when
		when(principalService.verificaSeInstalacaoFoiEfetuada()).thenReturn(Boolean.FALSE);
		
		//then
  	mockMvc.perform(get(PATH_ROOT+PATH_SOBRE))
  			.andExpectAll(
  					status().isFound(),
  					view().name(REDIRECT+PATH_INSTALACAO)
  			);
  	
	}
}
