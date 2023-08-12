package com.igorfood.dtos;

import com.igorfood.domain.model.StatusPedido;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class PedidoResumoDTO {
    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private StatusPedido status;
    private OffsetDateTime dataCriacao;
    private RestauranteIdNomeDTO restaurante;
    private UsuarioDTO cliente;
}
