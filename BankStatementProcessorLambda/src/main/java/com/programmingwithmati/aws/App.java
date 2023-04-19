package com.programmingwithmati.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.programmingwithmati.aws.converter.BankStatementConverter;
import com.programmingwithmati.aws.model.*;
import com.programmingwithmati.aws.service.BankStatementReportService;
import com.programmingwithmati.aws.service.S3Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<S3Event, Void> {


    private final AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
    private static final String BANK_STATEMENT_REPORTS_BUCKET_NAME = "bank-statement-reports";
    private final BankStatementReportService bankStatementReportService = new BankStatementReportService();
    private final S3Service s3Service = new S3Service();
    private final BankStatementConverter bankStatementConverter = new BankStatementConverter();

    public Void handleRequest(final S3Event input, final Context context) {
        context.getLogger().log("New S3 event. Stream version. Records size: " + input.getRecords().size());

        input.getRecords().stream()
                .map( this::recordToBankStatement )
                .map( bankStatementReportService::generateBankStatementReport)
                .forEach( reportLines -> s3Service.saveFileToS3(generateFileContent(reportLines)));

        return null;
    }

  private String generateFileContent(List<BankStatementReportLine> reportLines) {
    return reportLines.stream().map(BankStatementReportLine::toString).collect(Collectors.joining("\n"));
  }

  private BankStatement recordToBankStatement(S3EventNotification.S3EventNotificationRecord record) {
    S3File s3File = s3Service.getS3File(record);

    return bankStatementConverter.toBankStatement(s3File);
  }

}
