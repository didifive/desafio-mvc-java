package com.gft.atr.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.MultipleFailuresError;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.gft.atr.entities.Ingrediente;
import com.gft.atr.entities.Receita;
import com.gft.atr.entities.Usuario;
import com.gft.atr.exceptions.ImagemComFormatoIncompativelException;
import com.gft.atr.exceptions.ReceitaNaoEncontradaException;
import com.gft.atr.repositories.ReceitaRepository;
import com.gft.atr.utils.IngredienteUtils;
import com.gft.atr.utils.ReceitaUtils;
import com.gft.atr.utils.UsuarioUtils;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes de ReceitaService")
class ReceitaServiceTest {
	
	private static final Long ID_INVALIDO = 2L;
	
	@Mock
  private ReceitaRepository receitaRepository;
	
	@Mock
  private UsuarioService usuarioService;
  
  @InjectMocks
  private ReceitaService receitaService;
  
	private Receita receita;
	
	private void assertReceita(Receita receitaAtual) throws MultipleFailuresError {
  	// given
  	Receita receitaEsperada = ReceitaUtils.createFakeEntity();
  	
  	assertAll(
  		"confirma que a retornou objeto salvo e verifica se atributos são conforme o esperado."
  		,() -> assertThat(receitaAtual).isNotNull()
  		,() -> assertEquals(receitaEsperada.getId(),				receitaAtual.getId())
  		,() -> assertEquals(receitaEsperada.getNome(), 			receitaAtual.getNome())
  		,() -> assertEquals(receitaEsperada.getImagem(), 		receitaAtual.getImagem())
  		,() -> assertEquals(receitaEsperada.getDescricao(),	receitaAtual.getDescricao())

    );
	}
	
  
  private void assertListaReceita(List<Receita> receitasListadas) {
  	// given
  	Receita receitaEsperada = ReceitaUtils.createFakeEntity();
   	
  	assertAll(
  		"verifica a lista de Receitas, não pode estar vazia e deve conter a Receita especificada"
  		,() -> assertThat(receitasListadas).isNotEmpty()
  		,() -> assertThat(receitasListadas.get(0)).isEqualTo(receitaEsperada)
    );
  }
	
	@BeforeEach
	void setup() {
		receita = ReceitaUtils.createFakeEntity();
	}
	
	@Test
  @DisplayName("1. Deve salvar Receita")
	void shouldSaveReceita() throws Exception {
    //given
  	Receita receitaSemId = ReceitaUtils.createFakeEntityWithoutId();
  	
  	// when
    when(receitaRepository.save(receitaSemId)).thenReturn(receita);

    //then
    assertReceita(receitaService.salvarReceita(receitaSemId));
	}

  @Test
  @DisplayName("2. Deve listar todas Receitas")
	void shouldListAllReceitas() throws Exception {
    // when
    when(receitaRepository.findAll()).thenReturn(Collections.singletonList(receita));

    //then
    assertListaReceita(receitaService.listarReceitas(null, null, null));
	}
  
  @Test
  @DisplayName("2.1. Deve listar Receitas por Nome")
	void shouldListReceitasByNome() throws Exception {
    // when
    when(receitaRepository.findAllByNomeContains(receita.getNome()))
    							.thenReturn(Collections.singletonList(receita)
    );

    //then
    assertListaReceita(receitaService.listarReceitas(receita.getNome(),null,null));
	}
  
  @Test
  @DisplayName("2.2. Deve listar Receitas por Usuario")
	void shouldListReceitasByUsuario() throws Exception {
    //given
  	Usuario usuario = UsuarioUtils.createFakeEntity();
  	
  	// when
    when(receitaRepository.findAllByNomeContainsAndUsuarioId(null,usuario.getId()))
    							.thenReturn(Collections.singletonList(receita)
    );

    //then
    assertListaReceita(receitaService.listarReceitas(null, null, usuario.getId()));
	}
  
  @Test
  @DisplayName("2.3. Deve listar Receitas por Ingrediente")
	void shouldListReceitasByIngrediente() throws Exception {
    //given
  	Ingrediente ingrediente = IngredienteUtils.createFakeEntity();
  	
  	// when
    when(receitaRepository.findAllByNomeContainsAndIngredientesReceitaIngredientePKIngredienteNomeContains(null,ingrediente.getNome()))
    							.thenReturn(Collections.singletonList(receita)
    );

    //then
    assertListaReceita(receitaService.listarReceitas(null, ingrediente.getNome(), null));
	}
  
  @Test
  @DisplayName("2.4. Deve listar Receitas por Usuario e Ingrediente")
	void shouldListReceitasByUsuarioAndIngrediente() throws Exception {
    //given
  	Ingrediente ingrediente = IngredienteUtils.createFakeEntity();
  	Usuario usuario = UsuarioUtils.createFakeEntity();
  	
  	// when
    when(receitaRepository
    			.findAllByNomeContainsAndIngredientesReceitaIngredientePKIngredienteNomeContainsAndUsuarioId(null,ingrediente.getNome(),usuario.getId()))
    							.thenReturn(Collections.singletonList(receita)
    );

    //then
    assertListaReceita(receitaService.listarReceitas(null, ingrediente.getNome(), usuario.getId()));
	}
  
  @Test
  @DisplayName("3. Quando vai buscar e o Id de Receita dado é válido, então deve retornar Receita")
	void whenFetchAndTheValidReceitaIdIsGivenThenShouldReturnReceita() throws Exception {
  	// when
    when(receitaRepository.findById(receita.getId())).thenReturn(Optional.of(receita));

    //then
    assertReceita(receitaService.obterReceita(receita.getId()));
  }
    
  @Test
  @DisplayName("3.1. Quando um Id Inválido de Receita é dado, então deve lançar ReceitaNaoEncontradaException")
	void whenFetchAndTheInvalidReceitaIdIsGivenThenShouldThrowReceitaNaoEncontradaException() throws Exception {
    	// when
      when(receitaRepository.findById(ID_INVALIDO)).thenReturn(Optional.empty());

      //then
      assertThrows(ReceitaNaoEncontradaException.class, () -> receitaService.obterReceita(ID_INVALIDO));
	}
	
  @Test
  @DisplayName("4. Quando vai excluir e o Id de Receita dado é válido, então deve excluir a Receita")
	void whenWillDeleteAndValidReceitaIdIsGivenThenShouldDeleteReceita() throws Exception {
  	// when
    when(receitaRepository.findById(receita.getId())).thenReturn(Optional.of(receita));

    //then
    receitaService.excluirReceita(receita.getId());
    
    assertAll(
    	"verifica se a busca e o delete de ingredienteRepository executou"
    	,() -> verify(receitaRepository, times(1)).findById(receita.getId())
    	,() -> verify(receitaRepository, times(1)).deleteById(receita.getId())
    );
  }
    
  @Test
  @DisplayName("4.1. Quando vai excluir e um Id inválido de Receita é dado, então deve lançar ReceitaNaoEncontradaException")
	void whenWillDeleteAndInvalidReceitaIdIsGivenThenShouldThrowReceitaNaoEncontradaException() throws Exception {
  	// when
		when(receitaRepository.findById(ID_INVALIDO)).thenReturn(Optional.empty());

		//then
		assertThrows(ReceitaNaoEncontradaException.class, () -> receitaService.excluirReceita(ID_INVALIDO));
	}
  
  @Test
  @DisplayName("5. Deve setar atributo imagem quando informado URL de imagem")
	void ShouldSetImageAttributeWhenGivenImageURL() throws Exception {
  	// given
  	String imagemUrl = "https://st2.depositphotos.com/1177973/6700/i/450/depositphotos_67008855-stock-photo-flour-in-burlap-bag-on.jpg";
  	
  	//when
  	receitaService.adicionarImagemURL(receita, imagemUrl);
  	
		//then
  	assertEquals(imagemUrl, receita.getImagem());
	}
  
  @Test
  @DisplayName("5.2. Quando informado URL com extensão diferente de png, jpg ou jpeg, deve lançar ImagemComFormatoIncompativelException")
	void whenGivenImageURLWithExtensionOtherThanPngJpgOrJpegShouldThrowImagemComFormatoIncompativelException() throws Exception {
  	// given
  	String imagemUrl = "https://media.giphy.com/media/YmgiG68VAsIX5LSas8/giphy.gif";
  	
		//then
  	assertThrows(ImagemComFormatoIncompativelException.class, () -> receitaService.adicionarImagemURL(receita, imagemUrl));
	}
  
  @Test
  @DisplayName("6. Deve setar atributo imagem quando enviado arquivo de imagem")
	void ShouldSetImageAttributeWhenGivenImageFile() throws Exception {
  	// given
  	String imagemPath = "src/main/resources/static/assets/images/no-image.png";
  	File file = new File(imagemPath);
  	MultipartFile multipartFile = new MockMultipartFile("file",
  	            file.getName(), "image/png", FileUtils.readFileToByteArray(file));
  	
  	String imagemEncodada = Base64.encodeBase64String(multipartFile.getBytes());;
  	
  	
  	//when
  	receitaService.adicionarImagemArquivo(receita, multipartFile);
  	
		//then
  	assertEquals("data:image/png;base64,"+imagemEncodada
  									, receita.getImagem());
	}
  
  @Test
  @DisplayName("6.2. Quando enviada imagem com formatação diferente de png, jpg ou jpeg, deve lançar ImagemComFormatoIncompativelException")
  void whenGivenImageFileWithTypeOtherThanPngJpgOrJpegShouldThrowImagemComFormatoIncompativelException() throws Exception {
  	// given
  	String imagemPath = "src/main/resources/static/assets/images/no-image.png";
  	File file = new File(imagemPath);
  	MultipartFile multipartFile = new MockMultipartFile("file",
  	            file.getName(), "image/gif", FileUtils.readFileToByteArray(file));
 	
  	//then
  	assertThrows(ImagemComFormatoIncompativelException.class, () -> receitaService.adicionarImagemArquivo(receita, multipartFile));
	}

}
