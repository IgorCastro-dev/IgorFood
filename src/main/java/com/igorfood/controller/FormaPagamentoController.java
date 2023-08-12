package com.igorfood.controller;


import com.igorfood.dtos.FormaPagamentoDTO;
import com.igorfood.dtos.input.FormaPagamentoInput;
import com.igorfood.services.FormaPagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("igorfood/forma-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> listarFormaPagamento(){
        return ResponseEntity.ok(formaPagamentoService.listar());
    }

    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoDTO> buscarFormaPagamento(@PathVariable Long formaPagamentoId){
        return ResponseEntity.ok(formaPagamentoService.buscar(formaPagamentoId));
    }

    @PostMapping
    public ResponseEntity<FormaPagamentoDTO> salvarFormaPagamento(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput){
        return ResponseEntity.status(HttpStatus.CREATED).body(formaPagamentoService.salvar(formaPagamentoInput));
    }

    @PutMapping
    public ResponseEntity<FormaPagamentoDTO> atualizarFormaPagamento(
            @PathVariable Long formaPagamentoId,
            @RequestBody @Valid FormaPagamentoInput formaPagamentoInput
    ){
        return ResponseEntity.ok(formaPagamentoService.atualizar(formaPagamentoId,formaPagamentoInput));
    }

    @DeleteMapping("/{formaPagamentoId}")
    public ResponseEntity<String> deletarFormaPagamento(@PathVariable Long formaPagamentoId){
        formaPagamentoService.deletar(formaPagamentoId);
        return ResponseEntity.ok(String.format("Forma de pagamento com o id = %d foi excluido"));
    }
}
