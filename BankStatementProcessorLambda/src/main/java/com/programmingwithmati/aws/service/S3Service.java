package com.programmingwithmati.aws.service;

import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.programmingwithmati.aws.model.S3File;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

public class S3Service {


  private final AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
  private static final String BANK_STATEMENT_REPORTS_BUCKET_NAME = "bank-statement-reports";

  public S3File getS3File(S3EventNotification.S3EventNotificationRecord record) {
    var s3Key = record.getS3().getObject().getKey();
    var sourceBucket = record.getS3().getBucket().getName();
    var getObjectRequest = new GetObjectRequest(sourceBucket, s3Key);
    var reader = new BufferedReader(new InputStreamReader(s3.getObject(getObjectRequest).getObjectContent()));
    var fileLines = reader
            .lines();
    return new S3File(sourceBucket, s3Key, fileLines);
  }

  public void saveFileToS3(String fileContent) {
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(fileContent.length());
    PutObjectRequest putObjectRequest = new PutObjectRequest(BANK_STATEMENT_REPORTS_BUCKET_NAME, "report-1.csv", new ByteArrayInputStream(fileContent.getBytes()), metadata);
    s3.putObject(putObjectRequest);
  }
}
