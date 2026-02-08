package com.example.sales.application.exception;

import java.math.BigDecimal;

public class ProductException extends RuntimeException {
    final static String PRODUCT_INVALID = "[ERROR] : Product must not be null.";
    final static String NAME_INVALID = "[ERROR] : Name must be valid. Provided name: ";
    final static String QUANTITY_NEGATIVE = "[ERROR] : Quantity must be positive. Provided quantity: ";
    final static String CATEGORY_INVALID = "[ERROR] : Category must not be null.";
    final static String PRICE_INVALID = "[ERROR] : price must be positive and not null. Provided price: ";

    public ProductException(String message) {
        super(message);
    }

    public static ProductException productInvalid() {
        return new ProductException(PRODUCT_INVALID);
    }

    public static ProductException nameInvalid(final String name) {
        final String messageError = NAME_INVALID + name;
        return new ProductException(messageError);
    }

    public static ProductException quantityNegative(final int quantity) {
        final String messageError = QUANTITY_NEGATIVE + quantity;
        return new ProductException(messageError);
    }

    public static ProductException categoryInvalid() {
        return new ProductException(CATEGORY_INVALID);
    }

    public static ProductException priceInvalid(final BigDecimal price) {
        final String messageError = PRICE_INVALID + price;
        return new ProductException(messageError);
    }
}
