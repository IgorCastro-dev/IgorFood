package com.igorfood.modelmapper;

import com.igorfood.domain.model.Cozinha;
import com.igorfood.dtos.CozinhaDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaDTO entityToDto(Cozinha cozinha){
        return modelMapper.map(cozinha,CozinhaDTO.class);
    }

    public List<CozinhaDTO> collectionToDTO(Collection<Cozinha> cozinhas){
        return cozinhas.stream().map(cozinha -> entityToDto(cozinha)).collect(Collectors.toList());
    }

}
