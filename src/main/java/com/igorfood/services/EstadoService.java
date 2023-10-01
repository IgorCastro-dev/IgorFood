package com.igorfood.services;

import com.igorfood.domain.model.Estado;
import com.igorfood.domain.repository.EstadoRepository;
import com.igorfood.dtos.EstadoDTO;
import com.igorfood.dtos.input.EstadoInput;
import com.igorfood.exception.EntidadeEmUsoException;
import com.igorfood.exception.EstadoNaoEncontradoException;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadoService {

    @Autowired
    EstadoRepository estadoRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final String MSG_ESTADO_EM_USO
            = "Estado de código %d não pode ser removido, pois está em uso";

    @Transactional
    public EstadoDTO salvar(EstadoInput estadoInput) {
        Estado estadoAtual = modelMapper.map(estadoInput,Estado.class);
        return modelMapper.map(estadoRepository.save(estadoAtual),EstadoDTO.class);
    }

    @Transactional
    public EstadoDTO update(Long estadoId, EstadoInput estadoInput){
        Estado estado = getEstado(estadoId);
        modelMapper.map(estadoInput,estado);
        return modelMapper.map(estadoRepository.save(estado),EstadoDTO.class);
    }

    public List<EstadoDTO> findAll(){
        return estadoRepository.findAll().stream()
                .map(estado -> modelMapper.map(estado,EstadoDTO.class))
                .collect(Collectors.toList());
    }


    @Transactional
    public void excluir(Long estadoId) {
        try {
            buscar(estadoId);
            estadoRepository.deleteById(estadoId);
            estadoRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_ESTADO_EM_USO, estadoId));
        }
    }
    public EstadoDTO buscar(Long estadoId) {
        return modelMapper.map(getEstado(estadoId),EstadoDTO.class);
    }

    public Estado getEstado(Long estadoId) {
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
    }
}
