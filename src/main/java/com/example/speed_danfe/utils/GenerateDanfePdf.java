package com.example.speed_danfe.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.w3c.dom.Document;

public class GenerateDanfePdf {

    public byte[] gerarDanfePdf(Document xmlDocument) throws JRException, FileNotFoundException {
        try {
            Map<String, Object> parameters = new HashMap<>();

            InputStream inputStream = getClass().getResourceAsStream("/jasper_nfe/danfe.jrxml");

            if (inputStream == null) {
                throw new FileNotFoundException("Arquivo danfe.jrxml não encontrado.");
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (JRException e) {
            e.printStackTrace();
            return null;
        }
    }
}
