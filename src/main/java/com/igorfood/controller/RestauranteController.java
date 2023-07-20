package com.igorfood.controller;

import com.igorfood.domain.model.Restaurante;
import com.igorfood.dtos.input.RestauranteInput;
import com.igorfood.exception.EntidadeNaoEncontradaException;
import com.igorfood.services.RestauranteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("igorfood/restaurantes")
public class RestauranteController {


    @Autowired
    private RestauranteService restauranteService;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(restauranteService.listar());
    }

    @GetMapping("/{id_restaurante}")
    public ResponseEntity<?> buscar(@PathVariable("id_restaurante") Long id) {
        return ResponseEntity.ok(restauranteService.buscar(id));
    }

    @PostMapping()
    public ResponseEntity<?> salvar(@RequestBody @Valid RestauranteInput restauranteInput){
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteService.save(restauranteInput));
    }

    @PutMapping("/{id_restaurante}")
    public ResponseEntity<?> update(@PathVariable("id_restaurante") Long id,@Valid @RequestBody RestauranteInput restauranteInput) {
        return ResponseEntity.ok(restauranteService.update(id, restauranteInput));
    }

    @PatchMapping("/{id_restaurante}")
    public ResponseEntity<?> updateField(
            @PathVariable("id_restaurante") Long restauranteId,
            @RequestBody Map<String,Object> campos) {
        return ResponseEntity.ok(restauranteService.updateField(restauranteId, campos));
    }

    @DeleteMapping("/{id_restaurante}")
    public ResponseEntity<?> delete(@PathVariable("id_restaurante") Long id){
        restauranteService.remover(id);
        return ResponseEntity.ok("restaurante removido com o id = "+id);
    }
}
