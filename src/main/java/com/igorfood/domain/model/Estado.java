package com.igorfood.domain.model;

import com.igorfood.Groups;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Estado {

    @NotNull(groups = Groups.EstadoId.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;
}
