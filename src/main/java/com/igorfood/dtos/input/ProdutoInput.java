package com.igorfood.dtos.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoInput {
    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @PositiveOrZero
    @NotNull
    private BigDecimal preco;

    @NotNull
    private Boolean ativo;
}
