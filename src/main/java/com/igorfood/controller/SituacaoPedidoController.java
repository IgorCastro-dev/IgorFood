package com.igorfood.controller;

import com.igorfood.domain.services.SituacaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("igorfood/pedidos/{pedido_codigo}")
public class SituacaoPedidoController {

    @Autowired
    private SituacaoPedidoService situacaoPedidoService;

    @PutMapping("/confirmar")
    public ResponseEntity<String> confirmarPedido(@PathVariable("pedido_codigo") String codigoPedido){
        situacaoPedidoService.confirmar(codigoPedido);
        return ResponseEntity.ok("Pedido confirmado com sucesso");
    }

    @PutMapping("/entregue")
    public ResponseEntity<String> pedidoEntregue(@PathVariable("pedido_codigo") String codigoPedido){
        situacaoPedidoService.entregue(codigoPedido);
        return ResponseEntity.ok("Pedido entregue com sucesso");
    }

    @PutMapping("/cancelar")
    public ResponseEntity<String> cancelarPedido(@PathVariable("pedido_codigo") String codigoPedido){
        situacaoPedidoService.cancelar(codigoPedido);
        return ResponseEntity.ok("Pedido cancelado com sucesso");
    }
}
