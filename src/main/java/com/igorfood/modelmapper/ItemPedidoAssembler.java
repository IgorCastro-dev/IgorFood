package com.igorfood.modelmapper;

import com.igorfood.domain.model.ItemPedido;
import com.igorfood.domain.model.Pedido;
import com.igorfood.dtos.ItemPedidoDTO;
import com.igorfood.dtos.PedidoDTO;
import com.igorfood.dtos.input.ItemPedidoInput;
import com.igorfood.dtos.input.PedidoInput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class ItemPedidoAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public ItemPedidoDTO toDTO(ItemPedido itemPedido) {
        return modelMapper.map(itemPedido, ItemPedidoDTO.class);
    }

    public Collection<ItemPedidoDTO> collectionToDTO(Collection<ItemPedido> pedidos) {
        return pedidos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ItemPedido toEntity(ItemPedidoInput itemPedidoInput) {
        return modelMapper.map(itemPedidoInput, ItemPedido.class);
    }

    public Collection<ItemPedido> collectionToEntity(Collection<ItemPedidoInput> pedidos) {
        return pedidos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}