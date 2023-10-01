package com.igorfood.dtos.input;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;


@Data
public class ItemPedidoInput {

    @NotNull
    private Long produtoId;

    @Positive
    private Integer quantidade;

    private String observacao;
}
