package com.example.sales.domain.utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class RoundingUtilsTest {

    @Test
    void emptyResult_caseNullPrice() {
        // When
        final Optional<BigDecimal> productAmountTaxOptional = RoundingUtils.getRoundedUpToNearestFiveCents(null);

        // Then
        assertThat(productAmountTaxOptional).isEmpty();
    }

    @Test
    void emptyResult_caseRoundedNotNeeded() {
        // When
        final BigDecimal priceGiven = BigDecimal.valueOf(2.35);

        // When
        final Optional<BigDecimal> productAmountTaxOptional = RoundingUtils.getRoundedUpToNearestFiveCents(priceGiven);

        // Then
        assertThat(productAmountTaxOptional).isNotEmpty();
        assertThat(productAmountTaxOptional.get()).isEqualByComparingTo("2.35");
    }

    @Test
    void emptyResult_caseRoundedNearestFiveCents() {
        // When
        final BigDecimal priceGiven = BigDecimal.valueOf(2.28);

        // When
        final Optional<BigDecimal> productAmountTaxOptional = RoundingUtils.getRoundedUpToNearestFiveCents(priceGiven);

        // Then
        assertThat(productAmountTaxOptional).isNotEmpty();
        assertThat(productAmountTaxOptional.get()).isEqualByComparingTo("2.30");
    }

    @Test
    void emptyResult_caseChangeUnit() {
        // When
        final BigDecimal priceGiven = BigDecimal.valueOf(2.997);

        // When
        final Optional<BigDecimal> productAmountTaxOptional = RoundingUtils.getRoundedUpToNearestFiveCents(priceGiven);

        // Then
        assertThat(productAmountTaxOptional).isNotEmpty();
        assertThat(productAmountTaxOptional.get()).isEqualByComparingTo("3.00");
    }
}
