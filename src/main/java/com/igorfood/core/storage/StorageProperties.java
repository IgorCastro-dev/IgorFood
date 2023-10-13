package com.igorfood.core.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Data
@Component
@ConfigurationProperties("igorfood.storage")
public class StorageProperties {
    private Implementacao impl = Implementacao.LOCAL;
    private Local local= new Local();
    private S3 s3 = new S3();
    @Data
    public class Local{
        private Path diretorioFotos;
    }

    @Data
    public class S3{
        private String idChaveAcesso;
        private String chaveAcessoSecreta;
        private String regiao;
        private String bucket;
        private String diretorioFotos;
    }

    public enum Implementacao{
        S3,LOCAL
    }
}
