package com.igorfood.services;

import com.igorfood.domain.model.ItemPedido;
import com.igorfood.domain.model.Pedido;
import com.igorfood.domain.model.Produto;
import com.igorfood.dtos.input.ItemPedidoInput;
import com.igorfood.exception.NegocioException;
import com.igorfood.exception.ProdutoNaoEncontradoException;
import com.igorfood.modelmapper.ItemPedidoAssembler;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoService {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoAssembler itemPedidoAssembler;


    @Transactional
    public void salvarItem(Pedido pedido, ItemPedido itemPedido) {
        try{
            Produto produto = produtoService.getProduto(pedido.getRestaurante().getId(),itemPedido.getProduto().getId());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            itemPedido.setPrecoUnitario(produto.getPreco());
            itemPedido.calculaPrecoTotal();
        }catch (ProdutoNaoEncontradoException e){
            throw new NegocioException(String.format("Não existe o produto com o id = %d cadastrado no restaurante %s",itemPedido.getProduto().getId(),pedido.getRestaurante().getNome()));
        }
    }
}
