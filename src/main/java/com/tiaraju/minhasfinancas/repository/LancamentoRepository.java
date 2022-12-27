package com.tiaraju.minhasfinancas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tiaraju.minhasfinancas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
