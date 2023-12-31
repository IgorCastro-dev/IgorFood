package com.igorfood.controller;

import com.igorfood.dtos.input.RestauranteInput;
import com.igorfood.exception.exceptionhandler.Erro;
import com.igorfood.domain.services.RestauranteService;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "Restaurantes")
@RestController
@RequestMapping("igorfood/restaurantes")
public class RestauranteController {


    @Autowired
    private RestauranteService restauranteService;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(restauranteService.listar());
    }

    @ApiOperation("Busca o restaurante por id")
    @ApiResponses({
            @ApiResponse(code = 404,message = "Restaurante não encontrado",response = Erro.class),
            @ApiResponse(code = 400,message = "Id do restaurante inválido",response = Erro.class)
    })
    @GetMapping("/{id_restaurante}")
    public ResponseEntity<?> buscar(@PathVariable("id_restaurante") Long id) {
        return ResponseEntity.ok(restauranteService.buscar(id));
    }

    @ApiOperation("Cria um restaurante")
    @ApiResponses({
            @ApiResponse(code = 201,message = "Restaurante criado")
    })
    @PostMapping()
    public ResponseEntity<?> salvar(@RequestBody @Valid RestauranteInput restauranteInput){
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteService.save(restauranteInput));
    }

    @ApiOperation("Atualiza um Restaurante")
    @ApiResponses({
            @ApiResponse(code = 200,message = "Restaurante atualizado"),
            @ApiResponse(code = 404,message = "Restaurante não encontrado",response = Erro.class)
    })
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

    @PutMapping("/{id_restaurante}/ativar")
    public ResponseEntity<?> ativarRestaurante(@PathVariable("id_restaurante") Long id){
        restauranteService.ativar(id);
        return ResponseEntity.ok("restaurante ativado com o id = "+id);
    }

    @DeleteMapping("/{id_restaurante}/desativar")
    public ResponseEntity<?> desativarRestaurante(@PathVariable("id_restaurante") Long id){
        restauranteService.desativar(id);
        return ResponseEntity.ok("restaurante desativado com o id = "+id);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarRestaurantes(@RequestBody List<Long> restaurantesIds){
        restauranteService.ativarRestaurantes(restaurantesIds);
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativarRestaurantes(@RequestBody List<Long> restaurantesIds){
        restauranteService.desativarRestaurantes(restaurantesIds);
    }

    @PutMapping("/{id_restaurante}/abertura")
    public ResponseEntity<String> abertura(@PathVariable("id_restaurante") Long restauranteId){
        restauranteService.abrir(restauranteId);
        return ResponseEntity.ok("restaurante aberto com o id = " + restauranteId);
    }

    @PutMapping("/{id_restaurante}/fechamento")
    public ResponseEntity<String> fechamento(@PathVariable("id_restaurante") Long restauranteId){
        restauranteService.fechar(restauranteId);
        return ResponseEntity.ok("restaurante fechado com o id = " + restauranteId);
    }
}
