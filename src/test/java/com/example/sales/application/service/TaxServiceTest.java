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
    void emptyResult_caseNullProduct() {
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
    void notTaxToApply_caseNotBasicSaleAndNotImported() {
        // Given
        final Product notBasicSaleAndNotImportedProductGiven = ProductTestBuilder.aBookProduct();

        // When
        final BigDecimal priceTaxed = TaxService.getPriceTaxed(notBasicSaleAndNotImportedProductGiven);

        // Then
        assertThat(priceTaxed).isEqualByComparingTo(notBasicSaleAndNotImportedProductGiven.price());
    }

    @Test
    void priceWithBasicTax_caseBasicSaleOnly() {
        // Given
        final Product basicSaleProductGiven = ProductTestBuilder.aMouseProduct();

        // When
        final BigDecimal priceTaxed = TaxService.getPriceTaxed(basicSaleProductGiven);

        // Then
        assertThat(priceTaxed).isEqualByComparingTo("25.56");
    }

    @Test
    void priceWithImportedTax_caseImportedTaxOnly() {
        // Given
        final Product importedSaleProductGiven = ProductTestBuilder.aFoodProductImported();

        // When
        final BigDecimal priceTaxed = TaxService.getPriceTaxed(importedSaleProductGiven);

        // Then
        assertThat(priceTaxed).isEqualByComparingTo("16.25");
    }

    @Test
    void priceTaxed_caseBasicSaleTaxAndImportedTax() {
        // Given
        final Product productGiven = ProductTestBuilder.aKeyboardImportedProduct();

        // When
        final BigDecimal priceTaxed = TaxService.getPriceTaxed(productGiven);

        // Then
        assertThat(priceTaxed).isEqualByComparingTo("35.08");
    }
}
