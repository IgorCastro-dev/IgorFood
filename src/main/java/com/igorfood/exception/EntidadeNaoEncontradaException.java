package com.igorfood.exception;

public abstract class EntidadeNaoEncontradaException extends NegocioException{
    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }
}
