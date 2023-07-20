package com.igorfood.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.igorfood.Groups;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.Data;


@Data
@Entity
public class Cidade {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @JsonIgnoreProperties(value = "nome",allowGetters = true)
    @Valid
    @ConvertGroup(from = Default.class,to = Groups.EstadoId.class)
    @NotNull
    @ManyToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;
}
