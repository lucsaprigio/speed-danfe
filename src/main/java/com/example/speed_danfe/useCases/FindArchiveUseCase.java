package com.example.speed_danfe.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.speed_danfe.repositories.DanfeRepository;

@Service
public class FindArchiveUseCase {

    @Autowired
    private DanfeRepository danfeRepository;

    public byte[] execute(String chave, String cnpj) {
        return danfeRepository.findArquivoByChaveNfe(chave, cnpj);
    }
}
