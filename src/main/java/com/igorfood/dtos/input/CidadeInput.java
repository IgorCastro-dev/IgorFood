package com.igorfood.dtos.input;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CidadeInput {

    @ApiModelProperty(example = "Uberlândia")
    @NotBlank
    private String nome;

    @Valid
    @NotNull
    private EstadoCidadeInput estado;
}
