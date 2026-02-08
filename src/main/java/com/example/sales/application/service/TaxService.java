package com.example.sales.application.service;

import com.example.sales.application.exception.ProductException;
import com.example.sales.application.exception.TaxException;
import com.example.sales.domain.model.Product;
import com.example.sales.domain.rule.BasicSaleTaxRule;
import com.example.sales.domain.rule.ImportSaleTaxRule;
import com.example.sales.domain.rule.TaxRule;

import java.math.BigDecimal;

public class TaxService {

    private static final TaxRule BASIC_SALE_TAX_RULE = new BasicSaleTaxRule();
    private static final TaxRule IMPORT_SALE_TAX_RULE = new ImportSaleTaxRule();

    public static BigDecimal getPriceTaxed(final Product product) {
        if (product == null) {
            throw ProductException.productInvalid();
        }
        BigDecimal taxedPrice = product.price();

        if (product.isBasicSaleTaxable()) {
            final BigDecimal basicTax = BASIC_SALE_TAX_RULE.getProductAmountTax(product)
                    .orElseThrow(() -> TaxException.basicSaleTaxCalculationFailed(product));
            taxedPrice = taxedPrice.add(basicTax);
        }

        if (product.isImported()) {
            final BigDecimal importTax = IMPORT_SALE_TAX_RULE.getProductAmountTax(product)
                    .orElseThrow(() -> TaxException.importSaleTaxCalculationFailed(product));
            taxedPrice = taxedPrice.add(importTax);
        }

        return taxedPrice;
    }
}
