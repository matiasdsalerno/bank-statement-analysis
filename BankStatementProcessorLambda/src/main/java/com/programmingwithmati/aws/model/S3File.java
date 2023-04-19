package com.programmingwithmati.aws.model;

import java.util.List;

public record S3File(String bucket,
                     String s3Key,
                     List<String> fileLines) {
}
