package com.igorfood.modelmapper;

import com.igorfood.domain.model.Pedido;
import com.igorfood.dtos.PedidoDTO;
import com.igorfood.dtos.input.PedidoInput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PedidoAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public PedidoDTO toDTO(Pedido pedido){
        return modelMapper.map(pedido,PedidoDTO.class);
    }

    public Collection<PedidoDTO> collectionToDTO(Collection<Pedido> pedidos){
        return pedidos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Pedido toEntity(PedidoInput pedidoInput){
        return modelMapper.map(pedidoInput,Pedido.class);
    }
}
