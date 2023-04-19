package com.programmingwithmati.aws.model;

import java.util.List;

public record BankStatement(
        String fileName,
        String sourceBucket,
        List<BankStatementLine> lines
) {
}
