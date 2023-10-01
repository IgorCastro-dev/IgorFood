package com.igorfood.controller;

import com.igorfood.domain.model.Grupo;
import com.igorfood.domain.model.Permissao;
import com.igorfood.dtos.PermissaoDTO;
import com.igorfood.services.PermissaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Api(tags = "GrupoPermissoes")
@RestController
@RequestMapping("igorfood/grupos/{grupo_id}/permissoes")
public class GrupoPermissaoController{

    @Autowired
    private PermissaoService permissaoService;

    @ApiOperation("Lista as prmissoes de um grupo")
    @GetMapping
    public ResponseEntity<Set<PermissaoDTO>> listarPermissoes(
            @PathVariable("grupo_id") Long grupoId
    ){
        return ResponseEntity.ok(permissaoService.listar(grupoId));
    }

    @ApiOperation("Desassocia uma permissão")
    @DeleteMapping("/{permissao_id}")
    public ResponseEntity<String> desassociarPermissao(
            @PathVariable("grupo_id") Long grupoId,
            @PathVariable("permissao_id") Long permissaoId
    ){
        permissaoService.desassociarPermissao(grupoId,permissaoId);
        return ResponseEntity.ok(String
                .format("Permissao com o id = %d foi desassociada do grupo com id = %d",permissaoId,grupoId));
    }

    @PutMapping("/{permissao_id}")
    public ResponseEntity<String> associarPermissao(
            @PathVariable("grupo_id") Long grupoId,
            @PathVariable("permissao_id") Long permissaoId
    ){
        permissaoService.associarPermissao(grupoId,permissaoId);
        return ResponseEntity.ok(String
                .format("Permissao com o id = %d foi associada do grupo com id = %d",permissaoId,grupoId));
    }
}
