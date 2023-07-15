package com.igorfood.services;

import com.igorfood.domain.model.Cozinha;
import com.igorfood.domain.repository.CozinhaRepository;
import com.igorfood.exception.CozinhaNaoEncontradaException;
import com.igorfood.exception.EntidadeEmUsoException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public List<Cozinha> listar(){
        return cozinhaRepository.findAll();
    }

    private static final String MSG_COZINHA_EM_USO  =
            "Cozinha de código %d não pode ser removida, pois está em uso";


    public Cozinha buscar(Long id){
        return cozinhaRepository.findById(id)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }

    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    public Cozinha update(Long cozinhaId,Cozinha cozinhaDto){
        var cozinha = buscar(cozinhaId);
        BeanUtils.copyProperties(cozinhaDto,cozinha,"id");
        return salvar(cozinha);
    }

    public void excluir(Long cozinhaId) {
        try {
            buscar(cozinhaId);
            cozinhaRepository.deleteById(cozinhaId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_COZINHA_EM_USO, cozinhaId));
        }
    }
}
