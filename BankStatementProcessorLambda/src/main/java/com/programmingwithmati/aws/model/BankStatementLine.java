package com.programmingwithmati.aws.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class BankStatementLine {
  private final String transactionId;
  private final TransactionCategory category;
  private final String business;
  private final String currency;
  private final BigDecimal amount;

  public BankStatementLine(
          String transactionId,
          TransactionCategory category,
          String business,
          String currency,
          BigDecimal amount
  ) {
    this.transactionId = transactionId;
    this.category = category;
    this.business = business;
    this.currency = currency;
    this.amount = amount;
  }

  public String transactionId() {
    return transactionId;
  }

  public TransactionCategory category() {
    return category;
  }

  public String business() {
    return business;
  }

  public String currency() {
    return currency;
  }

  public BigDecimal amount() {
    return amount;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (BankStatementLine) obj;
    return Objects.equals(this.transactionId, that.transactionId) &&
            Objects.equals(this.category, that.category) &&
            Objects.equals(this.business, that.business) &&
            Objects.equals(this.currency, that.currency) &&
            Objects.equals(this.amount, that.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, category, business, currency, amount);
  }

  @Override
  public String toString() {
    return "BankStatementLine[" +
            "transactionId=" + transactionId + ", " +
            "category=" + category + ", " +
            "business=" + business + ", " +
            "currency=" + currency + ", " +
            "amount=" + amount + ']';
  }

}
