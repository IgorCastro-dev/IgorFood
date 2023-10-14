package com.igorfood.domain.event;

import com.igorfood.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PedidoConfirmadoEvento {
    private Pedido pedido;
}
