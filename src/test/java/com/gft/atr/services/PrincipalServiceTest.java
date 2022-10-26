package com.gft.atr.services;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gft.atr.utils.IngredienteUtils;
import com.gft.atr.utils.ReceitaUtils;
import com.gft.atr.utils.UnidadeMedidaUtils;
import com.gft.atr.utils.UsuarioUtils;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes de PrincipalService")
class PrincipalServiceTest {

	@Mock
	ReceitaService receitaService;
	
	@Mock
	IngredienteService ingredienteService;
	
	@Mock
	UnidadeMedidaService unidadeMedidaService;
	
	@Mock
	UsuarioService usuarioService;
	
  @InjectMocks
  private PrincipalService principalService;
  
  @Test
  @DisplayName("1. Quando todas as listas estão vazias, verificaSeInstalacaoFoiEfetuada deve retornar false")
	void whenAllListSAreEmptyVerificaSeInstalacaoFoiEfetuadaShouldReturnFalse() throws Exception {
  	//when
  	when(receitaService.listarReceitas(null, null, null)).thenReturn(Collections.emptyList());
  	when(ingredienteService.listarIngredientes(null)).thenReturn(Collections.emptyList());
  	when(unidadeMedidaService.listarUnidadesMedida(null)).thenReturn(Collections.emptyList());
  	when(usuarioService.listarUsuarios(null)).thenReturn(Collections.emptyList());
  	
  	//then
  	assertFalse(principalService.verificaSeInstalacaoFoiEfetuada());
  	
  }
  
  @Test
  @DisplayName("2. Quando existir algum Usuario, verificaSeInstalacaoFoiEfetuada deve retornar true")
	void whenThereIsAnyUsuarioVerificaSeInstalacaoFoiEfetuadaShouldReturnFalse() throws Exception {
  	//when
  	when(receitaService.listarReceitas(null, null, null)).thenReturn(Collections.emptyList());
  	when(ingredienteService.listarIngredientes(null)).thenReturn(Collections.emptyList());
  	when(unidadeMedidaService.listarUnidadesMedida(null)).thenReturn(Collections.emptyList());
  	when(usuarioService.listarUsuarios(null)).thenReturn(Collections.singletonList(UsuarioUtils.createFakeEntity()));
  	
  	//then
  	assertTrue(principalService.verificaSeInstalacaoFoiEfetuada());
  	
  }
  
  @Test
  @DisplayName("3. Quando existir alguma UnidadeMedida, verificaSeInstalacaoFoiEfetuada deve retornar true")
	void whenThereIsAnyUnidadeMedidaVerificaSeInstalacaoFoiEfetuadaShouldReturnFalse() throws Exception {
  	//when
  	when(receitaService.listarReceitas(null, null, null)).thenReturn(Collections.emptyList());
  	when(ingredienteService.listarIngredientes(null)).thenReturn(Collections.emptyList());
  	when(unidadeMedidaService.listarUnidadesMedida(null)).thenReturn(Collections.singletonList(UnidadeMedidaUtils.createFakeEntity()));
  	when(usuarioService.listarUsuarios(null)).thenReturn(Collections.emptyList());
  	
  	//then
  	assertTrue(principalService.verificaSeInstalacaoFoiEfetuada());
  	
  }
  
  @Test
  @DisplayName("4. Quando existir algum Ingrediente, verificaSeInstalacaoFoiEfetuada deve retornar true")
	void whenThereIsAnyIngredienteVerificaSeInstalacaoFoiEfetuadaShouldReturnFalse() throws Exception {
  	//when
  	when(receitaService.listarReceitas(null, null, null)).thenReturn(Collections.emptyList());
  	when(ingredienteService.listarIngredientes(null)).thenReturn(Collections.singletonList(IngredienteUtils.createFakeEntity()));
  	when(unidadeMedidaService.listarUnidadesMedida(null)).thenReturn(Collections.emptyList());
  	when(usuarioService.listarUsuarios(null)).thenReturn(Collections.emptyList());
  	
  	//then
  	assertTrue(principalService.verificaSeInstalacaoFoiEfetuada());
  	
  }
  
  @Test
  @DisplayName("5. Quando existir alguma Receita, verificaSeInstalacaoFoiEfetuada deve retornar true")
	void whenThereIsAnyReceitaVerificaSeInstalacaoFoiEfetuadaShouldReturnFalse() throws Exception {
  	//when
  	when(receitaService.listarReceitas(null, null, null)).thenReturn(Collections.singletonList(ReceitaUtils.createFakeEntity()));
  	when(ingredienteService.listarIngredientes(null)).thenReturn(Collections.emptyList());
  	when(unidadeMedidaService.listarUnidadesMedida(null)).thenReturn(Collections.emptyList());
  	when(usuarioService.listarUsuarios(null)).thenReturn(Collections.emptyList());
  	
  	//then
  	assertTrue(principalService.verificaSeInstalacaoFoiEfetuada());
  }
  
  @Test
  @DisplayName("6. deve retornar quantidade de usuarios, unidades de medida, ingredientes e receitas")
	void shouldReturnNumberOfUsuarioUnidadeMedidaIngredienteAndReceita() throws Exception {
  	//when
  	when(receitaService.listarReceitas(null, null, null)).thenReturn(Collections.singletonList(ReceitaUtils.createFakeEntity()));
  	when(ingredienteService.listarIngredientes(null)).thenReturn(Collections.singletonList(IngredienteUtils.createFakeEntity()));
  	when(unidadeMedidaService.listarUnidadesMedida(null)).thenReturn(Collections.singletonList(UnidadeMedidaUtils.createFakeEntity()));
  	when(usuarioService.listarUsuarios(null)).thenReturn(Collections.singletonList(UsuarioUtils.createFakeEntity()));
  	
  	//then
  	Map<String, Integer> quantidadeListas = principalService.quantidadeListas();
  	
  	assertAll(
  			"Verifica se retornou um Map e se rodou o método e pegar listas em seus respectivos services"
  			, () -> verify(unidadeMedidaService, times(1)).listarUnidadesMedida(null)
				, () -> verify(ingredienteService, times(1)).listarIngredientes(null)
				, () -> verify(receitaService, times(1)).listarReceitas(null, null, null)
				, () -> verify(usuarioService, times(1)).listarUsuarios(null)
				, () -> assertEquals(1, quantidadeListas.get("usuarios"))
				, () -> assertEquals(1, quantidadeListas.get("unidadesMedida"))
				, () -> assertEquals(1, quantidadeListas.get("ingredientes"))
				, () -> assertEquals(1, quantidadeListas.get("receitas"))
  	);
 
  }
  
}
