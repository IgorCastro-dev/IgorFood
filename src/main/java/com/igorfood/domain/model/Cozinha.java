package com.igorfood.domain.model;

import javax.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class Cozinha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
}
