package com.igorfood.domain.model;

import com.igorfood.exception.NegocioException;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

@Data
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo = UUID.randomUUID().toString();
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;

    @Embedded
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.CRIADO;;

    @CreationTimestamp
    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @OneToMany(mappedBy = "pedido",cascade = CascadeType.ALL)
    private List<ItemPedido> itens ;

    public void calcularValorTotal() {
        this.subtotal = getItens().stream()
                .map(itemPedido -> itemPedido.getPrecoTotal())
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    public void confirmar() {
        setStatus(StatusPedido.CONFIRMADO);
        setDataConfirmacao(OffsetDateTime.now());
    }

    public void entregue() {
        setStatus(StatusPedido.ENTREGUE);
        setDataEntrega(OffsetDateTime.now());
    }

    public void cancelar() {
        setStatus(StatusPedido.CANCELADO);
        setDataCancelamento(OffsetDateTime.now());
    }

    private void setStatus(StatusPedido statusNovo){
        if(getStatus().isProibidoMudarStatus(statusNovo)){
            throw new NegocioException(String.format("Status do pedido %d não pode ser alterado de %s para %s",
                    getId(),getStatus().getTitle(),statusNovo.getTitle()));
        }
        this.status = statusNovo;
    }
}
