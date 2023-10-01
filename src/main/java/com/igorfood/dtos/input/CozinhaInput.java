package com.igorfood.dtos.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CozinhaInput {

    @ApiModelProperty(example = "cozinha do seu zé")
    @NotBlank
    private String nome;
}
