package com.igorfood.controller;


import com.igorfood.dtos.FormaPagamentoDTO;
import com.igorfood.dtos.input.FormaPagamentoInput;
import com.igorfood.exception.exceptionhandler.Erro;
import com.igorfood.domain.services.FormaPagamentoService;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Api(tags = "Formas de pagamento")
@RestController
@RequestMapping("igorfood/forma-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @ApiOperation("Lista as formas de pagamento")
    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> listarFormaPagamento(){
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formaPagamentoService.listar());
    }

    @ApiOperation("Busca uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Erro.class),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Erro.class)
    })
    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoDTO> buscarFormaPagamento(@PathVariable Long formaPagamentoId){
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formaPagamentoService.buscar(formaPagamentoId));
    }


    @ApiOperation("Cadastra uma forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Forma de pagamento cadastrada"),
    })
    @PostMapping
    public ResponseEntity<FormaPagamentoDTO> salvarFormaPagamento(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput){
        return ResponseEntity.status(HttpStatus.CREATED).body(formaPagamentoService.salvar(formaPagamentoInput));
    }

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Erro.class)
    })
    @PutMapping("/{id}")
    public ResponseEntity<FormaPagamentoDTO> atualizarFormaPagamento(
            @PathVariable("id") Long formaPagamentoId,
            @RequestBody @Valid FormaPagamentoInput formaPagamentoInput
    ){
        return ResponseEntity.ok(formaPagamentoService.atualizar(formaPagamentoId,formaPagamentoInput));
    }

    @ApiOperation("Exclui uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de pagamento excluída"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Erro.class)
    })
    @DeleteMapping("/{formaPagamentoId}")
    public ResponseEntity<String> deletarFormaPagamento(@PathVariable Long formaPagamentoId){
        formaPagamentoService.deletar(formaPagamentoId);
        return ResponseEntity.ok(String.format("Forma de pagamento com o id = %d foi excluido"));
    }
}
