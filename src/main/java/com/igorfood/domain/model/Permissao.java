package com.igorfood.domain.model;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
public class Permissao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;
}
