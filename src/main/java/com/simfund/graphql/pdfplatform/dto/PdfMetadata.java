package com.simfund.graphql.pdfplatform.dto;

import java.util.Map;

public record PdfMetadata(
    String clientName,
    String countryCode,
    String inputFilename,
    String reportName,
    ReportType reportType) {

  public static final String CLIENT_NAME = FilterFieldName.CLIENT_NAME.getStringification();
  public static final String COUNTRY_CODE = FilterFieldName.COUNTRY_CODE.getStringification();
  public static final String INPUT_FILENAME = FilterFieldName.INPUT_FILE_NAME.getStringification();
  public static final String REPORT_NAME = FilterFieldName.REPORT_NAME.getStringification();
  public static final String REPORT_TYPE = FilterFieldName.REPORT_TYPE.getStringification();

  public static PdfMetadata clone(PdfMetadata parent, Map<String, String> overrides) {
    return new PdfMetadata(
        overrides.containsKey(CLIENT_NAME) ? overrides.get(CLIENT_NAME) : parent.clientName(),
        overrides.containsKey(COUNTRY_CODE) ? overrides.get(COUNTRY_CODE) : parent.countryCode(),
        overrides.containsKey(INPUT_FILENAME) ? overrides.get(INPUT_FILENAME) :
            parent.inputFilename(),
        overrides.containsKey(REPORT_NAME) ? overrides.get(REPORT_NAME) : parent.reportName(),
        overrides.containsKey(REPORT_TYPE) ? ReportType.valueOf(overrides.get(REPORT_TYPE))
            : parent.reportType()
    );
  }
}
