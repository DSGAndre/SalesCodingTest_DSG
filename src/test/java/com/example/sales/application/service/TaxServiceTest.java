package com.example.sales.application.service;

import com.example.sales.application.exception.ProductException;
import com.example.sales.domain.builder.ProductTestBuilder;
import com.example.sales.domain.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TaxServiceTest {

    @Test
    void productInvalidException_caseNullProduct() {
        assertThatThrownBy(() -> TaxService.getTaxAmount(null))
                .isInstanceOf(ProductException.class)
                .hasMessage(ProductException.productInvalid().getMessage());
    }

    @Test
    void productPriceException_caseNullPrice() {
        assertThatThrownBy(() -> new ProductTestBuilder()
                .withoutPrice()
                .build())
                .isInstanceOf(ProductException.class)
                .hasMessage(ProductException.priceInvalid(null).getMessage());
    }

    @Test
    void noTaxToApply_caseNotBasicSaleAndNotImported() {
        // Given
        final Product notBasicSaleAndNotImportedProductGiven = ProductTestBuilder.aBookProduct();

        // When
        final BigDecimal taxAmountActual = TaxService.getTaxAmount(notBasicSaleAndNotImportedProductGiven);

        // Then
        assertThat(taxAmountActual).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void basicTax_caseBasicSaleOnly() {
        // Given
        final Product basicSaleProductGiven = ProductTestBuilder.aMouseProduct();

        // When
        final BigDecimal taxAmountActual = TaxService.getTaxAmount(basicSaleProductGiven);

        // Then
        assertThat(taxAmountActual).isEqualByComparingTo("2.35");
    }

    @Test
    void importedTax_caseImportedTaxOnly() {
        // Given
        final Product importedSaleProductGiven = ProductTestBuilder.aFoodProductImported();

        // When
        final BigDecimal taxAmountActual = TaxService.getTaxAmount(importedSaleProductGiven);

        // Then
        assertThat(taxAmountActual).isEqualByComparingTo("2.35");
    }

    @Test
    void bothTaxAmount_caseBasicSaleTaxAndImportedTax() {
        // Given
        final Product productGiven = ProductTestBuilder.aKeyboardImportedProduct();

        // When
        final BigDecimal taxAmountActual = TaxService.getTaxAmount(productGiven);

        // Then
        assertThat(taxAmountActual).isEqualByComparingTo("4.60");
    }

    @Test
    void emptyProductResult_caseNullProduct() {
        // Given
        assertThatThrownBy(() -> TaxService.applyTax(null))
                .isInstanceOf(ProductException.class)
                .hasMessage(ProductException.productInvalid().getMessage());
    }

    @Test
    void productWithNoTaxToApply_caseNotBasicSaleAndNotImported() {
        // Given
        final Product notBasicSaleAndNotImportedProductGiven = ProductTestBuilder.aBookProduct();

        // When
        final Product productActual = TaxService.applyTax(notBasicSaleAndNotImportedProductGiven);

        // Then
        assertThat(productActual).usingRecursiveComparison().isEqualTo(notBasicSaleAndNotImportedProductGiven);
    }

    @Test
    void productWithQuantityAndTax_caseSucess() {
        // Given
        final Product basicSaleProductGiven =  new ProductTestBuilder()
                .withQuantity(7)
                .build();

        // When
        final Product productActual = TaxService.applyTax(basicSaleProductGiven);

        // Then
        assertThat(productActual).usingRecursiveComparison()
                .ignoringFields("price")
                .isEqualTo(basicSaleProductGiven);
        assertThat(productActual.price()).isEqualTo(new BigDecimal("77.00"));
    }

    @Test
    void productWithTax_caseSucess() {
        // Given
        final Product basicSaleProductGiven = ProductTestBuilder.aMouseProduct();

        // When
        final Product productActual = TaxService.applyTax(basicSaleProductGiven);

        // Then
        assertThat(productActual).usingRecursiveComparison()
                .ignoringFields("price")
                .isEqualTo(basicSaleProductGiven);
        assertThat(productActual.price()).isEqualTo(BigDecimal.valueOf(25.56));
    }
}
