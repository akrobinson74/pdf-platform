package com.simfund.graphql.pdfPlatform.resolver;

import com.simfund.graphql.pdfPlatform.dto.PdfMetadata;
import com.simfund.graphql.pdfPlatform.dto.ReportType;
import com.simfund.graphql.pdfPlatform.dto.UploadInput;
import com.simfund.graphql.pdfPlatform.model.PdfRecord;
import com.simfund.graphql.pdfPlatform.repository.PgRepository;
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
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
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class PdfMutationResolver implements GraphQLMutationResolver {
    private static final Logger log = LoggerFactory.getLogger(PdfMutationResolver.class);
    private static final String BASE_DIR = "/opt/pdf/data";

    private final PgRepository pgRepository;

    public PdfMutationResolver(@Autowired PgRepository pgRepository) {
        this.pgRepository = pgRepository;
    }

    @SuppressWarnings("unused")
    public List<PdfRecord> uploadPdfs(DataFetchingEnvironment dfe) {
        return storeAndSavePdf(dfe.getContext(), null);
    }

    @SuppressWarnings("unused")
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
        List<PdfRecord> pdfRecordList = new ArrayList<>();
        AtomicInteger index = new AtomicInteger(0);
        context.getFileParts()
                .forEach(part -> {
                    final var fileName = part.getSubmittedFileName();
                    final var pdfMetadata = getMetadataOrParseFilename(
                            fileName, uploadMetadata == null ? null : uploadMetadata.meta().get(index.get())
                    );

                    storeAndSaveMetadata(pdfRecordList, part, pdfMetadata, pgRepository);
                    index.incrementAndGet();
                });

        return pdfRecordList;
    }

    private static void storeAndSaveMetadata(
            List<PdfRecord> pdfRecordList,
            Part filePart,
            PdfMetadata metadata,
            PgRepository pgRepository
    ) {
        final var fileName = metadata.inputFilename();
        final var reportName = metadata.reportName();
        final var newDirPath = String.join(File.separator,
                Arrays.asList(BASE_DIR,
                        metadata.clientName(),
                        metadata.countryCode(),
                        metadata.reportType().name())
        );
        final var newFilename = String.format("%s.pdf", reportName);

        try {
            Files.createDirectories(Paths.get(newDirPath));
            final var copyPath = Paths.get(String.join(File.separator, newDirPath, StringUtils.cleanPath(newFilename)));

            log.info("Copying inputStream named {} to: {} ({} bytes)", fileName, copyPath, filePart.getSize());
            Files.copy(filePart.getInputStream(), copyPath, StandardCopyOption.REPLACE_EXISTING);

            final var pdfRecord = PdfRecord.buildPdfRecord(metadata);

            log.debug("Saving .pdf metaData for file: {}", fileName);
            pdfRecordList.add(pgRepository.save(pdfRecord));
        } catch (IOException e) {
            log.error("Unable to save .pdf file {}: {}", newFilename, e.getLocalizedMessage());
        }
    }

    private PdfMetadata getMetadataOrParseFilename(String fileName, PdfMetadata metaData) {
        PdfMetadata newMetadata;
        if (metaData != null) {
            newMetadata = PdfMetadata.clone(metaData, Map.of("inputFilename", fileName));
        } else {
            newMetadata = parseFilename(fileName);
        }
        return newMetadata;
    }

    private PdfMetadata parseFilename(String fileName) {
        final var nameParts = fileName.split("\\.");
        return new PdfMetadata(nameParts[0], nameParts[1], fileName, nameParts[2], ReportType.valueOf(nameParts[3]));
    }
}
