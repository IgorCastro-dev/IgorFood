package com.igorfood.dtos.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SenhaInput {
    @NotBlank
    private String senhaAtual;

    @NotBlank
    private String novaSenha;
}
