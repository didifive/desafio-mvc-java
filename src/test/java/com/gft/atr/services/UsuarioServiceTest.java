package com.gft.atr.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gft.atr.entities.Usuario;
import com.gft.atr.exceptions.UsuarioNaoEncontradoException;
import com.gft.atr.repositories.UsuarioRepository;
import com.gft.atr.utils.UsuarioUtils;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes de UsuarioService")
class UsuarioServiceTest {
	
	private static final Long ID_INVALIDO = 2L;
	
	@Mock
  private UsuarioRepository usuarioRepository;
	
	@Mock
	BCryptPasswordEncoder criptografia;
	
  @InjectMocks
  private UsuarioService usuarioService;
  
	private Usuario usuario;
	
	private void assertUsuario(Usuario usuarioAtual) throws MultipleFailuresError {
  	// given
  	Usuario usuarioEsperado = UsuarioUtils.createFakeEntity();
  	
  	assertAll(
  		"confirma que a retornou objeto salvo e verifica se atributos são conforme o esperado."
  		,() -> assertThat(usuarioAtual).isNotNull()
  		,() -> assertEquals(usuarioEsperado.getId(),					usuarioAtual.getId())
  		,() -> assertEquals(usuarioEsperado.getNome(), 				usuarioAtual.getNome())
  		,() -> assertEquals(usuarioEsperado.getNomeUsuario(),	usuarioAtual.getNomeUsuario())
  		,() -> assertEquals(usuarioEsperado.getSenha(), 			usuarioAtual.getSenha())
  		,() -> assertEquals(usuarioEsperado.getEmail(), 			usuarioAtual.getEmail())
  		,() -> assertEquals(usuarioEsperado.getPermissoes(), 	usuarioAtual.getPermissoes())
  		,() -> assertEquals(usuarioEsperado.isAtivo(), 				usuarioAtual.isAtivo())
  		,() -> assertEquals(usuarioEsperado.getReceitas(), 		usuarioAtual.getReceitas())
  		,() -> assertEquals(usuarioEsperado.getAvaliacoes(), 	usuarioAtual.getAvaliacoes())

    );
	}
	
  
  private void assertListaUsuario(List<Usuario> usuariosListados) {
  	// given
  	Usuario usuarioEsperado = UsuarioUtils.createFakeEntity();
   	
  	assertAll(
  		"verifica a lista de Usuários, não pode estar vazia e deve conter o Usuário especificado"
  		,() -> assertThat(usuariosListados).isNotEmpty()
  		,() -> assertThat(usuariosListados.get(0)).isEqualTo(usuarioEsperado)
    );
  }
	
	@BeforeEach
	void setup() {
		usuario = UsuarioUtils.createFakeEntity();
	}
	
	@Test
  @DisplayName("1. Deve salvar Usuário")
	void shouldSaveUsuario() throws Exception {
    //given
  	Usuario usuarioSemId = UsuarioUtils.createFakeEntityWithoutId();
  	
  	// when
    when(usuarioRepository.save(usuarioSemId)).thenReturn(usuario);

    //then
    assertUsuario(usuarioService.salvarUsuario(usuarioSemId));
	}

  @Test
  @DisplayName("2. Deve listar todos Usuários")
	void shouldListAllUsuarios() throws Exception {
    // when
    when(usuarioRepository.findAll()).thenReturn(Collections.singletonList(usuario));

    //then
    assertListaUsuario(usuarioService.listarUsuarios(null));
	}
  
  @Test
  @DisplayName("2.2. Deve listar Usuarios por Nome")
	void shouldListUsuariosByNome() throws Exception {
    // when
    when(usuarioRepository.findByNomeContainsAndNomeUsuarioContains(usuario.getNome(), usuario.getNome()))
    							.thenReturn(Collections.singletonList(usuario)
    );

    //then
    assertListaUsuario(usuarioService.listarUsuarios(usuario.getNome()));
	}
  
  @Test
  @DisplayName("2.3. Deve listar Usuarios por Nome de Usuario")
	void shouldListUsuariosByNomeUsuario() throws Exception {
  	// when
    when(usuarioRepository.findByNomeContainsAndNomeUsuarioContains(usuario.getNomeUsuario(), usuario.getNomeUsuario()))
    							.thenReturn(Collections.singletonList(usuario)
    );

    //then
    assertListaUsuario(usuarioService.listarUsuarios(usuario.getNomeUsuario()));
	}
  
  
  @Test
  @DisplayName("3. Quando vai buscar e o Id de Usuario dado é válido, então deve retornar Usuario")
	void whenFetchAndTheValidUsuarioIdIsGivenThenShouldReturnUsuario() throws Exception {
  	// when
    when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

    //then
    assertUsuario(usuarioService.obterUsuario(usuario.getId()));
  }
    
  @Test
  @DisplayName("3.2. Quando vai buscar e um Id Inválido de Usuario é dado, então deve lançar UsuarioNaoEncontradoException")
	void whenFetchAndTheInvalidUsuarioIdIsGivenThenShouldThrowUsuarioNaoEncontradoException() throws Exception {
  	// when
    when(usuarioRepository.findById(ID_INVALIDO)).thenReturn(Optional.empty());

    //then
    assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.obterUsuario(ID_INVALIDO));
	}
  
  @Test
  @DisplayName("3.3, Quando vai buscar e o Nome de Usuario dado é válido, então deve retornar Usuario")
	void whenFetchAndTheValidUsuarioUserNameIsGivenThenShouldReturnUsuario() throws Exception {
  	// when
    when(usuarioRepository.findByNomeUsuario(usuario.getNomeUsuario())).thenReturn(usuario);

    //then
    assertUsuario(usuarioService.obterUsuario(usuario.getNomeUsuario()));
  }
  
  @Test
  @DisplayName("4. Quando vai ativar Usuário e o Id de Usuario dado é válido, então deve ativar Usuario")
	void whenWillActivateUsuarioAndTheValidUsuarioIdIsGivenThenShouldActivateUsuario() throws Exception {
  	//given
  	usuario.setAtivo(false);
  	
  	// when
    when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

    //then
    usuarioService.ativarUsuario(usuario.getId());
    
    assertAll(
      	"verifica se a busca de usuarioRepository executou e se Usuário está ativo"
      	,() -> verify(usuarioRepository, times(1)).findById(usuario.getId())
      	,() -> assertEquals(true, usuario.isAtivo())
      );
  }
  
  @Test
  @DisplayName("4.2. Quando vai ativar Usuário e um Id Inválido de Usuario é dado, então deve lançar UsuarioNaoEncontradoException")
	void whenWillActivateUsuarioAndTheInvalidUsuarioIdIsGivenThenShouldThrowUsuarioNaoEncontradoException() throws Exception {
		// when
    when(usuarioRepository.findById(ID_INVALIDO)).thenReturn(Optional.empty());

    //then
    assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.ativarUsuario(ID_INVALIDO));
	}
  
  @Test
  @DisplayName("5. Quando vai desativar Usuário e o Id de Usuario dado é válido, então deve desativar Usuario")
	void whenWillDisableUsuarioAndTheValidUsuarioIdIsGivenThenShouldDisableUsuario() throws Exception {
  	//given
  	usuario.setAtivo(true);
  	
  	// when
    when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

    //then
    usuarioService.desativarUsuario(usuario.getId());
    
    assertAll(
      	"verifica se a busca de usuarioRepository executou e se Usuário está desativo"
      	,() -> verify(usuarioRepository, times(1)).findById(usuario.getId())
      	,() -> assertEquals(false, usuario.isAtivo())
      );
  }
  
  @Test
  @DisplayName("5.2. Quando vai desativar Usuário e um Id Inválido de Usuario é dado, então deve lançar UsuarioNaoEncontradoException")
	void whenWillDisableUsuarioAndTheInvalidUsuarioIdIsGivenThenShouldThrowUsuarioNaoEncontradoException() throws Exception {
  	// when
    when(usuarioRepository.findById(ID_INVALIDO)).thenReturn(Optional.empty());

    //then
    assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.desativarUsuario(ID_INVALIDO));
	}
	
  @Test
  @DisplayName("6. Quando vai excluir e o Id de Usuario dado é válido, então deve excluir o Usuario")
	void whenWillDeleteAndValidUsuarioIdIsGivenThenShouldDeleteUsuario() throws Exception {
  	// when
    when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

    //then
    usuarioService.excluirUsuario(usuario.getId());
    
    assertAll(
    	"verifica se a busca e o delete de ingredienteRepository executou"
    	,() -> verify(usuarioRepository, times(1)).findById(usuario.getId())
    	,() -> verify(usuarioRepository, times(1)).deleteById(usuario.getId())
    );
  }
    
  @Test
  @DisplayName("6.2. Quando vai excluir e um Id inválido de Usuário é dado, então deve lançar UsuarioNaoEncontradoException")
	void whenWillDeleteAndInvalidUsuarioIdIsGivenThenShouldThrowUsuarioNaoEncontradoException() throws Exception {
  	// when
		when(usuarioRepository.findById(ID_INVALIDO)).thenReturn(Optional.empty());

		//then
		assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.excluirUsuario(ID_INVALIDO));
	}

}
