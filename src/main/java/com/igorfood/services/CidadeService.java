package com.igorfood.services;

import com.igorfood.domain.model.Cidade;
import com.igorfood.domain.model.Estado;
import com.igorfood.domain.model.Restaurante;
import com.igorfood.domain.repository.CidadeRepository;
import com.igorfood.domain.repository.EstadoRepository;
import com.igorfood.dtos.CidadeDTO;
import com.igorfood.dtos.input.CidadeInput;
import com.igorfood.exception.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {

    @Autowired
    EstadoService estadoService;

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final String MSG_CIDADE_EM_USO
            = "Cidade de código %d não pode ser removida, pois está em uso";

    public CidadeDTO buscar(Long cidadeId){
        return modelMapper.map(getCidade(cidadeId), CidadeDTO.class);
    }

    @Transactional
    public CidadeDTO salvar(CidadeInput cidadeInput){
        try{
            Cidade cidade = modelMapper.map(cidadeInput, Cidade.class);
            return modelMapper.map(saveCidade(cidade),CidadeDTO.class);
        }catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @Transactional
    public CidadeDTO update(Long cidadeId,CidadeInput cidadeInput){
        Cidade cidadeatual = getCidade(cidadeId);
        cidadeatual.setEstado(new Estado());
        modelMapper.map(cidadeInput,cidadeatual);
        try {
            return modelMapper.map(saveCidade(cidadeatual),CidadeDTO.class);
        }catch (EntidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @Transactional
    public void excluir(Long cidadeId){
        try {
            buscar(cidadeId);
            cidadeRepository.deleteById(cidadeId);
            cidadeRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, cidadeId));
        }
    }

    public Cidade getCidade(Long cidadeId) {
        return cidadeRepository.findById(cidadeId).orElseThrow(
                () -> new CidadeNaoEncontradaException(cidadeId));
    }

    private Cidade  saveCidade(Cidade cidade) {
        Estado estado = estadoService.getEstado(cidade.getEstado().getId());
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }
}
