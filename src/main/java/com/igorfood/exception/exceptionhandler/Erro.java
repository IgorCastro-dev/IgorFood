package com.igorfood.exception.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Erro{
    @ApiModelProperty(example = "400")
    private Integer status;
    @ApiModelProperty(example = "2023-09-20T12:36:03.8674079")
    private OffsetDateTime timestamp;
    @ApiModelProperty(example = "dados invalidos")
    private String title;
    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto")
    private String detail;
    @ApiModelProperty(example = "Objeto com os erros")
    private List<Field> fields;

    @ApiModel("ObjetoErro")
    @Getter
    @Builder
    public static class Field{
        @ApiModelProperty(example = "nome")
        private String name;
        @ApiModelProperty(example = "nome é obrigatório")
        private String userMessage;
    }
}
