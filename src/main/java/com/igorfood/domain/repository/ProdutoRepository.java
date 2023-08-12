package com.igorfood.domain.repository;

import com.igorfood.domain.model.Produto;
import com.igorfood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {

    @Query("from Produto where restaurante.id = :restaurante and id = :produto")
    Optional<Produto> findById(
            @Param("restaurante") Long restauranteId,
            @Param("produto") Long produtoId);

    Collection<Produto> findByRestaurante(Restaurante restaurante);
}
