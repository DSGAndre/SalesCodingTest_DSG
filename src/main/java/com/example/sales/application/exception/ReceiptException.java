package com.example.sales.application.exception;

import com.example.sales.domain.model.Product;

import java.math.BigDecimal;
import java.util.List;

public class ReceiptException extends RuntimeException {
    final static String RECEIPT_INVALID = "[ERROR] : receipt must not be null.";
    final static String PRODUCT_LIST_INVALID = "[ERROR] : total price must be filled and not null. Provided product list: ";
    final static String TOTAL_PRICE_INVALID = "[ERROR] : total price must be positive and not null. Provided total price: ";
    final static String TOTAL_TAX_INVALID = "[ERROR] : total tax must be positive and not null. Provided total tax: ";

    public ReceiptException(String message) {
        super(message);
    }

    public static ReceiptException receiptInvalid() {
        return new ReceiptException(RECEIPT_INVALID);
    }

    public static ReceiptException productListInvalid(final List<Product> productList) {
        final String messageError = TOTAL_PRICE_INVALID + PRODUCT_LIST_INVALID;
        return new ReceiptException(messageError);
    }

    public static ReceiptException totalPriceInvalid(final BigDecimal totalPrice) {
        final String messageError = TOTAL_PRICE_INVALID + totalPrice;
        return new ReceiptException(messageError);
    }

    public static ReceiptException totalTaxInvalid(final BigDecimal TotalTax) {
        final String messageError = TOTAL_TAX_INVALID + TotalTax;
        return new ReceiptException(messageError);
    }


}
