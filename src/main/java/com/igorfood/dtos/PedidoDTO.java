package com.igorfood.dtos;

import com.igorfood.domain.model.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Data
public class PedidoDTO {
    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private EnderecoDTO endereco;
    private StatusPedido status;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;
    private FormaPagamentoDTO formaPagamento;
    private RestauranteIdNomeDTO restaurante;
    private UsuarioDTO cliente;
    private List<ItemPedidoDTO> itens;
}
