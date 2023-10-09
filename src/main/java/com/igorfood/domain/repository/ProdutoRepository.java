package com.igorfood.domain.repository;

import com.igorfood.domain.model.FotoProduto;
import com.igorfood.domain.model.Produto;
import com.igorfood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Long>, ProdutoRepositoryQueries{

    @Query("from Produto where restaurante.id = :restaurante and id = :produto")
    Optional<Produto> findById(
            @Param("restaurante") Long restauranteId,
            @Param("produto") Long produtoId);
    Collection<Produto> findByRestaurante(Restaurante restaurante);

    @Query("from Produto p where p.ativo = true and p.restaurante = :restaurante")
    Collection<Produto> findAtivosByRestaurante(Restaurante restaurante);

    @Query("select f from FotoProduto f join f.produto p where f.id = :produtoId AND " +
            "p.restaurante.id = :restauranteId")
    Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);
}
