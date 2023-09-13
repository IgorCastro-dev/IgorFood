package com.igorfood.services;

import com.igorfood.domain.model.Produto;
import com.igorfood.domain.model.Restaurante;
import com.igorfood.domain.repository.ProdutoRepository;
import com.igorfood.dtos.ProdutoDTO;
import com.igorfood.dtos.input.ProdutoInput;
import com.igorfood.exception.ProdutoNaoEncontradoException;
import com.igorfood.modelmapper.ProdutoAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProdutoService {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoAssembler produtoAssembler;

    public Set<ProdutoDTO> listarProduto(Restaurante restaurante, boolean incluirAtivos){
        if (incluirAtivos){
            return (Set<ProdutoDTO>) produtoAssembler.toCollectionDTO(produtoRepository.findAtivosByRestaurante(restaurante));
        }else {
            return (Set<ProdutoDTO>) produtoAssembler.toCollectionDTO(produtoRepository.findByRestaurante(restaurante));
        }
    }
    public ProdutoDTO buscar(Long restauranteId, Long produtoId){
        return produtoAssembler.toDTO(getProduto(restauranteId,produtoId));
    }

    public ProdutoDTO salvarProduto(Long restauranteId, ProdutoInput produtoInput) {
        Restaurante restaurante = restauranteService.getRestaurante(restauranteId);
        Produto produto = produtoAssembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);
        Produto produtoSalvo = produtoRepository.save(produto);
        return produtoAssembler.toDTO(produtoSalvo);
    }


    public Produto getProduto(Long restauranteId, Long produtoId){
        restauranteService.getRestaurante(restauranteId);
        return produtoRepository.findById(restauranteId,produtoId).orElseThrow(
                () -> new ProdutoNaoEncontradoException(produtoId)
        );
    }


}
