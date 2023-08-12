package com.igorfood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {
    CRIADO("Criado"),
    CONFIRMADO("Confirmado",CRIADO),
    ENTREGUE("Entregue",CONFIRMADO),
    CANCELADO("Cancelado",CRIADO);

    private String title;
    private List<StatusPedido> statusPermitidosMudar;

    StatusPedido(String title,StatusPedido... statusPermitidosMudar){
        this.title = title;
        this.statusPermitidosMudar = Arrays.asList(statusPermitidosMudar);
    }

    public String getTitle(){
        return this.title;
    }

    public boolean isProibidoMudarStatus(StatusPedido statusNovo) {
        return !statusNovo.statusPermitidosMudar.contains(this);
    }
}
