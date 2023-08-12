package com.igorfood.services;

import com.igorfood.domain.model.*;
import com.igorfood.domain.repository.PedidoRepository;
import com.igorfood.dtos.PedidoDTO;
import com.igorfood.dtos.PedidoResumoDTO;
import com.igorfood.dtos.input.PedidoInput;
import com.igorfood.dtos.input.RestauranteIdInput;
import com.igorfood.exception.NegocioException;
import com.igorfood.exception.PedidoNaoEncontradoException;
import com.igorfood.modelmapper.PedidoAssembler;
import com.igorfood.modelmapper.PedidoResumeAssembler;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoAssembler pedidoAssembler;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private ItemPedidoService itemPedidoService;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private PedidoResumeAssembler pedidoResumeAssembler;

    public List<PedidoResumoDTO> listar(){
        List<Pedido> pedidos = pedidoRepository.findAll();
        return (List<PedidoResumoDTO>) pedidoResumeAssembler.collectionToDTO(pedidos);
    }

    public PedidoDTO buscar(String pedidoCodigo) {
        Pedido pedido = getPedido(pedidoCodigo);
        return pedidoAssembler.toDTO(pedido);
    }

    @Transactional
    public PedidoDTO salvar(PedidoInput pedidoInput) {

        Pedido pedido = pedidoAssembler.toEntity(pedidoInput);
        // TODO pegar usuário autenticado
        pedido.setCliente(new Usuario());
        pedido.getCliente().setId(1L);
        validarPedido(pedido);
        validarItens(pedido);
        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();
        return pedidoAssembler.toDTO(pedidoRepository.save(pedido));
    }

    private void validarItens(Pedido pedido) {
        pedido.getItens().forEach(itemPedido ->
                itemPedidoService.salvarItem(pedido,itemPedido));
    }

    private void validarPedido(Pedido pedido) {
        Restaurante restaurante = restauranteService.getRestaurante(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = formaPagamentoService.getFormaPagamento(pedido.getFormaPagamento().getId());
        if(restaurante.naoContemFormaPagamento(formaPagamento)){
            throw new NegocioException(String.format("A forma de pagamento %s não é aceita nesse restaurante",formaPagamento.getDescricao()));
        }

        Cidade cidade = cidadeService.getCidade(pedido.getEndereco().getCidade().getId());
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
        pedido.getEndereco().setCidade(cidade);
    }

    public Pedido getPedido(String pedidoCodigo) {
        return pedidoRepository.findByCodigo(pedidoCodigo).orElseThrow(
                () -> new PedidoNaoEncontradoException(pedidoCodigo)
        );
    }
}
