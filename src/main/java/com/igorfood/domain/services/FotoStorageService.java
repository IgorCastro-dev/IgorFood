package com.igorfood.domain.services;

import lombok.Builder;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {
    void arquivar(NovaFoto fotoStorage);

    void remover(String nomeArquivo) throws IOException;

    FotoRecuperada recuperar(String nomeArquivo);

    default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) throws IOException {
        this.arquivar(novaFoto);
        if (nomeArquivoAntigo != null){
            this.remover(nomeArquivoAntigo);
        }
    }


    default String gerarNomeNovo(String nomeOriginal) {
        String novoNome = UUID.randomUUID()+"_"+nomeOriginal;
        return novoNome;
    }


    @Data
    @Builder
    class NovaFoto{
        private String nomeArquivo;
        private InputStream inputStream;
        private String contentType;
    }

    @Data
    @Builder
    class NovoVideo{
        private String nomeArquivo;
        private InputStream inputStream;
        private String contentType;
    }

    @Data
    @Builder
    class FotoRecuperada{
        private InputStream inputStream;
        private String url;

        public boolean temUrl(){
            return url != null;
        }

        public boolean temInpuStream(){
            return inputStream != null;
        }
    }
}
