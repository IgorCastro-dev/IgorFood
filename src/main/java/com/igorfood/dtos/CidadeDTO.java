package com.igorfood.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CidadeDTO {

    @ApiModelProperty(value = "Id de uma cidade",example = "1")
    private Long id;

    @ApiModelProperty(example = "Uberlândia")
    private String nome;
    private EstadoDTO estado;
}
