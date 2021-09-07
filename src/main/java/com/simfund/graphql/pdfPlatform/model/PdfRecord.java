package com.simfund.graphql.pdfPlatform.model;

import com.simfund.graphql.pdfPlatform.dto.PdfMetadata;
import com.simfund.graphql.pdfPlatform.dto.ReportType;
import lombok.*;

import javax.persistence.*;

@Builder(setterPrefix = "set", toBuilder=true)
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter(value = AccessLevel.PACKAGE)
@Table(name = "pdf_platform")
@ToString
public class PdfRecord {
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    private String clientName;

    private String countryCode;

    @Column(name = "input_file_name")
    private String inputFilename;

    private String reportName;

    @Enumerated(value = EnumType.STRING)
    private ReportType reportType;

    public static PdfRecord buildPdfRecord(PdfMetadata pdfMetadata) {
        return PdfRecord.builder()
                .setClientName(pdfMetadata.clientName())
                .setCountryCode(pdfMetadata.countryCode())
                .setId(-1L)
                .setInputFilename(pdfMetadata.inputFilename())
                .setReportName(pdfMetadata.reportName())
                .setReportType(pdfMetadata.reportType())
                .build();
    }
}
