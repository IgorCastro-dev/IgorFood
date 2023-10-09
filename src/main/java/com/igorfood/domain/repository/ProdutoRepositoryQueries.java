package com.igorfood.domain.repository;

import com.igorfood.domain.model.FotoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProdutoRepositoryQueries{
    FotoProduto save(FotoProduto fotoProduto);

    void delete(FotoProduto fotoProduto);

}
