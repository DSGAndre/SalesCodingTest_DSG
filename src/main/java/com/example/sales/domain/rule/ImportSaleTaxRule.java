package com.example.sales.domain.rule;

import java.math.BigDecimal;

public class ImportSaleTaxRule extends TaxRule {
    private static final BigDecimal IMPORT_TAX_PERCENT = BigDecimal.valueOf(5);

    public ImportSaleTaxRule() {
        super(IMPORT_TAX_PERCENT);
    }
}
