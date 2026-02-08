package com.example.sales.domain.rule;

import com.example.sales.domain.model.Product;
import com.example.sales.domain.builder.ProductTestBuilder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class BasicSalesTaxRuleTest {

    private static final BasicSaleTaxRule BASIC_SALE_TAX_RULE = new BasicSaleTaxRule();

    @Test
    void emptyResult_caseNullProduct() {
        // When
        final Optional<BigDecimal> productAmountTaxOptional = BASIC_SALE_TAX_RULE.getProductAmountTax(null);

        // Then
        assertThat(productAmountTaxOptional).isEmpty();
    }

    @Test
    void emptyResult_caseNullPrice() {
        // Given
        final Product productWithoutPriceGiven = new ProductTestBuilder()
                .withoutPrice()
                .build();

        // When
        final Optional<BigDecimal> productAmountTaxOptional = BASIC_SALE_TAX_RULE.getProductAmountTax(productWithoutPriceGiven);

        // Then
        assertThat(productAmountTaxOptional).isEmpty();
    }

    @Test
    void taxCalculated_caseProductTaxable() {
        // Given
        final Product productGiven = new ProductTestBuilder().build();

        // When
        final Optional<BigDecimal> productAmountTaxOptional = BASIC_SALE_TAX_RULE.getProductAmountTax(productGiven);

        // Then
        assertThat(productAmountTaxOptional).isNotEmpty();
        assertThat(productAmountTaxOptional.get()).isEqualByComparingTo("1.00");
    }

    @Test
    void taxRoundedCalculated_caseProductTaxableRoundable() {
        // Given
        final Product productGiven = ProductTestBuilder.aMouseProduct();

        // When
        final Optional<BigDecimal> productAmountTaxOptional = BASIC_SALE_TAX_RULE.getProductAmountTax(productGiven);

        // Then
        assertThat(productAmountTaxOptional).isNotEmpty();
        assertThat(productAmountTaxOptional.get()).isEqualByComparingTo("2.35");
    }
}
