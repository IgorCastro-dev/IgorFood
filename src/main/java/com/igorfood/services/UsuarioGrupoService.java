package com.igorfood.services;

import com.igorfood.domain.model.Grupo;
import com.igorfood.domain.model.Usuario;
import com.igorfood.dtos.GrupoDTO;
import com.igorfood.exception.NegocioException;
import com.igorfood.modelmapper.GrupoAssembler;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UsuarioGrupoService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoAssembler grupoAssembler;

    private final String GRUPO_NAO_PERTENCE = "O Grupo %d não pertence ao Usuário %d";

    public Set<GrupoDTO> listar(Long usuarioId) {
        Usuario usuario = usuarioService.getUsuario(usuarioId);
        return (Set<GrupoDTO>) grupoAssembler.collectionToDTO(usuario.getGrupos());
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = usuarioService.getUsuario(usuarioId);
        Grupo grupo = grupoService.getGrupo(grupoId);
        usuario.associarGrupo(grupo);
    }

    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = usuarioService.getUsuario(usuarioId);
        Grupo grupo = grupoService.getGrupo(grupoId);
        if(!usuario.getGrupos().contains(grupo)){
            throw new NegocioException(String.format(GRUPO_NAO_PERTENCE,grupoId,usuarioId));
        }
        usuario.desassociarGrupo(grupo);
    }
}
