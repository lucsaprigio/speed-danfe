package com.example.speed_danfe.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.*;

import org.w3c.dom.Document;

import net.sf.jasperreports.engine.data.JRXmlDataSource;

public class GenerateDanfePdf {

    public byte[] gerarDanfePdf(Document xmlDocument) throws JRException, FileNotFoundException {
        try {
            Map<String, Object> parameters = new HashMap<>();

            InputStream inputStream = getClass().getResourceAsStream("/jasper_nfe/danfe.jrxml");
            InputStream subReport = getClass().getResourceAsStream("/jasper_nfe/danfe_fatura.jrxml");
            InputStream logo = getClass().getResourceAsStream("/img/nfe.png");

            String pathExpression = "/nfeProc/NFe/infNFe/det";
            parameters.put("Logo", logo);
            parameters.put("SUBREPORT", subReport);
            parameters.put("XML_DATA_DOCUMENT", xmlDocument);

            if (inputStream == null) {
                throw new FileNotFoundException("Arquivo danfe.jrxml não encontrado.");
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            JRDataSource xmlDatasource = new JRXmlDataSource(xmlDocument, pathExpression);

            // Onde passa as variáveis para criar o relatório
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, xmlDatasource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (JRException e) {
            e.printStackTrace();
            return null;
        }
    }
}
