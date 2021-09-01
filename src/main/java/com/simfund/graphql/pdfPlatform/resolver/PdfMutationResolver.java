package com.simfund.graphql.pdfPlatform.resolver;

import com.simfund.graphql.pdfPlatform.dto.PdfMetadata;
import com.simfund.graphql.pdfPlatform.dto.ReportType;
import com.simfund.graphql.pdfPlatform.dto.UploadInput;
import com.simfund.graphql.pdfPlatform.model.PdfRecord;
import com.simfund.graphql.pdfPlatform.repository.PgRepository;
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class PdfMutationResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(PdfMutationResolver.class);
    private static final String BASE_DIR = "/opt/pdf/data";

    @Autowired
    private PgRepository pgRepository;

    public List<PdfRecord> uploadPdfs(DataFetchingEnvironment dfe) {
        return storeAndSavePdf(dfe.getContext(), null);
    }

    public List<PdfRecord> uploadPdfsWithMetadata(
            UploadInput uploadMetadata,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        return storeAndSavePdf(dataFetchingEnvironment.getContext(), uploadMetadata);
    }

    private List<PdfRecord> storeAndSavePdf(
            DefaultGraphQLServletContext context,
            UploadInput uploadMetadata
    ) {
        List<PdfRecord> pdfRecordList = new ArrayList<PdfRecord>();
        AtomicInteger index = new AtomicInteger(0);
        
        context.getFileParts()
                .stream()
                .map(Part::getSubmittedFileName)
                .map(fileName -> getMetadataOrParseFilename(fileName, uploadMetadata.getMeta().get(index.get())))
                .forEach(metadata -> storeAndSaveMetadata(pdfRecordList, index, metadata, pgRepository));

        return pdfRecordList;
    }

    private static void storeAndSaveMetadata(
            List<PdfRecord> pdfRecordList,
            AtomicInteger index,
            PdfMetadata metadata,
            PgRepository pgRepository
    ) {
        val reportName = metadata.getReportName();
        val newDirPath = String.join(File.separator,
                Arrays.asList(BASE_DIR,
                        metadata.getClientName(),
                        metadata.getCountryCode(),
                        metadata.getReportType().name())
        );

        try {
            Files.createDirectories(Paths.get(newDirPath));
            val copyPath =
                    Paths.get(String.join(File.separator, newDirPath, StringUtils.cleanPath("$reportName.pdf")));

            val pdfRecord = PdfRecord.buildPdfRecord(metadata);

            pdfRecordList.add(pgRepository.save(pdfRecord));
            index.incrementAndGet();
        } catch (IOException e) {
            LOGGER.error("Unable to save .pdf file {}: {}", "$reportName.pdf", e.getLocalizedMessage());
        }
    }

    private PdfMetadata getMetadataOrParseFilename(String fileName, PdfMetadata metaData) {
        PdfMetadata newMetadata = null;
        if (metaData != null) {
            newMetadata = metaData.toBuilder().inputFilename(fileName).build();
        } else {
            newMetadata = parseFilename(fileName);
        }
        return newMetadata;
    }

    private PdfMetadata parseFilename(String fileName) {
        val nameParts = fileName.split(".");
        return PdfMetadata.builder()
                .clientName(nameParts[0])
                .countryCode(nameParts[1])
                .inputFilename(fileName)
                .reportName(nameParts[2])
                .reportType(ReportType.valueOf(nameParts[3]))
                .build();
    }
}
