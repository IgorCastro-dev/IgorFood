package com.igorfood.domain.services;

import com.igorfood.domain.model.FotoProduto;
import com.igorfood.domain.model.Produto;
import com.igorfood.domain.repository.ProdutoRepository;
import com.igorfood.dtos.FotoProdutoDto;
import com.igorfood.dtos.input.FotoArquivoInput;
import com.igorfood.exception.FotoProdutoNaoEncontradaException;
import com.igorfood.modelmapper.FotoProdutoAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private FotoProdutoAssembler fotoProdutoAssembler;

    @Autowired
    private FotoStorageService fotoStorageService;

    @GetMapping
    public FotoProduto buscarFotoOuFalhar(Long produtoId,Long restauranteId){
        FotoProduto fotoProduto = produtoRepository.findFotoById(restauranteId,produtoId).orElseThrow(
                ()->new FotoProdutoNaoEncontradaException(produtoId,restauranteId));
        return fotoProduto;
    }

    @Transactional
    public FotoProdutoDto fotoProdutoSalvar(FotoArquivoInput fotoArquivoInput, Long produtoId,Long restauranteId) throws IOException {
        FotoProduto fotoProduto = new FotoProduto();
        Produto produto = produtoService.getProduto(restauranteId,produtoId);
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(fotoArquivoInput.getDescricao());
        fotoProduto.setTamanho(fotoArquivoInput.getArquivo().getSize());
        fotoProduto.setContentType(fotoArquivoInput.getArquivo().getContentType());
        String novoNomeArquivo = fotoStorageService.gerarNomeNovo(fotoArquivoInput.getArquivo().getOriginalFilename());
        fotoProduto.setNomeArquivo(novoNomeArquivo);

        String fotoExistente = null;
        Optional<FotoProduto> fotoProdutoOptional = produtoRepository.findFotoById(restauranteId,produtoId);
        if (fotoProdutoOptional.isPresent()){
            fotoExistente = fotoProdutoOptional.get().getNomeArquivo();
            produtoRepository.delete(fotoProdutoOptional.get());

        }

        produtoRepository.save(fotoProduto);
        produtoRepository.flush();
        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                                        .nomeArquivo(fotoProduto.getNomeArquivo())
                                        .inputStream(fotoArquivoInput.getArquivo().getInputStream())
                                        .build();
        fotoStorageService.substituir(fotoExistente,novaFoto);
        return fotoProdutoAssembler.modelToDto(fotoProduto);
    }

    @Transactional
    public void fotoProdutoDeletar(FotoProduto fotoProduto) {
        try {
            produtoRepository.delete(fotoProduto);
            produtoRepository.flush();
            fotoStorageService.remover(fotoProduto.getNomeArquivo());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
