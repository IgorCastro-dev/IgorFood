package com.igorfood.modelmapper;

import com.igorfood.domain.model.Usuario;
import com.igorfood.dtos.UsuarioDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UsuarioAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioDTO toDTO(Usuario usuario){
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public Collection<UsuarioDTO> collectionToDTO(Collection<Usuario> usuarios){
        return usuarios.stream().map(usuario -> toDTO(usuario)).collect(Collectors.toSet());
    }
}
