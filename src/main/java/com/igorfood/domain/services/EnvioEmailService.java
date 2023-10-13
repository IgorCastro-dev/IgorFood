package com.igorfood.domain.services;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;
import org.springframework.validation.annotation.Validated;
import java.util.Map;
import java.util.Set;

public interface EnvioEmailService {
    void enviar(Menssagem menssagem);

    @Validated
    @Builder
    @Data
    class Menssagem{

        @Singular
        private Set<String> destinatarios;

        @NonNull
        private String assunto;

        @NonNull
        private String corpo;

        @Singular("variavel")
        private Map<String,Object> variaveis;
    }
}
