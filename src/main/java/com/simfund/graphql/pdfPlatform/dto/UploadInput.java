package com.simfund.graphql.pdfPlatform.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UploadInput {
    private List<PdfMetadata> meta;
}
