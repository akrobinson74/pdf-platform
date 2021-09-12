package com.simfund.graphql.pdfplatform.model;

import com.simfund.graphql.pdfplatform.dto.PdfMetadata;
import com.simfund.graphql.pdfplatform.dto.ReportType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "pdf_platform")
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

  public PdfRecord() {
    this(null, "", "", "", "", ReportType.PLATFORMS);
  }

  public PdfRecord(Long id, String clientName, String countryCode, String inputFilename,
      String reportName, ReportType reportType) {
    this.id = id;
    this.clientName = clientName;
    this.countryCode = countryCode;
    this.inputFilename = inputFilename;
    this.reportName = reportName;
    this.reportType = reportType;
  }

  public static PdfRecord buildPdfRecord(PdfMetadata pdfMetadata) {
    return new PdfRecord()
        .setClientName(pdfMetadata.clientName())
        .setCountryCode(pdfMetadata.countryCode())
        .setId(-1L)
        .setInputFilename(pdfMetadata.inputFilename())
        .setReportName(pdfMetadata.reportName())
        .setReportType(pdfMetadata.reportType());
  }

  public Long getId() {
    return id;
  }

  public ReportType getReportType() {
    return reportType;
  }

  public String getClientName() {
    return clientName;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public String getInputFilename() {
    return inputFilename;
  }

  public String getReportName() {
    return reportName;
  }

  protected PdfRecord setClientName(String clientName) {
    this.clientName = clientName;
    return this;
  }

  protected PdfRecord setCountryCode(String countryCode) {
    this.countryCode = countryCode;
    return this;
  }

  protected PdfRecord setId(Long id) {
    this.id = id;
    return this;
  }

  protected PdfRecord setInputFilename(String inputFilename) {
    this.inputFilename = inputFilename;
    return this;
  }

  protected PdfRecord setReportName(String reportName) {
    this.reportName = reportName;
    return this;
  }

  protected PdfRecord setReportType(ReportType reportType) {
    this.reportType = reportType;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    PdfRecord pdfRecord = (PdfRecord) o;

    return new EqualsBuilder()
        .append(id, pdfRecord.id)
        .append(clientName, pdfRecord.clientName)
        .append(countryCode, pdfRecord.countryCode)
        .append(inputFilename, pdfRecord.inputFilename)
        .append(reportName, pdfRecord.reportName)
        .append(reportType, pdfRecord.reportType)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(id)
        .append(clientName)
        .append(countryCode)
        .append(inputFilename)
        .append(reportName)
        .append(reportType)
        .toHashCode();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
