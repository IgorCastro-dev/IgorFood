package com.igorfood.dtos.input;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class GrupoInput {

    @ApiModelProperty(example = "Gerente")
    @NotBlank
    private String nome;

}
