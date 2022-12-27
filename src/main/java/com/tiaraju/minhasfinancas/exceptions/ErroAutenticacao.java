package com.tiaraju.minhasfinancas.exceptions;

public class ErroAutenticacao extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1860531718947759098L;

	public ErroAutenticacao(String mensagem) {
		super(mensagem);
	}

}
