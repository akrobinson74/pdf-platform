package com.simfund.graphql.pdfPlatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfMetadata {
    @Getter
    private String clientName;

    @Getter
    private String countryCode;

    @Getter
    private String inputFilename;

    @Getter
    private String reportName;

    @Getter
    private ReportType reportType;
}
