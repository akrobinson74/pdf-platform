package com.simfund.graphql.pdfPlatform.dto;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FilterFieldNameTest {
    @Test
    public void Test_toCamelCase() {
        val countryCode = FilterFieldName.COUNTRY_CODE;
        val camelCasedCountryCode = countryCode.toCamelCase();
        Assertions.assertEquals("countryCode", camelCasedCountryCode);
    }
}
