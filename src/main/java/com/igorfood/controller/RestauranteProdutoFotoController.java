package com.igorfood.controller;

import com.igorfood.domain.model.FotoProduto;
import com.igorfood.domain.services.CatalogoFotoProdutoService;
import com.igorfood.domain.services.FotoStorageService;
import com.igorfood.dtos.FotoProdutoDto;
import com.igorfood.dtos.input.FotoArquivoInput;
import com.igorfood.exception.EntidadeNaoEncontradaException;
import com.igorfood.modelmapper.FotoProdutoAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("igorfood/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @Autowired
    private CatalogoFotoProdutoService produtoService;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Autowired
    private FotoProdutoAssembler fotoProdutoAssembler;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoDto atualizaFoto(
            @PathVariable("restauranteId") Long restauranteId,
            @PathVariable("produtoId") Long produtoId,
            @Valid FotoArquivoInput fotoArquivoInput) throws IOException {
        return produtoService.fotoProdutoSalvar(fotoArquivoInput,restauranteId,restauranteId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoDto buscarFoto(@PathVariable Long restauranteId,
                                     @PathVariable Long produtoId) {
            return fotoProdutoAssembler.modelToDto(produtoService.buscarFotoOuFalhar(produtoId,restauranteId));
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId,
                                                          @PathVariable Long produtoId,
                                                          @RequestHeader(name = "accept") String AcceptHeader) {
        try {
            FotoProduto fotoProduto = produtoService.buscarFotoOuFalhar(produtoId,restauranteId);
            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(AcceptHeader);
            verificaCompatibilidade(mediaTypeFoto,mediaTypesAceitas);
            FotoStorageService.FotoRecuperada foto = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
            if (foto.temUrl()){
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION,foto.getUrl())
                        .build();

            }else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new InputStreamResource(foto.getInputStream()));
            }
        }catch(EntidadeNaoEncontradaException | HttpMediaTypeNotAcceptableException e){
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping
    public void deletar(@PathVariable Long restauranteId,
                        @PathVariable Long produtoId){
        FotoProduto fotoProduto = produtoService.buscarFotoOuFalhar(produtoId,restauranteId);
        produtoService.fotoProdutoDeletar(fotoProduto);
    }

    private void verificaCompatibilidade(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypesAceitas.stream().anyMatch(mediaType ->
        mediaType.isCompatibleWith(mediaTypeFoto));
        if (!compativel){
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
    }
}


//    String nomeArquivo = UUID.randomUUID().toString()+"_"+fotoArquivoInput.getArquivo().getOriginalFilename();
//    Path localArquivo = Path.of("/Users/igorj/Videos/teste",nomeArquivo);
//
//        System.out.println(fotoArquivoInput.getDescricao());
//                System.out.println(localArquivo);
//                System.out.println(fotoArquivoInput.getArquivo().getContentType());
//
//                try {
//                fotoArquivoInput.getArquivo().transferTo(localArquivo);
//                } catch (IOException e) {
//                throw new RuntimeException(e);
//                }