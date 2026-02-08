package com.example.sales.application.service;

import com.example.sales.application.exception.ReceiptException;
import com.example.sales.domain.model.Product;
import com.example.sales.domain.model.Receipt;

import java.math.BigDecimal;
import java.util.List;

public class ReceiptService {

    public static Receipt constructReceipt(final List<Product> productList) {
        if (productList == null || productList.isEmpty()) {
            throw ReceiptException.productListInvalid(productList);
        }

        // More readable, but not optimal — multiple passes over the productList
        final BigDecimal totalTax = calculateTotalTax(productList);
        final List<Product> productsTaxedList = productList.stream()
                .map(TaxService::applyTax)
                .toList();
        final BigDecimal totalPrice = calculateTotalPrice(productsTaxedList);

        return new Receipt(productsTaxedList, totalPrice, totalTax);
    }

    public static void printReceipt(final Receipt receipt) {
        if (receipt == null) {
            throw ReceiptException.receiptInvalid();
        }

        for (Product product : receipt.productList()) {
            System.out.println(product.print());
        }

        System.out.println(receipt.print());
    }

    private static BigDecimal calculateTotalTax(final List<Product> productList) {
        return productList.stream()
                .map(TaxService::getTaxAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal calculateTotalPrice(final List<Product> productList) {
        return productList.stream()
                .map(Product::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
