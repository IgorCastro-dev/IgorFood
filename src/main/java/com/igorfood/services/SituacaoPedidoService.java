package com.igorfood.services;

import com.igorfood.domain.model.Pedido;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SituacaoPedidoService {

    @Autowired
    private PedidoService pedidoService;

    @Transactional
    public void confirmar(String pedidoCodigo) {
        Pedido pedido = pedidoService.getPedido(pedidoCodigo);
        pedido.confirmar();
    }
    @Transactional
    public void entregue(String pedidoCodigo) {
        Pedido pedido = pedidoService.getPedido(pedidoCodigo);
        pedido.entregue();
    }

    @Transactional
    public void cancelar(String pedidoCodigo) {
        Pedido pedido = pedidoService.getPedido(pedidoCodigo);
        pedido.cancelar();
    }
}
