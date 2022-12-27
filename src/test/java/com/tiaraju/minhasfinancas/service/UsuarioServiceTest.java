package com.tiaraju.minhasfinancas.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import com.tiaraju.minhasfinancas.exceptions.ErroAutenticacao;
import com.tiaraju.minhasfinancas.exceptions.RegraNegocioException;
import com.tiaraju.minhasfinancas.model.entity.Usuario;
import com.tiaraju.minhasfinancas.repository.UsuarioRepository;
import com.tiaraju.minhasfinancas.service.impl.UsuarioServiceImpl;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@SpyBean
	UsuarioServiceImpl service;

	@MockBean
	UsuarioRepository repository;

	@BeforeEach
	public void setUp() {
//		Mockito.spy(UsuarioServiceImpl.class);

//		service = new UsuarioServiceImpl(repository);
	}

	@Test
	public void deveAutenticarUmUsuarioComSucesso() {
		// cenário
		String email = "tmathyas@gmail.com";
		String senha = "123456";

		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

		// ação
		Usuario resultado = service.autenticar(email, senha);

		// verificação
		Assertions.assertNotNull(resultado);
	}

	@Test
	public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailCadastrado() {
		ErroAutenticacao erroAutenticacao = Assertions.assertThrows(ErroAutenticacao.class, () -> {
			// cenário
			Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

			// ação
			String email = "tmathyas@gmail.com";
			String senha = "123456";
			service.autenticar(email, senha);
		});
		Assertions.assertTrue(
				erroAutenticacao.getMessage().contentEquals("Usuário não encontrado para o e-mail informado."));
	}

	@Test
	public void deveLancarErroQuandoSenhaNaoBater() {
		ErroAutenticacao erroAutenticacao = Assertions.assertThrows(ErroAutenticacao.class, () -> {
			// cenário
			String senha = "123456";
			Usuario usuario = Usuario.builder().email("tmathyas@gmail.com").senha(senha).build();

			Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

			// ação
			service.autenticar("tmathyas@gmail.com", "senha");
		});
		Assertions.assertTrue(erroAutenticacao.getMessage().contentEquals("Senha inválida."));
	}

	@Test
	public void deveValidarEmail() {
		Assertions.assertDoesNotThrow(() -> {
			// cenário
			Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

			// ação
			service.validarEmail("tmathyas@gmail.com");
		});
	}

	@Test
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		Assertions.assertThrows(RegraNegocioException.class, () -> {

			// cenário
			Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

			// ação
			service.validarEmail("tmathyas@gmail.com");
		});
	}

	@Test
	public void deveSalvarUmUsuario() {
		// cenário
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
		Usuario usuario = Usuario.builder().id(1l).nome("Tiaraju").email("tmathyas@gmail.com").senha("123456").build();
		Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		
		// Ação
		Usuario usuarioSalvo = service.salvarUsuario(usuario);

		// Verificação
		Assertions.assertNotNull(usuarioSalvo);
		Assertions.assertTrue(usuarioSalvo.getId().equals(1l));
		Assertions.assertTrue(usuarioSalvo.getNome().equals("Tiaraju"));
		Assertions.assertTrue(usuarioSalvo.getEmail().equals("tmathyas@gmail.com"));
		Assertions.assertTrue(usuarioSalvo.getSenha().equals("123456"));
	}
	
	@Test
	public void naoDeveSalvarUmUsuarioComEmailJaCadastrado() {
		Assertions.assertThrows(RegraNegocioException.class, () -> {
		//cenário
		String email = "tmathyas@gmail.com";
		Usuario usuario = Usuario.builder().email(email).build();
		
		Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);
		
		//Ação
		service.salvarUsuario(usuario);
		
		//verificação
		Mockito.verify(repository, Mockito.never()).save(usuario);
		
		});
	}
}
