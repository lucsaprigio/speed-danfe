package com.example.speed_danfe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.speed_danfe.entities.DanfeEntity;

public interface DanfeRepository extends JpaRepository<DanfeEntity, Integer> {

    @Query(value= "SELECT FIRST 1 XML.ARQUIVO FROM INDICE INNER JOIN XML ON INDICE.ITEN = XML.LANCAB WHERE CHAVE = :chave AND E_S = 'E' AND CNPJ = :cnpj", nativeQuery = true)
    byte[] findArquivoByChaveNfe(@Param("chave") String chave, @Param("cnpj") String cnpj);
}
