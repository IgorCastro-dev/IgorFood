package com.igorfood.controller;

import com.igorfood.dtos.PedidoDTO;
import com.igorfood.dtos.PedidoResumoDTO;
import com.igorfood.dtos.input.PedidoInput;
import com.igorfood.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("igorfood/pedidos")
public class PedidosController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoResumoDTO>> listarPedidos(){
        return ResponseEntity.ok(pedidoService.listar());
    }

    @GetMapping("/{pedido_codigo}")
    public ResponseEntity<PedidoDTO> buscarPedido(
            @PathVariable("pedido_codigo") String pedidoCodigo
    ){
        return ResponseEntity.ok(pedidoService.buscar(pedidoCodigo));
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> salvarPedido(
            @Valid @RequestBody PedidoInput pedidoInput
            ){
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.salvar(pedidoInput));
    }
}
