package com.igorfood.domain.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "restaurante")
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "taxa_frete",nullable = false)
    private BigDecimal taxaFrete;

    @ManyToOne
    @JoinColumn(name = "cozinha_id")
    private Cozinha cozinha;

    @OneToMany(mappedBy = "restaurante")
    private Set<Produto>  produtos;

    @Embedded
    private Endereco endereco;

    private OffsetDateTime dataAtualizacao;

    private OffsetDateTime dataCadastro;

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaPagamento> formaPagamentos;

    @ManyToMany
    @JoinTable(
            name = "restaurante_usuario_responsavel",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> usuarios;

    private Boolean ativado = Boolean.TRUE;

    private Boolean aberto;

    public void abertura(){setAberto(true);}

    public void fechamento(){setAberto(false);}

    public void ativar(){
        setAtivado(true);
    }
    public void desativar(){
        setAtivado(false);
    }

    public boolean removerFormaPagamento(FormaPagamento formaPagamento){
        return this.getFormaPagamentos().remove(formaPagamento);
    }

    public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
        return this.getFormaPagamentos().add(formaPagamento);
    }

    public void associarUsuario(Usuario usuario) {
        getUsuarios().add(usuario);
    }
    public void desassociarUsuario(Usuario usuario) {
        getUsuarios().remove(usuario);
    }

    public boolean contemFormaPagamento(FormaPagamento formaPagamento) {
        return getFormaPagamentos().contains(formaPagamento);
    }

    public boolean naoContemFormaPagamento(FormaPagamento formaPagamento) {
        return !contemFormaPagamento(formaPagamento);
    }
}
