package com.igorfood.dtos;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EstadoDTO {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "MG")
    private String nome;
}
