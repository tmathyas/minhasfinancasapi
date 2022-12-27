package com.tiaraju.minhasfinancas.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.tiaraju.minhasfinancas.model.entity.Usuario;
import com.tiaraju.minhasfinancas.repository.UsuarioRepository;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository repository;

	@Autowired
	TestEntityManager entityManager;

	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		// cenário
		Usuario usuario = Usuario.builder().nome("Tiaraju").email("tmathyas@gmail.com").build();
		entityManager.persist(usuario);

		// ação - execução
		boolean result = repository.existsByEmail("tmathyas@gmail.com");

		// verificação
		Assertions.assertThat(result).isTrue();
	}

	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
		// cenário

		// ação
		boolean result = repository.existsByEmail("tmathyas@gmail.com");

		// Verificação
		Assertions.assertThat(result).isFalse();
	}

	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() {
		// cenário
		Usuario usuario = Usuario.builder().nome("Tiaraju").email("tmathyas@gmail.com").senha("123456").build();

		// ação
		Usuario usuarioSalvo = repository.save(usuario);

		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
	}

	@Test
	public void deveBuscarUmUsuarioPorEmail() {
		// cenário
		Usuario usuario = criarUsuario();

		entityManager.persist(usuario);

		// verificacao
		Optional<Usuario> result = repository.findByEmail("tmathyas@gmail.com");

		Assertions.assertThat(result.isPresent()).isTrue();
	}

	@Test
	public void deveRetornarVazioAoBuscarUsuarioPorEmailQuandoNaoExisteNaBase() {
		// verificacao
		Optional<Usuario> result = repository.findByEmail("tmathyas@gmail.com");

		Assertions.assertThat(result.isPresent()).isFalse();
	}

	public static Usuario criarUsuario() {
		return Usuario.builder().nome("Tiaraju").email("tmathyas@gmail.com").senha("123456").build();
	}
}
