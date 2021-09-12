package com.simfund.graphql.pdfplatform.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FilterFieldNameTest {
    @Test
    void Test_toCamelCase() {
        final var countryCode = FilterFieldName.COUNTRY_CODE;
        final var camelCasedCountryCode = countryCode.toCamelCase();
        Assertions.assertEquals("countryCode", camelCasedCountryCode);
    }
}
