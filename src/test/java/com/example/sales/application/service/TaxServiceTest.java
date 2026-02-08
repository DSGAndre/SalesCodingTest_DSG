package com.example.sales.application.service;

import com.example.sales.application.exception.ProductException;
import com.example.sales.application.exception.TaxException;
import com.example.sales.domain.builder.ProductTestBuilder;
import com.example.sales.domain.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TaxServiceTest {

    @Test
    void emptyPriceResult_caseNullProduct() {
        assertThatThrownBy(() -> TaxService.getPriceTaxed(null))
                .isInstanceOf(ProductException.class)
                .hasMessage(ProductException.productInvalid().getMessage());
    }

    @Test
    void productExceptionPriceInvalid_caseNullPrice() {
        assertThatThrownBy(() -> new ProductTestBuilder()
                .withoutPrice()
                .build())
                .isInstanceOf(ProductException.class)
                .hasMessage(ProductException.priceInvalid(null).getMessage());
    }

    @Test
    void priceWithNoTaxToApply_caseNotBasicSaleAndNotImported() {
        // Given
        final Product notBasicSaleAndNotImportedProductGiven = ProductTestBuilder.aBookProduct();

        // When
        final BigDecimal priceTaxed = TaxService.getPriceTaxed(notBasicSaleAndNotImportedProductGiven);

        // Then
        assertThat(priceTaxed).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void priceWithBasicTax_caseBasicSaleOnly() {
        // Given
        final Product basicSaleProductGiven = ProductTestBuilder.aMouseProduct();

        // When
        final BigDecimal priceTaxed = TaxService.getPriceTaxed(basicSaleProductGiven);

        // Then
        assertThat(priceTaxed).isEqualByComparingTo("2.35");
    }

    @Test
    void priceWithImportedTax_caseImportedTaxOnly() {
        // Given
        final Product importedSaleProductGiven = ProductTestBuilder.aFoodProductImported();

        // When
        final BigDecimal priceTaxed = TaxService.getPriceTaxed(importedSaleProductGiven);

        // Then
        assertThat(priceTaxed).isEqualByComparingTo("0.80");
    }

    @Test
    void priceTaxed_caseBasicSaleTaxAndImportedTax() {
        // Given
        final Product productGiven = ProductTestBuilder.aKeyboardImportedProduct();

        // When
        final BigDecimal priceTaxed = TaxService.getPriceTaxed(productGiven);

        // Then
        assertThat(priceTaxed).isEqualByComparingTo("4.60");
    }

    @Test
    void emptyProductResult_caseNullProduct() {
        assertThatThrownBy(() -> TaxService.addPriceTaxed(null, BigDecimal.ONE))
                .isInstanceOf(ProductException.class)
                .hasMessage(ProductException.productInvalid().getMessage());
    }

    @Test
    void emptyProductResult_caseNullTaxAmount() {
        assertThatThrownBy(() -> TaxService.addPriceTaxed(new ProductTestBuilder().build(), null))
                .isInstanceOf(TaxException.class)
                .hasMessage(TaxException.taxAmountInvalid(null).getMessage());
    }

    @Test
    void emptyProductResult_caseNegativeTaxAmount() {
        // Given
        final BigDecimal negativeTaxAmountGiven = BigDecimal.valueOf(-5);

        // When / Then
        assertThatThrownBy(() -> TaxService.addPriceTaxed(new ProductTestBuilder().build(), negativeTaxAmountGiven))
                .isInstanceOf(TaxException.class)
                .hasMessage(TaxException.taxAmountInvalid(negativeTaxAmountGiven).getMessage());
    }

    @Test
    void productWithNoTaxToApply_caseNotBasicSaleAndNotImported() {
        // Given
        final Product notBasicSaleAndNotImportedProductGiven = ProductTestBuilder.aBookProduct();

        // When
        final Product productActual = TaxService.addPriceTaxed(notBasicSaleAndNotImportedProductGiven, BigDecimal.ZERO);

        // Then
        assertThat(productActual).usingRecursiveComparison().isEqualTo(notBasicSaleAndNotImportedProductGiven);
    }

    @Test
    void productWithTax_caseSucess() {
        // Given
        final Product basicSaleProductGiven = ProductTestBuilder.aMouseProduct();
        final BigDecimal taxAmountGiven = BigDecimal.valueOf(2.35);

        // When
        final Product productActual = TaxService.addPriceTaxed(basicSaleProductGiven, taxAmountGiven);

        // Then
        assertThat(productActual).usingRecursiveComparison()
                .ignoringFields("price")
                .isEqualTo(basicSaleProductGiven);
        assertThat(productActual.price()).isEqualTo(BigDecimal.valueOf(25.56));
    }
}
