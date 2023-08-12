package com.igorfood.domain.repository;

import com.igorfood.domain.model.Grupo;
import com.igorfood.domain.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface PermissaoRepository extends JpaRepository<Permissao,Long> {

}
