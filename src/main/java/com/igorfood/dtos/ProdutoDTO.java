package com.igorfood.dtos;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoDTO {
    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Boolean ativo;
}
