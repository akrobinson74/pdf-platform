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
  //  private static final CriteriaBuilder CRITERIA_BUILDER = new
  // EntityManager().getCriteriaBuilder();
  //  private static final CriteriaQuery<PdfRecord> CRITERIA_QUERY = CriteriaQuery

  @ParameterizedTest
  @MethodSource("generateCriteriaSources")
  void Test_generateCriteria(CriteriaBuilder criteriaBuilder, Path<?> path, Predicate expected) {
    String fred = null;
    assertThat(fred).isNull();
  }

  private static Stream<Arguments> generateCriteriaSources() {
    //    (Specification<PdfRecord>)(root, query, builder) -> {
    //      return Stream.of(
    //          Arguments.of(builder, root.get("clientName"), builder.equals()),
    //          Arguments.of()
    //      );
    //    };
    return null;
  }
}
