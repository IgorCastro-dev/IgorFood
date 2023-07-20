package com.igorfood.dtos.input;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CidadeInput {

    @NotBlank
    private String nome;

    @NotNull
    private EstadoCidadeInput estado;
}
