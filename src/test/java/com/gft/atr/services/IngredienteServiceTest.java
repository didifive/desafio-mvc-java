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
import com.gft.atr.exceptions.ImagemComFormatoIncompativelException;
import com.gft.atr.exceptions.IngredienteNaoEncontradoException;
import com.gft.atr.repositories.IngredienteRepository;
import com.gft.atr.utils.IngredienteUtils;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes de IngredienteService")
class IngredienteServiceTest {
	
	private static final Long ID_INVALIDO = 2L;
	
	@Mock
  private IngredienteRepository ingredienteRepository;
  
  @InjectMocks
  private IngredienteService ingredienteService;
  
	private Ingrediente ingrediente;
	
	private void assertIngrediente(Ingrediente ingredienteAtual) throws MultipleFailuresError {
  	// given
  	Ingrediente ingredienteEsperado = IngredienteUtils.createFakeEntity();
  	
  	assertAll(
  		"confirma que a retornou objeto salvo e verifica se atributos são conforme o esperado."
  		,() -> assertThat(ingredienteAtual).isNotNull()
  		,() -> assertEquals(ingredienteEsperado.getId(),				ingredienteAtual.getId())
  		,() -> assertEquals(ingredienteEsperado.getNome(), 			ingredienteAtual.getNome())
  		,() -> assertEquals(ingredienteEsperado.getImagem(), 		ingredienteAtual.getImagem())
  		,() -> assertEquals(ingredienteEsperado.getDensidade(),	ingredienteAtual.getDensidade())
  		,() -> assertEquals(ingredienteEsperado.getReceitas(), 	ingredienteAtual.getReceitas())
    );
	}
	
  
  private void assertListaIngrediente(List<Ingrediente> ingredientesListados) {
  	// given
   	Ingrediente ingredienteEsperado = IngredienteUtils.createFakeEntity();
   	
  	assertAll(
  		"verifica a lista de Ingredientes, não pode estar vazia e deve conter o Ingrediente especificado"
  		,() -> assertThat(ingredientesListados).isNotEmpty()
  		,() -> assertThat(ingredientesListados.get(0)).isEqualTo(ingredienteEsperado)
    );
  }
	
	@BeforeEach
	void setup() {
		ingrediente = IngredienteUtils.createFakeEntity();
	}
	
	@Test
  @DisplayName("1. Deve salvar Ingrediente")
	void shouldSaveIngrediente() throws Exception {
    //given
  	Ingrediente ingredienteSemId = IngredienteUtils.createFakeEntityWithoutId();
  	
  	// when
    when(ingredienteRepository.save(ingredienteSemId)).thenReturn(ingrediente);

    //then
    assertIngrediente(ingredienteService.salvarIngrediente(ingredienteSemId));
	}

  @Test
  @DisplayName("2. Deve listar todos os Ingredientes")
	void shouldListAllIngredientes() throws Exception {
    // when
    when(ingredienteRepository.findAll()).thenReturn(Collections.singletonList(ingrediente));

    //then
    assertListaIngrediente(ingredienteService.listarIngredientes(null));
	}
  
  @Test
  @DisplayName("2.2. Deve listar Ingredientes por Nome")
	void shouldListIngredientesByNome() throws Exception {
    // when
    when(ingredienteRepository.findByNomeContains(ingrediente.getNome()))
    							.thenReturn(Collections.singletonList(ingrediente)
    );

    //then
    assertListaIngrediente(ingredienteService.listarIngredientes(ingrediente.getNome()));
	}
  
  
  @Test
  @DisplayName("3. Quando vai buscar e o Id de Ingrediente dado é válido, então deve retornar Ingrediente")
	void whenFetchAndTheValidIngredienteIdIsGivenThenShouldReturnIngrediente() throws Exception {
  	// when
    when(ingredienteRepository.findById(ingrediente.getId())).thenReturn(Optional.of(ingrediente));

    //then
    assertIngrediente(ingredienteService.obterIngrediente(ingrediente.getId()));
  }
    
  @Test
  @DisplayName("3.2. Quando um Id Inválido de Ingrediente é dado, então deve lançar IngredienteNaoEncontradoException")
	void whenFetchAndTheInvalidIngredienteIdIsGivenThenShouldThrowIngredienteNaoEncontradoException() throws Exception {
    	// when
      when(ingredienteRepository.findById(ID_INVALIDO)).thenReturn(Optional.empty());

      //then
      assertThrows(IngredienteNaoEncontradoException.class, () -> ingredienteService.obterIngrediente(ID_INVALIDO));
	}
	
  @Test
  @DisplayName("4. Quando vai excluir e o Id de Ingrediente dado é válido, então deve excluir o Ingrediente")
	void whenWillDeleteAndValidIngredienteIdIsGivenThenShouldDeleteIngrediente() throws Exception {
  	// when
    when(ingredienteRepository.findById(ingrediente.getId())).thenReturn(Optional.of(ingrediente));

    //then
    ingredienteService.excluirIngrediente(ingrediente.getId());
    
    assertAll(
    	"verifica se a busca e o delete de ingredienteRepository executou"
    	,() -> verify(ingredienteRepository, times(1)).findById(ingrediente.getId())
    	,() -> verify(ingredienteRepository, times(1)).deleteById(ingrediente.getId())
    );
  }
    
  @Test
  @DisplayName("4.2. Quando vai excluir e um Id inválido de Ingrediente é dado, então deve lançar IngredienteNaoEncontradoException")
	void whenWillDeleteAndInvalidIngredienteIdIsGivenThenShouldThrowIngredienteNaoEncontradoException() throws Exception {
  	// when
		when(ingredienteRepository.findById(ID_INVALIDO)).thenReturn(Optional.empty());

		//then
		assertThrows(IngredienteNaoEncontradoException.class, () -> ingredienteService.excluirIngrediente(ID_INVALIDO));
	}
  
  @Test
  @DisplayName("5. Deve setar atributo imagem quando informado URL de imagem")
	void ShouldSetImageAttributeWhenGivenImageURL() throws Exception {
  	// given
  	String imagemUrl = "https://st2.depositphotos.com/1177973/6700/i/450/depositphotos_67008855-stock-photo-flour-in-burlap-bag-on.jpg";
  	
  	//when
  	ingredienteService.adicionarImagemURL(ingrediente, imagemUrl);
  	
		//then
  	assertEquals(imagemUrl, ingrediente.getImagem());
	}
  
  @Test
  @DisplayName("5.2. Quando informado URL com extensão diferente de png, jpg ou jpeg, deve lançar ImagemComFormatoIncompativelException")
	void whenGivenImageURLWithExtensionOtherThanPngJpgOrJpegShouldThrowImagemComFormatoIncompativelException() throws Exception {
  	// given
  	String imagemUrl = "https://media.giphy.com/media/YmgiG68VAsIX5LSas8/giphy.gif";
  	
		//then
  	assertThrows(ImagemComFormatoIncompativelException.class, () -> ingredienteService.adicionarImagemURL(ingrediente, imagemUrl));
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
  	ingredienteService.adicionarImagemArquivo(ingrediente, multipartFile);
  	
		//then
  	assertEquals("data:image/png;base64,"+imagemEncodada
  									, ingrediente.getImagem());
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
  	assertThrows(ImagemComFormatoIncompativelException.class, () -> ingredienteService.adicionarImagemArquivo(ingrediente, multipartFile));
	}
  
  

}
