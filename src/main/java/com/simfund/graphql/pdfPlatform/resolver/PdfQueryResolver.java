package com.simfund.graphql.pdfPlatform.resolver;

import com.simfund.graphql.pdfPlatform.dto.FilterField;
import com.simfund.graphql.pdfPlatform.model.PdfRecord;
import com.simfund.graphql.pdfPlatform.repository.PgRepository;
import graphql.com.google.common.collect.Lists;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Component
public class PdfQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private PgRepository pgRepository;

    public List<PdfRecord> pdfs() {
        return Lists.newArrayList(pgRepository.findAll());
    }

    public Optional<PdfRecord> getPdf(Long id) {
        return pgRepository.findById(id);
    }
    
    public List<PdfRecord> pdfsWithFilter(List<FilterField> filterFieldList) {
        Specification<PdfRecord> querySpec = null;
        for (FilterField filterField : filterFieldList) {
            querySpec = querySpec != null ? querySpec.and(applyFilter(filterField)) : applyFilter(filterField);
        }
        return querySpec != null ? pgRepository.findAll(querySpec) : Lists.newArrayList(pgRepository.findAll().iterator());
    }

    private Specification<PdfRecord> applyFilter(FilterField filter) {
        return (Specification<PdfRecord>) (root, query, builder) -> {
            String attributeName = filter.getFieldName().toCamelCase();
            return filter.generateCriteria(builder, root.get(attributeName));
        };
    }
}
