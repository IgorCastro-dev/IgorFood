package com.igorfood.controller;

import com.igorfood.dtos.GrupoDTO;
import com.igorfood.domain.services.UsuarioGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("igorfood/usuarios/{usuario_id}/grupos")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioGrupoService usuarioGrupoService;

    @GetMapping
    public ResponseEntity<Set<GrupoDTO>> listarGrupo(
            @PathVariable("usuario_id") Long usuarioId
    ){
            return ResponseEntity.ok(usuarioGrupoService.listar(usuarioId));
    }

    @PutMapping("/{grupo_id}")
    public ResponseEntity<String> associarGrupo(
            @PathVariable("usuario_id") Long usuarioId,
            @PathVariable("grupo_id") Long grupoId
    ){
        usuarioGrupoService.associarGrupo(usuarioId,grupoId);
        return ResponseEntity.ok(String.format("Grupo %d foi associado com o Usuário %d",grupoId,usuarioId));
    }

    @DeleteMapping("/{grupo_id}")
    public ResponseEntity<String> desassociarGrupo(
            @PathVariable("usuario_id") Long usuarioId,
            @PathVariable("grupo_id") Long grupoId
    ){
        usuarioGrupoService.desassociarGrupo(usuarioId,grupoId);
        return ResponseEntity.ok(String.format("Grupo %d foi desassociado com o Usuário %d",grupoId,usuarioId));
    }
}
