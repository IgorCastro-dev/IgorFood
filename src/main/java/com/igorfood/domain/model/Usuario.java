package com.igorfood.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String senha;

    @CreationTimestamp
    @Column(name = "data_cadastro")
    private OffsetDateTime dataCadastro;

    @ManyToMany
    @JoinTable(name = "usuario_grupo",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "grupo_id"))
    private Set<Grupo> grupos;


    public boolean senhaCoincideCom(String senha){
        return getSenha().equals(senha);
    }

    public boolean senhaNaoCoincideCom(String senha){
        return !senhaCoincideCom(senha);
    }

    public void associarGrupo(Grupo grupo) {
        getGrupos().add(grupo);
    }
    public void desassociarGrupo(Grupo grupo) {
        getGrupos().remove(grupo);
    }
}
