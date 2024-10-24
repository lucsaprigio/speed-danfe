package com.example.speed_danfe.controllers;

import java.io.ByteArrayInputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.example.speed_danfe.dto.DanfeDTO;
import com.example.speed_danfe.useCases.FindArchiveUseCase;
import com.example.speed_danfe.utils.GenerateDanfePdf;

@RestController
@RequestMapping("/api")
public class DanfeController {

    @Autowired
    private FindArchiveUseCase findArchiveUseCase;

    @PostMapping("/danfe")
    public ResponseEntity<byte[]> gerarDanfe(@RequestBody DanfeDTO danfeDTO) {
        try {
            byte[] arquivo = findArchiveUseCase.execute(danfeDTO.getChave());

            if (arquivo == null || arquivo.length == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            String xmlContent = new String(arquivo);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Document xmlDocument = factory.newDocumentBuilder().parse(new
            // InputSource((new StringReader(xmlContent))));
            Document xmlDocument = factory.newDocumentBuilder().parse(new ByteArrayInputStream(xmlContent.getBytes()));

            var generate = new GenerateDanfePdf();

            System.out.println(xmlDocument);

            byte[] danfePdf = generate.gerarDanfePdf(xmlDocument); // Implemente esta

            // Retornar o PDF do DANFE como resposta
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "danfe.pdf");

            return ResponseEntity.ok().headers(headers).body(danfePdf);

            // return ResponseEntity.ok().body(arquivo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}