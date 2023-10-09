package com.igorfood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
public class FotoProduto {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "produto_id")
    private Long id;

    @OneToOne
    @MapsId
    private Produto produto;

    private String nomeArquivo;

    private String descricao;

    private String contentType;

    private Long tamanho;
}
