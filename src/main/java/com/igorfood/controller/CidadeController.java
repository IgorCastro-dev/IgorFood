package com.igorfood.controller;

import com.igorfood.domain.model.Cidade;
import com.igorfood.domain.repository.CidadeRepository;
import com.igorfood.dtos.CidadeDTO;
import com.igorfood.dtos.input.CidadeInput;
import com.igorfood.exception.exceptionhandler.Erro;
import com.igorfood.domain.services.CidadeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
@RestController
@RequestMapping(path = "igorfood/cidades",produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeService cidadeService;

    @ApiOperation("Busca uma cidade por id")
    @ApiResponses({
            @ApiResponse(code = 400,message = "Id da cidade inválido",response = Erro.class),
            @ApiResponse(code = 404,message = "Id da cidade não encontrado",response = Erro.class)
    })
    @GetMapping("/{cidadeId}")
    public ResponseEntity<CidadeDTO> buscar(@ApiParam(value = "Id de uma cidade",example = "1")@PathVariable Long cidadeId) {
        return ResponseEntity.ok().body(cidadeService.buscar(cidadeId));
    }

    @ApiOperation("Lista as cidades")
    @GetMapping
    public ResponseEntity<List<Cidade>> listar() {
        return ResponseEntity.ok().body(cidadeRepository.findAll());
    }

//	@PostMapping
//	public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
//		try {
//			cidade = cadastroCidade.salvar(cidade);
//
//			return ResponseEntity.status(HttpStatus.CREATED)
//					.body(cidade);
//		} catch (EntidadeNaoEncontradaException e) {
//			return ResponseEntity.badRequest()
//					.body(e.getMessage());
//		}
//	}

    @ApiOperation("Cria uma cidade")
    @ApiResponses({
            @ApiResponse(code = 201,message = "Cidade criada")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CidadeDTO> adicionar(@ApiParam(name = "corpo",value = "representação de uma nova cidade") @Valid @RequestBody CidadeInput cidade) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cidadeService.salvar(cidade));
    }

//	@PutMapping("/{cidadeId}")
//	public ResponseEntity<?> atualizar(@PathVariable Long cidadeId,
//			@RequestBody Cidade cidade) {
//		try {
//			Cidade cidadeAtual = cidadeRepository.findById(cidadeId).orElse(null);
//
//			if (cidadeAtual != null) {
//				BeanUtils.copyProperties(cidade, cidadeAtual, "id");
//
//				cidadeAtual = cadastroCidade.salvar(cidadeAtual);
//				return ResponseEntity.ok(cidadeAtual);
//			}
//
//			return ResponseEntity.notFound().build();
//
//		} catch (EntidadeNaoEncontradaException e) {
//			return ResponseEntity.badRequest()
//					.body(e.getMessage());
//		}
//	}

    @ApiOperation("Atualiza a cidade por id")
    @ApiResponses({
            @ApiResponse(code = 200,message = "Cidade atualizada"),
            @ApiResponse(code = 400, message = "Id da cidade inválido",response = Erro.class),
            @ApiResponse(code = 404,message = "Cidade não encontrado",response = Erro.class)
    })
    @PutMapping("/{cidadeId}")
    public ResponseEntity<CidadeDTO> atualizar(@ApiParam(value = "Id de uma cidade",example ="1")@PathVariable Long cidadeId,
                            @ApiParam(name = "corpo",value = "Representação de uma atualização de uma cidade")@RequestBody CidadeInput cidade) {
        return ResponseEntity.ok(cidadeService.update(cidadeId,cidade));
    }

    @ApiOperation("Deleta uma cidade por id")
    @ApiResponses({
            @ApiResponse(code = 204,message = "Cidade Excluída"),
            @ApiResponse(code = 404,message = "Cidade não encontrada",response = Erro.class)
    })
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@ApiParam(value = "Id de uma cidade",example ="1")@PathVariable Long cidadeId) {
        cidadeService.excluir(cidadeId);
    }

}
