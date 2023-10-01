package com.igorfood.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.igorfood.Groups;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import lombok.Data;

import javax.persistence.*;


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
