package com.simfund.graphql.pdfPlatform.dto;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum FilterFieldName {
    CLIENT_NAME,
    COUNTRY_CODE,
    INPUT_FILE_NAME,
    REPORT_NAME,
    REPORT_TYPE;

    public String toCamelCase() {
        String[] tokenArray = this.name().toLowerCase().split("_");
        return tokenArray[0] +
                IntStream.range(1, tokenArray.length)
                        .mapToObj(i -> tokenArray[i].substring(0, 0) + tokenArray[i].substring(1))
                        .collect(Collectors.joining());
    }
}
