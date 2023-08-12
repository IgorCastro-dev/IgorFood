package com.igorfood.dtos.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioInput {
    @NotBlank
    private String nome;

    @Email
    private String email;

    @NotBlank
    private String senha;
}
