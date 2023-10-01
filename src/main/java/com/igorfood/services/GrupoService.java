package com.igorfood.services;

import com.igorfood.domain.model.Grupo;
import com.igorfood.domain.repository.GrupoRepository;
import com.igorfood.dtos.GrupoDTO;
import com.igorfood.dtos.input.GrupoInput;
import com.igorfood.exception.EntidadeEmUsoException;
import com.igorfood.exception.GrupoNaoEncontradoException;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final String MSG_GRUPO_EM_USO
            = "Grupo de código %d não pode ser removido, pois está em uso";

    public List<GrupoDTO> listar(){
        return grupoRepository.findAll().stream()
                .map(grupo -> modelMapper.map(grupo,GrupoDTO.class))
                .collect(Collectors.toList());
    }

    public GrupoDTO buscar(Long grupoId){
        return modelMapper.map(getGrupo(grupoId),GrupoDTO.class);
    }

    @Transactional
    public GrupoDTO salvar(GrupoInput grupoInput){
        Grupo grupo = modelMapper.map(grupoInput,Grupo.class);
        return modelMapper.map(grupoRepository.save(grupo),GrupoDTO.class);
    }

    @Transactional
    public GrupoDTO atualizar(Long grupoId,GrupoInput grupoInput){
        Grupo grupoAtual = getGrupo(grupoId);
        modelMapper.map(grupoInput,grupoAtual);
        return modelMapper.map(grupoRepository.save(grupoAtual),GrupoDTO.class);
    }

    @Transactional
    public void deletar(Long grupoId){
        try{
            getGrupo(grupoId);
            grupoRepository.deleteById(grupoId);
            grupoRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_GRUPO_EM_USO, grupoId));
        }
    }

    public Grupo getGrupo(Long grupoId){
        return grupoRepository.findById(grupoId).orElseThrow(
                () -> new GrupoNaoEncontradoException(grupoId)
        );
    }
}
