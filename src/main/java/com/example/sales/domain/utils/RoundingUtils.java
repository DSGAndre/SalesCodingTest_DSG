package com.example.sales.domain.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class RoundingUtils {

    final static BigDecimal FIVE_CENTS = new BigDecimal("0.05");

    public static Optional<BigDecimal> getRoundedUpToNearestFiveCents(final BigDecimal price) {
        if (price == null) {
            return Optional.empty();
        }

        final BigDecimal priceTaxed = price.divide(FIVE_CENTS, 0, RoundingMode.CEILING)
                .multiply(FIVE_CENTS);

        return Optional.of(priceTaxed);
    }
}
