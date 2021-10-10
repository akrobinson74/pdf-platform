package com.simfund.graphql.pdfplatform.dto;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record FilterField(FilterFieldName fieldName, FilterOperator operator, String value) {

  private static final Logger log = LoggerFactory.getLogger(FilterField.class);

  @SuppressWarnings("unchecked")
  // Need to figure out how to specify data type coming through w/o warning
  public Predicate generateCriteria(CriteriaBuilder builder, Path<?> objectPath) {
    Predicate predicate = null;
    try {
      predicate = fieldName == FilterFieldName.REPORT_TYPE ?
          getReportTypePredicate(builder, (Path<String>) objectPath) :
          switch (operator) {
            case EQ -> builder.equal(objectPath, Integer.valueOf(value));
            case GE -> builder.ge((Path<Integer>) objectPath, Integer.valueOf(value));
            case GT -> builder.gt((Path<Integer>) objectPath, Integer.valueOf(value));
            case LE -> builder.le((Path<Integer>) objectPath, Integer.valueOf(value));
            case LT -> builder.lt((Path<Integer>) objectPath, Integer.valueOf(value));
            case CONTAINS -> builder.like((Path<String>) objectPath, "%" + value + "%");
            case ENDS_WITH -> builder.like((Path<String>) objectPath, "%" + value);
            case EQUALS -> builder.equal(objectPath, value);
            case STARTS_WITH -> builder.like((Path<String>) objectPath, value + "%");
          };

    } catch (Exception e) {
      log.error("Unable to generate a predicate for field {} with operator {} and value {}: {}",
          fieldName.name(),
          operator.name(),
          value,
          e.getLocalizedMessage()
      );
    }
    return predicate;
  }

  private Predicate getReportTypePredicate(CriteriaBuilder builder, Path<String> objectPath)
      throws IllegalStateException {
    return switch (operator) {
      case EQ, GE, GT, LE, LT ->
        throw new IllegalStateException("ReportType only supports string predicate operators!");
      case CONTAINS -> builder.like(objectPath.as(String.class), "%" + value + "%");
      case ENDS_WITH -> builder.like(objectPath.as(String.class), "%" + value);
      case EQUALS -> builder.equal(objectPath.as(String.class), value);
      case STARTS_WITH -> builder.like(objectPath.as(String.class), value + "%");
    };
  }
}
