package com.igorfood.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public PedidoNaoEncontradoException(String pedidoCodigo) {
        super(String.format("Pedido com o codigo = %s não encontrado",pedidoCodigo));
    }
}
