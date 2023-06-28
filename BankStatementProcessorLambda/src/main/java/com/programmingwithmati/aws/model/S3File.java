package com.programmingwithmati.aws.model;

import java.util.List;
import java.util.Objects;

public final class S3File {
  private final String bucket;
  private final String s3Key;
  private final List<String> fileLines;

  public S3File(String bucket,
                String s3Key,
                List<String> lines) {
    this.bucket = bucket;
    this.s3Key = s3Key;
    this.fileLines = lines;
  }

  public String bucket() {
    return bucket;
  }

  public String s3Key() {
    return s3Key;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (S3File) obj;
    return Objects.equals(this.bucket, that.bucket) &&
            Objects.equals(this.s3Key, that.s3Key);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bucket, s3Key);
  }

  @Override
  public String toString() {
    return "S3File[" +
            "bucket=" + bucket + ", " +
            "s3Key=" + s3Key + ']';
  }

  public List<String> fileLines() {
    return fileLines;
  }
}
