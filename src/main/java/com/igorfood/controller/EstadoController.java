package com.igorfood.controller;

import com.igorfood.domain.model.Estado;
import com.igorfood.dtos.EstadoDTO;
import com.igorfood.dtos.input.EstadoInput;
import com.igorfood.exception.exceptionhandler.Erro;
import com.igorfood.services.EstadoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Estados")
@RestController
@RequestMapping("igorfood/estados")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @ApiOperation("Listar os estados")
    @GetMapping
    public List<EstadoDTO> listar() {
        return estadoService.findAll();
    }

    @ApiOperation("Busca o estado por id")
    @ApiResponses({
            @ApiResponse(code = 404,message = "Estado não encontrado",response = Erro.class),
            @ApiResponse(code = 400,message = "Id do Estado está inválido")
    })
    @GetMapping("/{estadoId}")
    public EstadoDTO buscar(@PathVariable Long estadoId) {
        return estadoService.buscar(estadoId);
    }


    @ApiOperation("Cadastra um novo estado")
    @ApiResponses(@ApiResponse(code = 201,message = "Estado criado"))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDTO adicionar(@RequestBody EstadoInput estado) {
        return estadoService.salvar(estado);
    }


    @ApiOperation("Atualiza o estado pelo id")
    @ApiResponses({
            @ApiResponse(code = 404,message = "Estado não encontrado",response = Erro.class),
            @ApiResponse(code = 200,message = "Estado atualizado")
    })
    @PutMapping("/{estadoId}")
    public EstadoDTO atualizar(@PathVariable Long estadoId,
                            @RequestBody EstadoInput estado) {

        return estadoService.update(estadoId,estado);
    }

    @ApiOperation("Deleta um estado")
    @ApiResponses({
            @ApiResponse(code = 200,message = "Estado deletado"),
            @ApiResponse(code = 404,message = "Estado não encontrado")
    })
    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        estadoService.excluir(estadoId);
    }

}
