package com.igorfood.core.email;

import lombok.Data;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


@Data
@Component
@ConfigurationProperties("igorfood.email")
public class EmailProperties {
    private Implementacao impl = Implementacao.FAKE;

    @NotNull
    private String remetente;

    private Integer port;

    private String host;

    private String usename;

    private String password;

    public enum Implementacao {
        SMTP, FAKE, SANDBOX
    }
}
