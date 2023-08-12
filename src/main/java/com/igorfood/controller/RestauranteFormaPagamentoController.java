package com.igorfood.controller;

import com.igorfood.domain.model.FormaPagamento;
import com.igorfood.domain.model.Restaurante;
import com.igorfood.dtos.FormaPagamentoDTO;
import com.igorfood.modelmapper.FormaPagamentoAssembler;
import com.igorfood.services.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("igorfood/restaurantes/{id_restaurante}/formas-pagamento")
public class RestauranteFormaPagamentoController {


    @Autowired
    private FormaPagamentoAssembler formaPagamentoAssembler;

    @Autowired
    private RestauranteService restauranteService;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> listarFormaPagamento(@PathVariable("id_restaurante") Long restauranteId){
        Restaurante restaurante = restauranteService.getRestaurante(restauranteId);
        Set<FormaPagamento> formasPagamento = restaurante.getFormaPagamentos();
        List<FormaPagamentoDTO> formasPagamentoDTO = (List<FormaPagamentoDTO>) formaPagamentoAssembler.toCollectionDTO(formasPagamento);
        return ResponseEntity.ok(formasPagamentoDTO);
    }

    @DeleteMapping("/{id_formaPagamento}")
    public ResponseEntity<String> removerFormaPagamento(
            @PathVariable("id_restaurante") Long restauranteId,
            @PathVariable("id_formaPagamento") Long formaPagamentoId){
        restauranteService.removerFormaPagamento(restauranteId,formaPagamentoId);
        return ResponseEntity.ok("forma pagamento removido com sucesso");
    }

    @PutMapping("/{id_formaPagamento}")
    public ResponseEntity<String> adicionarFormePagamento(
            @PathVariable("id_restaurante") Long restauranteId,
            @PathVariable("id_formaPagamento") Long formaPagamentoId){
        restauranteService.adicionarFormaPagamento(restauranteId,formaPagamentoId);
        return ResponseEntity.ok("forma pagamento associada com sucesso");
    }
}
