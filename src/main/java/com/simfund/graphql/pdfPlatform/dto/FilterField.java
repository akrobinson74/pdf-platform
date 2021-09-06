package com.simfund.graphql.pdfPlatform.dto;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

@Slf4j
public record FilterField(FilterFieldName fieldName, FilterOperator operator, String value) {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterField.class);

    @SuppressWarnings("unchecked")
    public Predicate generateCriteria(CriteriaBuilder builder, Path objectPath) {
        Predicate predicate = null;
        try {

            switch (operator) {
                case EQ:
                    predicate = builder.equal(objectPath, Integer.valueOf(value));
                case GE:
                    predicate = builder.ge(objectPath, Integer.valueOf(value));
                case GT:
                    predicate = builder.gt(objectPath, Integer.valueOf(value));
                case LE:
                    predicate = builder.le(objectPath, Integer.valueOf(value));
                    break;
                case LT:
                    predicate = builder.lt(objectPath, Integer.valueOf(value));
                    break;
                case CONTAINS:
                    predicate = builder.like(objectPath, "%" + value + "%");
                    break;
                case ENDS_WITH:
                    predicate = builder.like(objectPath, "%" + value);
                    break;
                case EQUALS:
                    predicate = fieldName == FilterFieldName.REPORT_TYPE ?
                            builder.equal(objectPath, ReportType.valueOf(value)) :
                            builder.equal(objectPath, value);
                    break;
                case STARTS_WITH:
                    predicate = builder.like(objectPath, value + "%");
                    break;
            }
        } catch (Throwable e) {
            LOGGER.error("Unable to generate a predicate for field {} with operator {} and value {}: {}",
                    fieldName.name(),
                    operator.name(),
                    value,
                    e.getLocalizedMessage()
            );
        }
        return predicate;
    }
}
