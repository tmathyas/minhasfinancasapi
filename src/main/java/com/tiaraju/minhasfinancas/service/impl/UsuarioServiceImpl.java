package com.tiaraju.minhasfinancas.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tiaraju.minhasfinancas.exceptions.ErroAutenticacao;
import com.tiaraju.minhasfinancas.exceptions.RegraNegocioException;
import com.tiaraju.minhasfinancas.model.entity.Usuario;
import com.tiaraju.minhasfinancas.repository.UsuarioRepository;
import com.tiaraju.minhasfinancas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioRepository repository;

	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = repository.findByEmail(email);

		if (usuario.isEmpty()) {
			throw new ErroAutenticacao("Usuário não encontrado para o e-mail informado.");
		}

		if (usuario.get().getSenha().equals(senha)) {
			return usuario.get();
		} else {
			throw new ErroAutenticacao("Senha inválida.");
		}
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		boolean result = repository.existsByEmail(email);
		if (result) {
			throw new RegraNegocioException("Já existe um susuario com esse email");
		}
	}

	@Override
	public Optional<Usuario> obterPorId(Long id) {
		if(id != null)
			return repository.findById(id);
		else
			return Optional.empty();
	}

}
