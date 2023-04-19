package com.programmingwithmati.aws.model;

import java.math.BigDecimal;

public record BankStatementLine(
        String transactionId,
        TransactionCategory category,
        String business,
        String currency,
        BigDecimal amount
) {
}
