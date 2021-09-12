package com.simfund.graphql.pdfplatform.resolver;

import com.simfund.graphql.pdfplatform.dto.FilterField;
import com.simfund.graphql.pdfplatform.model.PdfRecord;
import com.simfund.graphql.pdfplatform.repository.PgRepository;
import graphql.com.google.common.collect.Lists;
import graphql.kickstart.tools.GraphQLQueryResolver;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PdfQueryResolver implements GraphQLQueryResolver {

  private final PgRepository pgRepository;

  public PdfQueryResolver(@Autowired PgRepository pgRepository) {
    this.pgRepository = pgRepository;
  }

  @SuppressWarnings("unused")
  public List<PdfRecord> pdfs() {
    return Lists.newArrayList(pgRepository.findAll());
  }

  @SuppressWarnings("unused")
  public Optional<PdfRecord> getPdf(Long id) {
    return pgRepository.findById(id);
  }

  @SuppressWarnings("unused")
  public List<PdfRecord> pdfsWithFilter(List<FilterField> filterFieldList) {
    Specification<PdfRecord> querySpec = null;
    for (FilterField filterField : filterFieldList) {
      querySpec =
          querySpec != null ? querySpec.and(applyFilter(filterField)) : applyFilter(filterField);
    }
    return querySpec != null ? pgRepository.findAll(querySpec)
        : Lists.newArrayList(pgRepository.findAll().iterator());
  }

  private Specification<PdfRecord> applyFilter(FilterField filter) {
    return (root, query, builder) -> {
      final var attributeName = filter.fieldName().toCamelCase();
      return filter.generateCriteria(builder, root.get(attributeName));
    };
  }
}
