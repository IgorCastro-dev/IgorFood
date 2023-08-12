package com.igorfood.modelmapper;

import com.igorfood.domain.model.FormaPagamento;
import com.igorfood.dtos.FormaPagamentoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoDTO toDTO(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }

    public Collection<FormaPagamentoDTO> toCollectionDTO(Collection<FormaPagamento> formasPagamentos){
        return formasPagamentos.stream()
                .map(formaPagamento -> toDTO(formaPagamento))
                .collect(Collectors.toList());
    }
}
