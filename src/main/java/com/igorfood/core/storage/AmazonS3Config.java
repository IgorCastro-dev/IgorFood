package com.igorfood.core.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.igorfood.domain.services.FotoStorageService;
import com.igorfood.infrastruture.storage.LocalFotoStorageImpl;
import com.igorfood.infrastruture.storage.S3StorageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {

    @Autowired
    private StorageProperties storageProperties;

    @Bean
    public AmazonS3 amazonS3(){

        BasicAWSCredentials credential = new BasicAWSCredentials(
                storageProperties.getS3().getIdChaveAcesso(),
                storageProperties.getS3().getChaveAcessoSecreta()
        );
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credential))
                .withRegion(storageProperties.getS3().getRegiao())
                .build();
    }

    @Bean
    public FotoStorageService fotoStorageService() {
        // Acho melhor usar switch aqui do que if/else if
        switch (storageProperties.getImpl()) {
            case LOCAL:
                return new LocalFotoStorageImpl();
            case S3:
                return new S3StorageImpl();
            default:
                return null;
        }
    }
}
