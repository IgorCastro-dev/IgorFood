package com.igorfood.dtos;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ItemPedidoDTO {
    private Long id;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private String observacao;
    private Long produtoId;
}
