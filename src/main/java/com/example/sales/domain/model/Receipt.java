package com.example.sales.domain.model;

import com.example.sales.application.exception.ReceiptException;
import com.example.sales.domain.utils.StringUtils;

import java.math.BigDecimal;
import java.util.List;

public record Receipt(List<Product> productList, BigDecimal totalPrice, BigDecimal totalTax) {

    private static final String SALE_TAXES = "Sales Taxes: ";
    private static final String TOTAL = "Total: ";

    public Receipt {
        if (productList == null || productList.isEmpty()) {
            throw ReceiptException.productListInvalid(productList);
        }

        if (totalPrice == null || totalPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw ReceiptException.totalPriceInvalid(totalPrice);
        }

        if (totalTax == null || totalTax.compareTo(BigDecimal.ZERO) < 0) {
            throw ReceiptException.totalTaxInvalid(totalPrice);
        }
    }

    public String print() {
        return SALE_TAXES + totalTax
                + StringUtils.SPACE_CHAR + TOTAL + totalPrice;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "productList=" + productList +
                ", totalPrice=" + totalPrice +
                ", totalTax=" + totalTax +
                '}';
    }
}
