package com.igorfood.controller;

import com.igorfood.dtos.UsuarioDTO;
import com.igorfood.dtos.input.SenhaInput;
import com.igorfood.dtos.input.UsuarioInput;
import com.igorfood.dtos.input.UsuarioSemSenhaInput;
import com.igorfood.domain.services.UsuarioService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("igorfood/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Set<UsuarioDTO>> listarUsuario(){
        return ResponseEntity.ok(usuarioService.listar());
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioDTO> buscarUsuario(@PathVariable("usuarioId") Long usuarioId){
        return ResponseEntity.ok(usuarioService.buscar(usuarioId));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvarUsuario(@RequestBody @Valid UsuarioInput usuarioInput){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvar(usuarioInput));
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(
            @PathVariable("usuarioId") Long usuarioId,
            @RequestBody @Valid UsuarioSemSenhaInput usuarioSemSenhaInput
    ){
        return ResponseEntity.ok(usuarioService.atualizarUsuario(usuarioId,usuarioSemSenhaInput));
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<String> excluirUsuario(@PathVariable("usuarioId") Long usuarioId){
        usuarioService.excluir(usuarioId);
        return ResponseEntity.ok("Usuario removido com o id = " + usuarioId);
    }

    @PutMapping("/{usuarioId}/senha")
    public ResponseEntity<String> atualizarSenha(
            @PathVariable("usuarioId") Long usuarioId,
            @RequestBody @Valid SenhaInput senhaInput){
        usuarioService.atualizarSenha(usuarioId,senhaInput);
        return ResponseEntity.ok("Senha atualizada com sucesso");
    }

}






















