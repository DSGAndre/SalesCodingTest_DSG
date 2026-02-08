package com.example.sales.domain.rule;

import com.example.sales.domain.builder.ProductTestBuilder;
import com.example.sales.domain.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ImportTaxRuleTest {

    private static final ImportSaleTaxRule IMPORT_SALE_TAX_RULE = new ImportSaleTaxRule();

    @Test
    void emptyResult_caseNullProduct() {
        // When
        final Optional<BigDecimal> productAmountTaxOptional = IMPORT_SALE_TAX_RULE.getProductAmountTax(null);

        // Then
        assertThat(productAmountTaxOptional).isEmpty();
    }

    @Test
    void taxCalculated_caseProductTaxable() {
        // Given
        final Product productImportedGiven = new ProductTestBuilder()
                .imported()
                .build();

        // When
        final Optional<BigDecimal> productAmountTaxOptional = IMPORT_SALE_TAX_RULE.getProductAmountTax(productImportedGiven);

        // Then
        assertThat(productAmountTaxOptional).isNotEmpty();
        assertThat(productAmountTaxOptional.get()).isEqualByComparingTo("0.50");
    }

    @Test
    void taxRoundedCalculated_caseProductTaxableRoundable() {
        // Given
        final Product productImportedGiven = ProductTestBuilder.aFoodProductImported();

        // When
        final Optional<BigDecimal> productAmountTaxOptional = IMPORT_SALE_TAX_RULE.getProductAmountTax(productImportedGiven);

        // Then
        assertThat(productAmountTaxOptional).isNotEmpty();
        assertThat(productAmountTaxOptional.get()).isEqualByComparingTo("0.80");
    }
}
