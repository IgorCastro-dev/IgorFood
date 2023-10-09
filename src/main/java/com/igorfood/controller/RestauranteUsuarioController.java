package com.igorfood.controller;

import com.igorfood.dtos.UsuarioDTO;
import com.igorfood.domain.services.RestauranteUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("igorfood/restaurantes/{restaurante_id}/usuarios")
public class RestauranteUsuarioController {

    @Autowired
    private RestauranteUsuarioService restauranteUsuarioService;

    @GetMapping
    private ResponseEntity<Set<UsuarioDTO>> listarUsuarios(
            @PathVariable("restaurante_id") Long restauranteId
    ){
        return ResponseEntity.ok(restauranteUsuarioService.listar(restauranteId));
    }

    @PutMapping("/{usuario_id}")
    private ResponseEntity<String> associarUsuario(
            @PathVariable("restaurante_id") Long restauranteId,
            @PathVariable("usuario_id") Long usuarioId
    ){
        restauranteUsuarioService.associarUsuario(restauranteId,usuarioId);
        return ResponseEntity.ok(String.format("Usuario %d foi associado ao Restaurante %d",usuarioId,restauranteId));
    }

    @DeleteMapping("/{usuario_id}")
    private ResponseEntity<String> desassociarUsuario(
            @PathVariable("restaurante_id") Long restauranteId,
            @PathVariable("usuario_id") Long usuarioId
    ){
        restauranteUsuarioService.desassociarUsuario(restauranteId,usuarioId);
        return ResponseEntity.ok(String.format("Usuario %d foi desassociado ao Restaurante %d",usuarioId,restauranteId));
    }
}
