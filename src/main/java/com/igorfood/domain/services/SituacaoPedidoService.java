package com.igorfood.domain.services;

import com.igorfood.domain.model.Pedido;
import com.igorfood.domain.repository.PedidoRepository;
import com.igorfood.domain.services.EnvioEmailService.Menssagem;
import javax.transaction.Transactional;

import com.igorfood.infrastruture.email.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SituacaoPedidoService {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public void confirmar(String pedidoCodigo) {
        Pedido pedido = pedidoService.getPedido(pedidoCodigo);
        pedido.confirmar();
        pedidoRepository.save(pedido);
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
