package com.igorfood.controller;


import com.igorfood.dtos.GrupoDTO;
import com.igorfood.dtos.input.GrupoInput;
import com.igorfood.exception.exceptionhandler.Erro;
import com.igorfood.services.GrupoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Api(tags = "Grupos")
@RestController
@RequestMapping(path = "igorfood/grupos",produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @ApiOperation(value = "listar os grupos")
    @GetMapping
    public ResponseEntity<List<GrupoDTO>> listarGrupo(){
        return ResponseEntity.ok(grupoService.listar());
    }


    @ApiOperation("buscar grupo por id")
    @ApiResponses({
            @ApiResponse(code = 400,message = "Id de grupo inválido",response = Erro.class),
            @ApiResponse(code = 404,message = "Grupo não encontrado",response = Erro.class)
    })
    @GetMapping("/{grupoId}")
    public ResponseEntity<GrupoDTO> buscarGrupo(@PathVariable Long grupoId){
        return ResponseEntity.ok(grupoService.buscar(grupoId));
    }

    @ApiOperation("Criar grupo")
    @ApiResponses({
            @ApiResponse(code = 201,message = "grupo criado")
    })
    @PostMapping
    public ResponseEntity<GrupoDTO> salvarGrupo(@Valid @RequestBody GrupoInput grupoInput){
        return ResponseEntity.status(HttpStatus.CREATED).body(grupoService.salvar(grupoInput));
    }

    @ApiOperation("Atualiza Grupo")
    @ApiResponses({
            @ApiResponse(code = 400,message = "Id do grupo inválido",response = Erro.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado",response = Erro.class)
    })
    @PutMapping("/{grupoId}")
    public ResponseEntity<GrupoDTO> atualizarGrupo(
            @PathVariable Long grupoId,
            @RequestBody GrupoInput grupoInput){
        return ResponseEntity.ok(grupoService.atualizar(grupoId,grupoInput));
    }


    @ApiOperation("Deleta grupo")
    @ApiResponses({
            @ApiResponse(code = 200,message = "Grupo deletado"),
            @ApiResponse(code = 404, message = "Grupo não encontrado")
    })
    @DeleteMapping("/{grupoId}")
    public ResponseEntity<String> deletarGrupo(@PathVariable Long grupoId){
        grupoService.deletar(grupoId);
        return ResponseEntity.ok(String.format("Grupo com o id = %s deletado",grupoId));
    }
}
