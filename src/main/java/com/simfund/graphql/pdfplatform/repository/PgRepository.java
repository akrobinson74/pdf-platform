package com.simfund.graphql.pdfplatform.repository;

import com.simfund.graphql.pdfplatform.model.PdfRecord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PgRepository extends CrudRepository<PdfRecord, Long>,
    JpaSpecificationExecutor<PdfRecord> {

}
