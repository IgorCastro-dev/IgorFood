package com.igorfood.controller;

import com.igorfood.domain.model.Cozinha;
import com.igorfood.dtos.CozinhaDTO;
import com.igorfood.dtos.input.CozinhaInput;
import com.igorfood.exception.EntidadeEmUsoException;
import com.igorfood.exception.EntidadeNaoEncontradaException;
import com.igorfood.services.CozinhaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("igorfood/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaService cozinhaService;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCozinha(@PathVariable("id") Long id){
        try{
            return ResponseEntity.ok(cozinhaService.buscar(id));
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity<CozinhaDTO> criaCozinha(@RequestBody @Valid CozinhaInput cozinha){
        return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaService.salvar(cozinha));
    }

    @GetMapping
    public ResponseEntity<List<CozinhaDTO>> listarCozinhas(){
            return ResponseEntity.status(HttpStatus.OK).body(cozinhaService.listar());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletaCozinha(@PathVariable("id") Long id) {
        try {
            cozinhaService.excluir(id);
            return ResponseEntity.status(HttpStatus.OK).body("cozinha excluída com sucesso com o id = "+id);

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }
}
