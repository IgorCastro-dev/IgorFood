package com.igorfood.controller;

import com.igorfood.domain.model.Restaurante;
import com.igorfood.dtos.ProdutoDTO;
import com.igorfood.dtos.input.ProdutoInput;
import com.igorfood.modelmapper.ProdutoAssembler;
import com.igorfood.domain.services.ProdutoService;
import com.igorfood.domain.services.RestauranteService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("igorfood/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ProdutoAssembler produtoAssembler;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Set<ProdutoDTO>> listarProdutos(
            @PathVariable("restauranteId") Long restauranteId,
            @RequestParam(required = false) boolean incluirAtivos
    ){
        Restaurante restaurante = restauranteService.getRestaurante(restauranteId);
        Set<ProdutoDTO> produtos = produtoService.listarProduto(restaurante,incluirAtivos);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id_produto}")
    public ResponseEntity<ProdutoDTO> buscarProdutoPorId(
            @PathVariable("restauranteId") Long restauranteId,
            @PathVariable("id_produto") Long produtoId){
        ProdutoDTO produtoDTO = produtoService.buscar(restauranteId,produtoId);
        return ResponseEntity.ok(produtoDTO);
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> salvarProduto(
            @PathVariable("restauranteId") Long restauranteId,
            @Valid @RequestBody ProdutoInput produtoInput
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(produtoService.salvarProduto(restauranteId,produtoInput));
    }
}
