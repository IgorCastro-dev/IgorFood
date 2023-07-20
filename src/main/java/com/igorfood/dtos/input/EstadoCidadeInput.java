package com.igorfood.dtos.input;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstadoCidadeInput {
    @NotNull
    private Long id;
}
