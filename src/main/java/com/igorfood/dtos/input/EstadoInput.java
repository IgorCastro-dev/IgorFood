package com.igorfood.dtos.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EstadoInput {

    @ApiModelProperty(example = "MG")
    @NotBlank
    private String nome;
}
