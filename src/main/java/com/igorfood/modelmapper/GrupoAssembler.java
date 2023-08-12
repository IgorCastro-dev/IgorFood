package com.igorfood.modelmapper;

import com.igorfood.domain.model.Grupo;
import com.igorfood.dtos.GrupoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class GrupoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoDTO toDTO(Grupo grupo){
        return modelMapper.map(grupo,GrupoDTO.class);
    }

    public Collection<GrupoDTO> collectionToDTO(Collection<Grupo> grupos){
        return grupos.stream().map(grupo -> toDTO(grupo)).collect(Collectors.toSet());
    }
}
