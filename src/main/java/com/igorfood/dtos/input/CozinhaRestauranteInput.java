package com.igorfood.dtos.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaRestauranteInput {

    @NotNull
    private Long id;
}
