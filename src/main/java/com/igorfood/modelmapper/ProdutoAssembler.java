package com.igorfood.modelmapper;

import com.igorfood.domain.model.Produto;
import com.igorfood.dtos.ProdutoDTO;
import com.igorfood.dtos.input.ProdutoInput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class ProdutoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoDTO toDTO(Produto produto){
        return modelMapper.map(produto,ProdutoDTO.class);
    }

    public Collection<ProdutoDTO> toCollectionDTO(Collection<Produto> produtos){
        return produtos.stream()
                .map(produto -> toDTO(produto))
                .collect(Collectors.toSet());
    }

    public Produto toModel(ProdutoDTO produtoDTO){
        return modelMapper.map(produtoDTO,Produto.class);
    }

    public Produto toDomainObject(ProdutoInput produtoInput) {
        return modelMapper.map(produtoInput, Produto.class);
    }
}



















