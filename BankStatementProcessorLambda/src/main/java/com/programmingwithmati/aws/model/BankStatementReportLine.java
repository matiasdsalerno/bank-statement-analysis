package com.programmingwithmati.aws.model;

import java.math.BigDecimal;

public record BankStatementReportLine(
        TransactionCategory category,
        BigDecimal amount
) {
}
