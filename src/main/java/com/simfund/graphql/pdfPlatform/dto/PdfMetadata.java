package com.simfund.graphql.pdfPlatform.dto;

import java.util.Map;

public record PdfMetadata(
        String clientName,
        String countryCode,
        String inputFilename,
        String reportName,
        ReportType reportType) {

    public static PdfMetadata clone(PdfMetadata parent, Map<String, String> overrides) {
        return new PdfMetadata(
                overrides.containsKey("clientName") ? overrides.get("clientName") : parent.clientName(),
                overrides.containsKey("countryCode") ? overrides.get("countryCode") : parent.countryCode(),
                overrides.containsKey("inputFilename") ? overrides.get("inputFilename") : parent.inputFilename(),
                overrides.containsKey("reportName") ? overrides.get("reportName") : parent.reportName(),
                overrides.containsKey("reportType") ? ReportType.valueOf(overrides.get("countryCode")) : parent.reportType()
        );
    }
}
