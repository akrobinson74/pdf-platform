package com.simfund.graphql.pdfplatform.dto;

public enum FilterFieldName {
  CLIENT_NAME("clientName"),
  COUNTRY_CODE("countryCode"),
  INPUT_FILE_NAME("inputFilename"),
  REPORT_NAME("reportName"),
  REPORT_TYPE("reportType");

  private final String stringification;

  FilterFieldName(String stringification) {
    this.stringification = stringification;
  }

  public String toCamelCase() {
    return this.stringification;
  }

  @Override
  public String toString() {
    return "FilterFieldName{" +
        "stringification='" + stringification + '\'' +
        '}';
  }

  public String getStringification() {
    return stringification;
  }
}
