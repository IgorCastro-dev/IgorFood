package com.igorfood.dtos.input;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FormaPagamentoInput {
    @NotBlank
    private String descricao;
}
