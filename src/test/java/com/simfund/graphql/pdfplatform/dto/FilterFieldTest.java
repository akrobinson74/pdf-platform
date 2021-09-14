package com.simfund.graphql.pdfplatform.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FilterFieldTest {
  
  @ParameterizedTest
  @MethodSource("generateCriteriaSources")
  void Test_generateCriteria(CriteriaBuilder criteriaBuilder, Path<?> path, Predicate expected) {
    assertThat(criteriaBuilder).isNull();
    assertThat(path).isNull();
    assertThat(expected).isNull();
  }

  private static Stream<Arguments> generateCriteriaSources() {
    return null;
  }
}
