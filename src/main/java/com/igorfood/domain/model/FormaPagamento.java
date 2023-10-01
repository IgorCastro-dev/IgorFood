package com.igorfood.domain.model;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "forma_pagamento")
public class FormaPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
}
