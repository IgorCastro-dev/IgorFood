package com.igorfood.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToMany
    @JoinTable(name = "grupo_permissao",
        joinColumns = @JoinColumn(name = "grupo_id"),
        inverseJoinColumns = @JoinColumn(name = "permissao_id"))
    private Set<Permissao> permissoes;

    public void desassociarPermissao(Permissao permissao) {
        permissoes.remove(permissao);
    }

    public void associarPermissao(Permissao permissao) {
        permissoes.add(permissao);
    }

}
