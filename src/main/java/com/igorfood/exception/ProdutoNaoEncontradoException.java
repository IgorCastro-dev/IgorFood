package com.igorfood.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }

    public ProdutoNaoEncontradoException(Long id){
        this(String.format("Não existe um cadastro de produto com código %d",id));
    }
}
