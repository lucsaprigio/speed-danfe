package com.example.speed_danfe.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.speed_danfe.dto.DanfeDTO;

import javax.xml.parsers.DocumentBuilderFactory;

import java.io.ByteArrayInputStream;

import org.w3c.dom.Document;

import com.example.speed_danfe.utils.GenerateDanfePdf;

@RestController
@RequestMapping("/api")
public class DanfeController {

    @PostMapping(value = "/danfe", consumes = "text/xml")
    public ResponseEntity<byte[]> gerarDanfe(@RequestBody DanfeDTO xmlRequest) {
        System.out.println(xmlRequest.getXmlContent());
        try {
            String xmlContent = xmlRequest.getXmlContent();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document xmlDocument = factory.newDocumentBuilder().parse(new ByteArrayInputStream(xmlContent.getBytes()));

            var generate = new GenerateDanfePdf();

            byte[] danfePdf = generate.gerarDanfePdf(xmlDocument); // Implemente esta função para gerar o PDF

            // Retornar o PDF do DANFE como resposta
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "danfe.pdf");

            return ResponseEntity.ok().headers(headers).body(danfePdf);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/teste")
    public ResponseEntity<Object> teste(@RequestBody DanfeDTO teste) {
        try {
            System.out.println(teste.getXmlContent());

            var result = teste.getXmlContent();

            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
