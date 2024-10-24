package com.example.speed_danfe.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.util.JRLoader;
import org.w3c.dom.Document;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

public class GenerateDanfePdf {

    public byte[] gerarDanfePdf(Document xmlDocument) throws JRException, FileNotFoundException {
        try {
            Map<String, Object> parameters = new HashMap<>();

            InputStream inputStream = getClass().getResourceAsStream("/jasper_nfe/danfe.jrxml");
            InputStream subReport = getClass().getResourceAsStream("/jasper_nfe/danfe_fatura.jasper");
            JasperReport subJasperReport = (JasperReport) JRLoader.loadObject(subReport);
            InputStream logo = getClass().getResourceAsStream("/img/nfe.png");

            String pathExpression = "/nfeProc/NFe/infNFe/det";
            parameters.put("Logo", logo);
            parameters.put("SUBREPORT", subJasperReport);
            parameters.put("XML_DATA_DOCUMENT", xmlDocument);

            if (inputStream == null) {
                throw new FileNotFoundException("Arquivo danfe.jrxml não encontrado.");
            }
            System.out.println("Compilando report...");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            System.out.println("Compilado");

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
