package com.igorfood.dtos.input;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstadoCidadeInput {
    @ApiModelProperty(example = "1")
    @NotNull
    private Long id;
}
