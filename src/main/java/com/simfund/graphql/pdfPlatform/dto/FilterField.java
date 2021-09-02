package com.simfund.graphql.pdfPlatform.dto;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

@Data
public class FilterField {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterField.class);
    private FilterFieldName fieldName;
    private FilterOperator operator;
    private String value;

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
                case LT:
                    predicate = builder.lt(objectPath, Integer.valueOf(value));
                case CONTAINS:
                    predicate = builder.like(objectPath, "%" + value + "%");
                case ENDS_WITH:
                    predicate = builder.like(objectPath, "%" + value);
                case EQUALS:
                    predicate = fieldName == FilterFieldName.REPORT_TYPE ?
                            builder.equal(objectPath, ReportType.valueOf(value)) :
                            builder.equal(objectPath, value);
                case STARTS_WITH:
                    predicate = builder.like(objectPath, value + "%");
            }
        }
        catch (Throwable e) {
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
