package com.igorfood.controller;

import com.igorfood.dtos.input.FotoArquivoInput;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("igorfood/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizaFoto(
            @PathVariable("restauranteId") Long restauranteId,
            @PathVariable("produtoId") Long produtoId,
            FotoArquivoInput fotoArquivoInput){

        String nomeArquivo = UUID.randomUUID().toString()+"_"+fotoArquivoInput.getArquivo().getOriginalFilename();
        Path localArquivo = Path.of("/Users/igorj/Videos/teste",nomeArquivo);

        System.out.println(fotoArquivoInput.getDescricao());
        System.out.println(localArquivo);
        System.out.println(fotoArquivoInput.getArquivo().getContentType());

        try {
            fotoArquivoInput.getArquivo().transferTo(localArquivo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
