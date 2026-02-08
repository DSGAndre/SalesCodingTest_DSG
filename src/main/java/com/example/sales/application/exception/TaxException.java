package com.example.sales.application.exception;

import com.example.sales.domain.model.Product;

import java.math.BigDecimal;

public class TaxException extends RuntimeException {
    final static String FAILED_TO_CALCULATE_BASIC_AMOUNT_TAX_WITH_PRODUCT = "[ERROR] : Failed to calculate basic amount taxes with product: ";
    final static String FAILED_TO_CALCULATE_IMPORT_AMOUNT_TAX_WITH_PRODUCT = "[ERROR] : Failed to calculate import amount taxes with product: ";
    final static String TAX_AMOUNT_INVALID = "[ERROR] : tax amount must be positive and not null. Provided price: ";

    public TaxException(final String errorMessage) {
        super(errorMessage);
    }

    public static TaxException basicSaleTaxCalculationFailed(final Product product) {
        final String messageError = FAILED_TO_CALCULATE_BASIC_AMOUNT_TAX_WITH_PRODUCT + product.toString();
        return new TaxException(messageError);
    }

    public static TaxException importSaleTaxCalculationFailed(final Product product) {
        final String messageError = FAILED_TO_CALCULATE_IMPORT_AMOUNT_TAX_WITH_PRODUCT + product.toString();
        return new TaxException(messageError);
    }

    public static TaxException taxAmountInvalid(final BigDecimal taxAmount) {
        final String messageError = TAX_AMOUNT_INVALID + taxAmount;
        return new TaxException(messageError);
    }
}
