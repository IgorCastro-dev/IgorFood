package com.igorfood.dtos.input;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FotoArquivoInput {

    @NotNull
    private MultipartFile arquivo;

    @NotBlank
    private String descricao;
}
