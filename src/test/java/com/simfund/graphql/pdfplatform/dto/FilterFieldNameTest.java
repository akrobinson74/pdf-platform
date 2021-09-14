package com.simfund.graphql.pdfplatform.dto;

import static com.simfund.graphql.pdfplatform.dto.FilterFieldName.INPUT_FILE_NAME;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class FilterFieldNameTest {
  @ParameterizedTest
  @EnumSource(FilterFieldName.class)
  void Test_toCamelCase(FilterFieldName filterFieldName) {
    final var camelCasedCountryCode = filterFieldName.toCamelCase();
    Assertions.assertEquals(buildCamelCase(filterFieldName), camelCasedCountryCode);
  }

  @ParameterizedTest
  @EnumSource(FilterFieldName.class)
  void Test_stringification(FilterFieldName filterFieldName) {
    Assertions.assertEquals(
        filterFieldName.getStringification(),
        filterFieldName.toCamelCase(),
        "stringification and toCamelCase yield the same value");
  }

  private String buildCamelCase(FilterFieldName filterFieldName) {
    final var amendedFieldName =
        filterFieldName.equals(INPUT_FILE_NAME) ? "INPUT_FILENAME" : filterFieldName.name();
    final var nameParts = amendedFieldName.toLowerCase().split("_");
    var result = new StringBuilder();
    for (int i = 0; i < nameParts.length; i++) {
      if (i == 0) {
        result.append(nameParts[0]);
      } else {
        result.append(ucFirst(nameParts[i]));
      }
    }
    return result.toString();
  }

  private String ucFirst(final String word) {
    return word.substring(0, 1).toUpperCase() + word.substring(1);
  }
}
