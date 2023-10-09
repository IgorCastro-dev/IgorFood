package com.igorfood.infrastruture.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.igorfood.core.storage.StorageProperties;
import com.igorfood.domain.services.FotoStorageService;
import com.igorfood.exception.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;

@Service
public class S3StorageImpl implements FotoStorageService {
    @Autowired
    private AmazonS3 amazonS3;
    @Autowired
    private StorageProperties storageProperties;
    @Override
    public void arquivar(NovaFoto novaFoto) {
            String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            Bucket bucket = amazonS3.createBucket(storageProperties.getS3().getBucket());
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucket.getName(),
                    caminhoArquivo,
                    novaFoto.getInputStream(),
                    objectMetadata
            );
            System.out.println("putObject aqui:"+putObjectRequest);
            amazonS3.putObject(putObjectRequest);
    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s",storageProperties.getS3().getDiretorioFotos(),nomeArquivo);
    }

    @Override
    public void remover(String nomeArquivo) throws IOException {

    }

    @Override
    public InputStream recuperar(String nomeArquivo) {
        return null;
    }
}
