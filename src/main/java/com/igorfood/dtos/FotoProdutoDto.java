package com.igorfood.dtos;

import lombok.Data;

@Data
public class FotoProdutoDto {
    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;
}
