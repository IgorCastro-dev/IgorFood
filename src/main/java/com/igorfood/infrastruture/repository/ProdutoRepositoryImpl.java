package com.igorfood.infrastruture.repository;

import com.igorfood.domain.model.FotoProduto;
import com.igorfood.domain.repository.ProdutoRepositoryQueries;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public FotoProduto save(FotoProduto fotoProduto) {
        return entityManager.merge(fotoProduto);
    }


    @Transactional
    @Override
    public void delete(FotoProduto fotoProduto) {
        entityManager.remove(fotoProduto);
    }
}
