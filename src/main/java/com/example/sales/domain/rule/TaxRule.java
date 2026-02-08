package com.example.sales.domain.rule;

import com.example.sales.domain.model.Product;
import com.example.sales.domain.utils.RoundingUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public abstract class TaxRule {
    private static final BigDecimal PERCENT_DIVISOR = BigDecimal.valueOf(100);
    private static final int TAX_RATE_SCALE = 4;

    private final BigDecimal taxRate;

    public TaxRule(BigDecimal taxPercent) {
        this.taxRate = taxPercent.divide(PERCENT_DIVISOR, TAX_RATE_SCALE, RoundingMode.HALF_UP);
    }

    public Optional<BigDecimal> getProductAmountTax(final Product product) {
        if (product == null) {
            return Optional.empty();
        }

        final BigDecimal taxAmount = product.price().multiply(taxRate);
        final BigDecimal taxAmountWithQuantity = taxAmount.multiply(BigDecimal.valueOf(product.quantity()));

        return RoundingUtils.getRoundedUpToNearestFiveCents(taxAmountWithQuantity);
    }
}
