package com.igorfood.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException{
    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }

    public UsuarioNaoEncontradoException(Long id) {
        super(String.format("Não existe um cadastro de Usuario com código %d",id));
    }
}
