package com.programmingwithmati.aws.converter;

import com.programmingwithmati.aws.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class BankStatementConverter {

  public BankStatement toBankStatement(S3File s3File) {
    return new BankStatement(
            s3File.s3Key(),
            s3File.bucket(),
            s3File.fileLines()
                    .stream()
                    .map(this::toBankStatementLine)
                    .collect(Collectors.toList()));
  }
  private BankStatementLine toBankStatementLine(String line) {
    var csvLine = line.split(";");
    return new BankStatementLine(
            csvLine[0],
            TransactionCategory.valueOf(csvLine[1]),
            csvLine[2],
            csvLine[3],
            new BigDecimal(csvLine[4])
    );
  }

  S3File reportToS3File(List<BankStatementReportLine> reportLines, String bucket, String s3Key) {
    var fileLines = reportLines.stream().map(this::lineToFileLine).collect(Collectors.toList());
    return new S3File(bucket, s3Key, fileLines);
  }

  private String lineToFileLine(BankStatementReportLine reportLine) {
    return String.format("%s;%s", reportLine.category().toString(), reportLine.amount().toString());
  }
}
