package com.igorfood.dtos.input;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RestauranteIdInput {
    @NotNull
    private Long id;
}
