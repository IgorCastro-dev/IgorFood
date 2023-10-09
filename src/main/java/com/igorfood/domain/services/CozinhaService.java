package com.igorfood.domain.services;

import com.igorfood.domain.model.Cozinha;
import com.igorfood.domain.repository.CozinhaRepository;
import com.igorfood.dtos.CozinhaDTO;
import com.igorfood.dtos.input.CozinhaInput;
import com.igorfood.exception.CozinhaNaoEncontradaException;
import com.igorfood.exception.EntidadeEmUsoException;
import com.igorfood.modelmapper.CozinhaAssembler;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CozinhaAssembler cozinhaAssembler;

    public Page<CozinhaDTO> listar(Pageable pageable){
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
        List<CozinhaDTO> cozinhasDto = cozinhaAssembler.collectionToDTO(cozinhasPage.getContent());
        Page<CozinhaDTO> cozinhasDTOPage = new PageImpl<>(cozinhasDto,pageable,cozinhasPage.getTotalElements());
        return cozinhasDTOPage;
    }

    private static final String MSG_COZINHA_EM_USO  =
            "Cozinha de código %d não pode ser removida, pois está em uso";


    public CozinhaDTO buscar(Long id){
        return modelMapper.map(getCozinha(id),CozinhaDTO.class);
    }

    @Transactional
    public CozinhaDTO salvar(CozinhaInput cozinhaInput) {
        Cozinha cozinhaAtual = modelMapper.map(cozinhaInput,Cozinha.class);
        return modelMapper.map(cozinhaRepository.save(cozinhaAtual),CozinhaDTO.class);
    }

    @Transactional
    public CozinhaDTO update(Long cozinhaId,CozinhaInput cozinhaInput){
        Cozinha cozinha = getCozinha(cozinhaId);
        modelMapper.map(cozinhaInput,cozinha);
        return modelMapper.map(cozinhaRepository.save(cozinha),CozinhaDTO.class);
    }

    @Transactional
    public void excluir(Long cozinhaId) {
        try {
            buscar(cozinhaId);
            cozinhaRepository.deleteById(cozinhaId);
            cozinhaRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_COZINHA_EM_USO, cozinhaId));
        }
    }


    public Cozinha getCozinha(Long id) {
        return cozinhaRepository.findById(id)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }

}
