package com.igorfood.modelmapper;

import com.igorfood.domain.model.Pedido;
import com.igorfood.dtos.PedidoDTO;
import com.igorfood.dtos.PedidoResumoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResumeAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoDTO toDTO(Pedido pedido){
        return modelMapper.map(pedido, PedidoResumoDTO.class);
    }

    public List<PedidoResumoDTO> collectionToDTO(Collection<Pedido> pedidos){
        return pedidos.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
