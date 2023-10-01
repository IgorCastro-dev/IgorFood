package com.igorfood.dtos.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
