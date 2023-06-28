package com.programmingwithmati.aws.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class BankStatementReportLine {
  private final TransactionCategory category;
  private final BigDecimal amount;

  public BankStatementReportLine(
          TransactionCategory category,
          BigDecimal amount
  ) {
    this.category = category;
    this.amount = amount;
  }

  public TransactionCategory category() {
    return category;
  }

  public BigDecimal amount() {
    return amount;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (BankStatementReportLine) obj;
    return Objects.equals(this.category, that.category) &&
            Objects.equals(this.amount, that.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(category, amount);
  }

  @Override
  public String toString() {
    return "BankStatementReportLine[" +
            "category=" + category + ", " +
            "amount=" + amount + ']';
  }

}
