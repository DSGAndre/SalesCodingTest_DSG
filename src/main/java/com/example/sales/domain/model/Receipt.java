package com.example.sales.domain.model;

import java.math.BigDecimal;
import java.util.List;

public class Receipt {
    private final List<Product> productList;
    private final BigDecimal totalPrice;
    private final BigDecimal totalTax;

    public Receipt(List<Product> productList, BigDecimal totalPrice, BigDecimal totalTax) {
        this.productList = productList;
        this.totalPrice = totalPrice;
        this.totalTax = totalTax;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }
}
