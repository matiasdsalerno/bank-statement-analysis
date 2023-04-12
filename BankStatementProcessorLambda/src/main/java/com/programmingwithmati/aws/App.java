package com.programmingwithmati.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<S3Event, Void> {


    private final AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
    private static final String BANK_STATEMENT_REPORTS_BUCKET_NAME = "bank-statement-reports";

    public Void handleRequest(final S3Event input, final Context context) {
        context.getLogger().log("New S3 event. Stream version. Records size: " + input.getRecords().size());

        input.getRecords().stream()
                .peek(record -> context.getLogger().log("Peeking record. Record Name: " + record.getS3().getObject().getKey() + ". S3 bucket: " + record.getS3().getBucket().getName()) )
                .forEach( record -> {
                    String s3Key = record.getS3().getObject().getKey();
                    String sourceBucket = record.getS3().getBucket().getName();

                    context.getLogger().log("Record Name: " + s3Key + ". S3 bucket: " + sourceBucket);

                    GetObjectRequest getObjectRequest = new GetObjectRequest(sourceBucket, s3Key);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(s3.getObject(getObjectRequest).getObjectContent()));
                    String fileContent = reader.lines().collect(Collectors.joining("\n"));

                    ObjectMetadata metadata = new ObjectMetadata();
                    metadata.setContentLength(fileContent.length());

                    PutObjectRequest putObjectRequest = new PutObjectRequest(BANK_STATEMENT_REPORTS_BUCKET_NAME, s3Key, new ByteArrayInputStream(fileContent.getBytes()), metadata);
                    var putObjectResult = s3.putObject(putObjectRequest);
                    context.getLogger().log("Result of object save  was: " + putObjectResult.getContentMd5() + ". Version Id: " + putObjectResult.getVersionId());
                });

        return null;
    }
}
