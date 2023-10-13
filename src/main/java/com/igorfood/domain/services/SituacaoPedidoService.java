package com.igorfood.domain.services;

import com.igorfood.domain.model.Pedido;
import com.igorfood.domain.services.EnvioEmailService.Menssagem;
import javax.transaction.Transactional;

import com.igorfood.infrastruture.email.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SituacaoPedidoService {

    @Autowired
    EnvioEmailService envioEmailService;

    @Autowired
    private PedidoService pedidoService;

    @Transactional
    public void confirmar(String pedidoCodigo) {
        Pedido pedido = pedidoService.getPedido(pedidoCodigo);
        pedido.confirmar();
        Menssagem menssagem = Menssagem.builder()
                .assunto(pedido.getRestaurante().getNome() + "pedido confirmado")
                .corpo("pedido-confirmado.html")
                .destinatario(pedido.getCliente().getEmail())
                .variavel("pedido",pedido)
                .build();
        envioEmailService.enviar(menssagem);
    }
    @Transactional
    public void entregue(String pedidoCodigo) {
        Pedido pedido = pedidoService.getPedido(pedidoCodigo);
        pedido.entregue();
    }

    @Transactional
    public void cancelar(String pedidoCodigo) {
        Pedido pedido = pedidoService.getPedido(pedidoCodigo);
        pedido.cancelar();
    }
}
