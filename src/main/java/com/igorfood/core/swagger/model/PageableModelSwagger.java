package com.igorfood.core.swagger.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("Pageable")
public class PageableModelSwagger {
    @ApiModelProperty(value = "Número da página (começa em 0)",example = "2")
    private int size;
    @ApiModelProperty(value = "Quantidade de elementos por página",example = "10")
    private int page;
    @ApiModelProperty(value = "Nome da propriedade para ordenação",example = "nome,asc")
    private List<String> sort;
}
