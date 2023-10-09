package com.igorfood.domain.services;

import com.igorfood.domain.model.FormaPagamento;
import com.igorfood.domain.repository.FormaPagamentoRepository;
import com.igorfood.dtos.FormaPagamentoDTO;
import com.igorfood.dtos.input.FormaPagamentoInput;
import com.igorfood.exception.FormaPagamentoNaoEncontradoException;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormaPagamentoService {
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<FormaPagamentoDTO> listar(){
        return formaPagamentoRepository.findAll().stream()
                .map(formaPagamento -> modelMapper.map(formaPagamento,FormaPagamentoDTO.class))
                .collect(Collectors.toList());
    }

    public FormaPagamentoDTO buscar(Long formaPagamentoId){
        return modelMapper.map(getFormaPagamento(formaPagamentoId),FormaPagamentoDTO.class);
    }

    @Transactional
    public FormaPagamentoDTO salvar(FormaPagamentoInput formaPagamentoInput){
        FormaPagamento formaPagamento = modelMapper.map(formaPagamentoInput,FormaPagamento.class);
        return modelMapper.map(formaPagamentoRepository.save(formaPagamento),FormaPagamentoDTO.class);
    }

    @Transactional
    public FormaPagamentoDTO atualizar(Long formaPagamentoId,FormaPagamentoInput formaPagamentoInput){
        FormaPagamento formaPagamentoAtual = getFormaPagamento(formaPagamentoId);
        modelMapper.map(formaPagamentoInput,formaPagamentoAtual);
        return modelMapper.map(formaPagamentoAtual,FormaPagamentoDTO.class);
    }

    @Transactional
    public void deletar(Long formaPagamentoId){
        FormaPagamento formaPagamento = getFormaPagamento(formaPagamentoId);
        formaPagamentoRepository.delete(formaPagamento);
    }

    public FormaPagamento getFormaPagamento(Long formaPagamentoId){
        return formaPagamentoRepository.findById(formaPagamentoId).orElseThrow(()->
                new FormaPagamentoNaoEncontradoException(formaPagamentoId));
    }

}





























