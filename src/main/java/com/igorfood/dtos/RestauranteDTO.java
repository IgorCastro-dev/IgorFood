package com.igorfood.dtos;

import com.igorfood.domain.model.Cozinha;
import com.igorfood.domain.model.Endereco;
import com.igorfood.domain.model.Produto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class RestauranteDTO {
    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaDTO cozinha;
    private Boolean ativado;
    private EnderecoDTO endereco;
    private Boolean aberto;
}
