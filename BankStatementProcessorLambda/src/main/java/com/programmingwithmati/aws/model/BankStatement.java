package com.programmingwithmati.aws.model;

import java.util.List;
import java.util.Objects;

public final class BankStatement {
  private final String fileName;
  private final String sourceBucket;

  private final List<BankStatementLine> lines;

  public BankStatement(
          String fileName,
          String sourceBucket,
          List<BankStatementLine> line) {
    this.fileName = fileName;
    this.sourceBucket = sourceBucket;
    this.lines = line;
  }

  public String fileName() {
    return fileName;
  }

  public String sourceBucket() {
    return sourceBucket;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (BankStatement) obj;
    return Objects.equals(this.fileName, that.fileName) &&
            Objects.equals(this.sourceBucket, that.sourceBucket);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fileName, sourceBucket);
  }

  @Override
  public String toString() {
    return "BankStatement[" +
            "fileName=" + fileName + ", " +
            "sourceBucket=" + sourceBucket + ']';
  }

  public List<BankStatementLine> lines() {
    return lines;
  }
}
