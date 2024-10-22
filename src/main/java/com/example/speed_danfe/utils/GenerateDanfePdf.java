package com.example.speed_danfe.utils;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.*;

import org.w3c.dom.Document;

public class GenerateDanfePdf {

    public byte[] gerarDanfePdf(Document xmlDocument) throws JRException {

        Map<String, Object> parameters = new HashMap<>();

        JasperReport jasperReport = JasperCompileManager.compileReport("/resources/jasper_nfe/danfe.jrxml");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
