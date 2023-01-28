package com.tiaraju.minhasfinancas.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tiaraju.minhasfinancas.model.entity.Lancamento;
import com.tiaraju.minhasfinancas.model.enums.TipoLancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

	@Query(value = "SELECT SUM(L.valor) FROM Lancamento L JOIN L.usuario U WHERE U.id = :idUsuario AND L.tipo = :tipo and L.status = :status GROUP BY U ")
	BigDecimal obterSaldoPorTipoLancamentoEUsuario(@Param("idUsuario") Long idUsuario, @Param("tipo") TipoLancamento tipo);
}
