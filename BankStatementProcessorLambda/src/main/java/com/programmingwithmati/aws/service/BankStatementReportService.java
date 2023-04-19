package com.programmingwithmati.aws.service;

import com.programmingwithmati.aws.model.BankStatement;
import com.programmingwithmati.aws.model.BankStatementLine;
import com.programmingwithmati.aws.model.BankStatementReportLine;
import com.programmingwithmati.aws.model.TransactionCategory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BankStatementReportService {

  public List<BankStatementReportLine> generateBankStatementReport(BankStatement bankStatement) {
    return groupLinesByCategory(bankStatement)
            .entrySet()
            .stream()
            .map(this::toBankStatementReportLine)
            .toList();
  }

  private Map<TransactionCategory, List<BankStatementLine>> groupLinesByCategory(BankStatement bankStatement) {
    return bankStatement.lines()
            .stream()
            .collect(Collectors.groupingBy(BankStatementLine::category));
  }

  private BankStatementReportLine toBankStatementReportLine(Map.Entry<TransactionCategory, List<BankStatementLine>> entry) {
    return new BankStatementReportLine(
            entry.getKey(),
            sumAllAmountsInCategory(entry.getValue())
    );
  }

  private BigDecimal sumAllAmountsInCategory(List<BankStatementLine> lines) {
    return lines.stream()
            .map(BankStatementLine::amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
