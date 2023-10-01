package com.igorfood.dtos.input;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EstadoInput {
    @NotBlank
    private String nome;
}
