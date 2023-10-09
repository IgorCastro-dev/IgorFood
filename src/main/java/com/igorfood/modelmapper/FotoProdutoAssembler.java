package com.igorfood.modelmapper;

import com.igorfood.domain.model.FotoProduto;
import com.igorfood.dtos.FotoProdutoDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoDto modelToDto(FotoProduto fotoProduto){
        return modelMapper.map(fotoProduto, FotoProdutoDto.class);
    }
}
