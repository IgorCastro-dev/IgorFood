package com.igorfood.domain.model;

import javax.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private String observacao;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Pedido pedido;

    public void calculaPrecoTotal() {

        setPrecoTotal(getPrecoUnitario().multiply(BigDecimal.valueOf(getQuantidade())));
    }
}
