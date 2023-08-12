package com.igorfood.dtos.input;

import com.igorfood.domain.model.Produto;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemPedidoInput {

    @NotNull
    private Long produtoId;

    @Positive
    private Integer quantidade;

    private String observacao;
}
