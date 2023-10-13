package com.igorfood.dtos.input;

import com.igorfood.core.validation.FileContentType;
import com.igorfood.core.validation.FileSize;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FotoArquivoInput {

    @NotNull
    //@FileContentType(allowed = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE})
    //@FileSize(max = "200KB")
    private MultipartFile arquivo;

    @NotBlank
    private String descricao;
}
