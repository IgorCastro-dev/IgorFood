package com.igorfood.domain.repository;

import com.igorfood.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento,Long> {
}
