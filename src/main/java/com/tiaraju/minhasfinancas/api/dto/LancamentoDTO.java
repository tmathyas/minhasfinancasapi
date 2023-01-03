package com.tiaraju.minhasfinancas.api.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
@Builder
public class LancamentoDTO {

	private Long id;
	
	private String descricao;
	
	private Long mes;
	
	private Long ano;
	
	private BigDecimal valor;
	
	private Long usuario;
	
	private String tipo;
	
	private String status;
}
