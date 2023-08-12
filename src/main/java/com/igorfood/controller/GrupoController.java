package com.igorfood.controller;


import com.igorfood.dtos.GrupoDTO;
import com.igorfood.dtos.input.GrupoInput;
import com.igorfood.services.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("igorfood/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @GetMapping
    public ResponseEntity<List<GrupoDTO>> listarGrupo(){
        return ResponseEntity.ok(grupoService.listar());
    }

    @GetMapping("/{grupoId}")
    public ResponseEntity<GrupoDTO> buscarGrupo(@PathVariable Long grupoId){
        return ResponseEntity.ok(grupoService.buscar(grupoId));
    }

    @PostMapping
    public ResponseEntity<GrupoDTO> salvarGrupo(@RequestBody GrupoInput grupoInput){
        return ResponseEntity.status(HttpStatus.CREATED).body(grupoService.salvar(grupoInput));
    }

    @PutMapping("/{grupoId}")
    public ResponseEntity<GrupoDTO> atualizarGrupo(
            @PathVariable Long grupoId,
            @RequestBody GrupoInput grupoInput){
        return ResponseEntity.ok(grupoService.atualizar(grupoId,grupoInput));
    }

    @DeleteMapping("/{grupoId}")
    public ResponseEntity<String> deletarGrupo(@PathVariable Long grupoId){
        grupoService.deletar(grupoId);
        return ResponseEntity.ok(String.format("Grupo com o id = %s deletado",grupoId));
    }
}
