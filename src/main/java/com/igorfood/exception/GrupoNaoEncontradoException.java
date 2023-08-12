package com.igorfood.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public GrupoNaoEncontradoException(String message) {
        super(message);
    }

    public GrupoNaoEncontradoException(Long grupoId) {
        super(String.format("Grupo com o id = %d não encontrado",grupoId));
    }
}
