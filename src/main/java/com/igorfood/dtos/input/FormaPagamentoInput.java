package com.igorfood.dtos.input;

import jakarta.validation.constraints.NotBlank;

public class FormaPagamentoInput {
    @NotBlank
    private String descricao;
}
