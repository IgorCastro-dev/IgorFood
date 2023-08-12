package com.igorfood.exception;

public class FormaPagamentoNaoEncontradoException extends EntidadeNaoEncontradaException{

    public FormaPagamentoNaoEncontradoException(String message) {
        super(message);
    }

    public FormaPagamentoNaoEncontradoException(Long id) {
        super(String.format("Forma de pagamento com o id = %d não encontrado",id));
    }
}
