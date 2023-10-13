package com.igorfood.infrastruture.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService{

    @Override
    public void enviar(Menssagem menssagem) {
        // Foi necessário alterar o modificador de acesso do método processarTemplate
        // da classe pai para "protected", para poder chamar aqui
        String corpo = processarTemplate(menssagem);


        log.info("[FAKE E-MAIL] Para: {}\n{}", menssagem.getDestinatarios(), corpo);
    }
}
