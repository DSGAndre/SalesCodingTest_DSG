package com.example.sales.domain.rule;

import java.math.BigDecimal;

public class BasicSaleTaxRule extends TaxRule {
    private static final BigDecimal BASIC_TAX_PERCENT = BigDecimal.valueOf(10);

    public BasicSaleTaxRule() {
        super(BASIC_TAX_PERCENT);
    }
}
