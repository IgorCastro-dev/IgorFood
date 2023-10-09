package com.igorfood.domain.services;

import com.igorfood.domain.model.Restaurante;
import com.igorfood.domain.model.Usuario;
import com.igorfood.dtos.UsuarioDTO;
import com.igorfood.exception.NegocioException;
import com.igorfood.modelmapper.UsuarioAssembler;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RestauranteUsuarioService {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioAssembler usuarioAssembler;

    private final String USUARIO_NAO_PERTENCE = "O Usuario %d não pertence ao Restaurante %d";

    public Set<UsuarioDTO> listar(Long restauranteId) {
        Restaurante restaurante = restauranteService.getRestaurante(restauranteId);
        return (Set<UsuarioDTO>) usuarioAssembler.collectionToDTO(restaurante.getUsuarios());
    }

    @Transactional
    public void associarUsuario(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = restauranteService.getRestaurante(restauranteId);
        Usuario usuario = usuarioService.getUsuario(usuarioId);
        restaurante.associarUsuario(usuario);
    }

    @Transactional
    public void desassociarUsuario(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = restauranteService.getRestaurante(restauranteId);
        Usuario usuario = usuarioService.getUsuario(usuarioId);
        if (!restaurante.getUsuarios().contains(usuario)){
            throw new NegocioException(String.format(USUARIO_NAO_PERTENCE,usuarioId,restauranteId));
        }
        restaurante.desassociarUsuario(usuario);
    }
}
