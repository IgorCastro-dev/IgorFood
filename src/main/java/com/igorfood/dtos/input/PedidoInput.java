package com.igorfood.dtos.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PedidoInput {

    @Valid
    @NotNull
    private RestauranteIdInput restaurante;

    @Valid
    @NotNull
    private FormaPagamentoIdInput formaPagamento;

    @Valid
    @NotNull
    private EnderecoInput endereco;

    @Valid
    @NotNull
    private List<ItemPedidoInput> itens;
}
