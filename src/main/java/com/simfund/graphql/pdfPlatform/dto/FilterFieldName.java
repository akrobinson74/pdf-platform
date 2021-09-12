package com.simfund.graphql.pdfPlatform.dto;

public enum FilterFieldName {
    CLIENT_NAME("clientName"),
    COUNTRY_CODE("countryCode"),
    INPUT_FILE_NAME("inputFilename"),
    REPORT_NAME("reportName"),
    REPORT_TYPE("reportType");

    private String stringification;

    FilterFieldName(String stringification) {
        this.stringification = stringification;
    }

    public String toCamelCase() {
        return this.stringification;
    }
}
