package com.igorfood.domain.listener;

import com.igorfood.domain.event.PedidoConfirmadoEvento;
import com.igorfood.domain.model.Pedido;
import com.igorfood.domain.services.EnvioEmailService;
import com.igorfood.domain.services.EnvioEmailService.Menssagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    @EventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvento event){
        Pedido pedido = event.getPedido();
        Menssagem menssagem = Menssagem.builder()
                .assunto(pedido.getRestaurante().getNome() + "pedido confirmado")
                .corpo("pedido-confirmado.html")
                .destinatario(pedido.getCliente().getEmail())
                .variavel("pedido",pedido)
                .build();
        envioEmailService.enviar(menssagem);
    }
}
