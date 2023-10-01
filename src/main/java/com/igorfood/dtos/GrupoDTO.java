package com.igorfood.dtos;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GrupoDTO{

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Gerente")
    private String nome;
}
