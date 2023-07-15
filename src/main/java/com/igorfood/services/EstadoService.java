package com.igorfood.services;

import com.igorfood.domain.model.Estado;
import com.igorfood.domain.repository.EstadoRepository;
import com.igorfood.exception.EntidadeEmUsoException;
import com.igorfood.exception.EstadoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    @Autowired
    EstadoRepository estadoRepository;

    private static final String MSG_ESTADO_EM_USO
            = "Estado de código %d não pode ser removido, pois está em uso";

    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public List<Estado> findAll(){
        return estadoRepository.findAll();
    }


    public void excluir(Long estadoId) {
        try {
            buscar(estadoId);
            estadoRepository.deleteById(estadoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_ESTADO_EM_USO, estadoId));
        }
    }
    public Estado buscar(Long estadoId) {
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
    }
}
