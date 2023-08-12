package com.igorfood.modelmapper;

import com.igorfood.domain.model.Permissao;
import com.igorfood.dtos.PermissaoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class PermissaoAssembler{

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoDTO toDTO(Permissao permissao){
        return modelMapper.map(permissao,PermissaoDTO.class);
    }

    public Collection<PermissaoDTO> collectionToDTO(Collection<Permissao> permissoes){
        return permissoes.stream().map(permissao -> toDTO(permissao))
                .collect(Collectors.toSet());
    }
}
