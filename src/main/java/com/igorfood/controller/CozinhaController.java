package com.igorfood.controller;

import com.igorfood.dtos.CozinhaDTO;
import com.igorfood.dtos.input.CozinhaInput;
import com.igorfood.exception.exceptionhandler.Erro;
import com.igorfood.domain.services.CozinhaService;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Cozinhas")
@RestController
@RequestMapping("igorfood/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaService cozinhaService;


    @ApiOperation("Busca cozinha por id")
    @ApiResponses({
            @ApiResponse(code = 404,message = "Cozinha não encontrada",response = Erro.class),
            @ApiResponse(code = 400,message = "Id da cozinha inválido",response = Erro.class)})
    @GetMapping("/{id}")
    public ResponseEntity<CozinhaDTO> buscarCozinha(@PathVariable("id") Long id){
            return ResponseEntity.ok(cozinhaService.buscar(id));
    }

    @ApiOperation("Cria uma cozinha")
    @ApiResponses(@ApiResponse(code = 201,message = "cidade criada"))
    @PostMapping
    public ResponseEntity<CozinhaDTO> criaCozinha(@RequestBody @Valid CozinhaInput cozinha){
        return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaService.salvar(cozinha));
    }

    @ApiOperation("Lista as cozinhas")
    @GetMapping
    public ResponseEntity<Page<CozinhaDTO>> listarCozinhas(Pageable pageable){
            return ResponseEntity.status(HttpStatus.OK).body(cozinhaService.listar(pageable));
    }

    @ApiOperation("Atualiza uma cozinha")
    @ApiResponses({
            @ApiResponse(code = 200,message = "Cozinha atualizada"),
            @ApiResponse(code = 404,message = "Cozinha não encontrada",response = Erro.class),
            @ApiResponse(code = 400,message = "Id da cozinha inválido",response = Erro.class)
    })
    @PutMapping("/{id_cozinha}")
    public ResponseEntity<CozinhaDTO> atualizarCozinha(
            @PathVariable("id_cozinha") Long idCozinha,
            @Valid @RequestBody CozinhaInput cozinhaInput){
        return ResponseEntity.ok(cozinhaService.update(idCozinha,cozinhaInput));
    }

    @ApiOperation("Deleta uma cozinha por id")
    @ApiResponses({
            @ApiResponse(code = 200,message = "Cozinha excluída"),
            @ApiResponse(code = 404,message = "Cozinha não encontrada",response = Erro.class),
            @ApiResponse(code = 400,message = "Id da cozinha inválido",response = Erro.class)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletaCozinha(@PathVariable("id") Long id) {
            cozinhaService.excluir(id);
            return ResponseEntity.status(HttpStatus.OK).body("cozinha excluída com sucesso com o id = "+id);
    }
}
