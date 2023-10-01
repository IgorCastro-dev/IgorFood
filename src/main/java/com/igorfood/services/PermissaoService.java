package com.igorfood.services;

import com.igorfood.domain.model.Grupo;
import com.igorfood.domain.model.Permissao;
import com.igorfood.domain.repository.PermissaoRepository;
import com.igorfood.dtos.PermissaoDTO;
import com.igorfood.exception.NegocioException;
import com.igorfood.exception.PermissaoNaoEncontradaException;
import com.igorfood.modelmapper.PermissaoAssembler;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PermissaoService {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private PermissaoAssembler permissaoAssembler;

    private final String NAO_CONTEM_PERMISSAO = "A permissão com o id = %d não pertence ao grupo com o id = %d";

    public Set<PermissaoDTO> listar(Long grupoId){
        Grupo grupo = grupoService.getGrupo(grupoId);
        Set<Permissao> permissoes = grupo.getPermissoes();
        Set<PermissaoDTO> permissoesDTO = (Set<PermissaoDTO>) permissaoAssembler.collectionToDTO(permissoes);
        return permissoesDTO;
    };

    @Transactional
    public void desassociarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = grupoService.getGrupo(grupoId);
        Permissao permissao = getPermissao(permissaoId);
        if(!grupo.getPermissoes().contains(permissao)){
            throw new NegocioException(String.format(NAO_CONTEM_PERMISSAO,permissaoId,grupoId));
        }
        grupo.desassociarPermissao(permissao);
    }

    @Transactional
    public void associarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = grupoService.getGrupo(grupoId);
        Permissao permissao = getPermissao(permissaoId);
        grupo.associarPermissao(permissao);
    }

    private Permissao getPermissao(Long permissaoId) {
        return permissaoRepository.findById(permissaoId).orElseThrow(
                () -> new PermissaoNaoEncontradaException(permissaoId));
    }


}
