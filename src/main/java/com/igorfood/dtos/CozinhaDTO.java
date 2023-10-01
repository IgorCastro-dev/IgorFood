package com.igorfood.dtos;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaDTO {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "cozinha do seu zé")
    private String nome;
}

