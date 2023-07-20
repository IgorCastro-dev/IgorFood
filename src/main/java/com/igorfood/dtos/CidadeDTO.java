package com.igorfood.dtos;

import lombok.Data;

@Data
public class CidadeDTO {
    private Long id;
    private String nome;
    private EstadoDTO estado;
}
