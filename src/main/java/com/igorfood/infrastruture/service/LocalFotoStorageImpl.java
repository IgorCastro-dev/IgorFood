package com.igorfood.infrastruture.service;

import com.igorfood.core.storage.StorageProperties;
import com.igorfood.domain.services.FotoStorageService;
import com.igorfood.exception.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

//@Service
public class LocalFotoStorageImpl implements FotoStorageService {
//    @Value("${igorfood.storage.local.diretorio-fotos}")
//    private Path localFoto;
    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void arquivar(NovaFoto novaFoto) {
        try {
            Path arquivoPath= getArquivoPath(novaFoto.getNomeArquivo());
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
        } catch (Exception e) {
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    public void remover(String nomeArquivo) throws IOException {
        Path caminhoArquivo = getArquivoPath(nomeArquivo);
        Files.deleteIfExists(caminhoArquivo);
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            Path caminhoArquivo = getArquivoPath(nomeArquivo);
            FotoRecuperada fotoRecuperada = FotoRecuperada.builder().inputStream(Files.newInputStream(caminhoArquivo)).build();
            return fotoRecuperada;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private Path getArquivoPath(String nomeArquivo) {

        return storageProperties.getLocal().getDiretorioFotos().resolve(nomeArquivo);
    }
}
