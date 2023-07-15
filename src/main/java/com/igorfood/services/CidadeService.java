package com.igorfood.services;

import com.igorfood.domain.model.Cidade;
import com.igorfood.domain.model.Estado;
import com.igorfood.domain.repository.CidadeRepository;
import com.igorfood.domain.repository.EstadoRepository;
import com.igorfood.exception.*;
import lombok.RequiredArgsConstructor;
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

    private static final String MSG_CIDADE_EM_USO
            = "Cidade de código %d não pode ser removida, pois está em uso";

    public Cidade buscar(Long cidadeId){
        return cidadeRepository.findById(cidadeId).orElseThrow(
                () -> new CidadeNaoEncontradaException(cidadeId));
    }

    public Cidade salvar(Cidade cidade){
        try{
            var estado = estadoService.buscar(cidade.getEstado().getId());
            cidade.setEstado(estado);
            return cidadeRepository.save(cidade);
        }catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    public Cidade update(Long cidadeId,Cidade cidadeDto){
        var cidade = buscar(cidadeId);
        BeanUtils.copyProperties(cidadeDto,cidade,"id");
        return salvar(cidade);
    }

    public void excluir(Long cidadeId){
        try {
            buscar(cidadeId);
            cidadeRepository.deleteById(cidadeId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, cidadeId));
        }


    }
}
