package com.simfund.graphql.pdfPlatform.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FilterFieldNameTest {
    @Test
    public void Test_toCamelCase() {
        final var countryCode = FilterFieldName.COUNTRY_CODE;
        final var camelCasedCountryCode = countryCode.toCamelCase();
        Assertions.assertEquals("countryCode", camelCasedCountryCode);
    }
}
