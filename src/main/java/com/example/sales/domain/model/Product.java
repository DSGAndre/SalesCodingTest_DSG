package com.example.sales.domain.model;

import com.example.sales.application.exception.ProductException;

import java.math.BigDecimal;

public record Product(String name, int quantity, Category category, BigDecimal price, boolean isImported) {

    public Product {
        if (name == null || name.isBlank()) {
            throw ProductException.nameInvalid(name);
        }

        if (quantity <= 0) {
            throw ProductException.quantityNegative(quantity);
        }

        if (category == null) {
            throw ProductException.categoryInvalid();
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw ProductException.priceInvalid(price);
        }
    }

    public boolean isBasicSaleTaxable() {
        return category == Category.OTHERS;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", category=" + category +
                ", price=" + price +
                ", isImported=" + isImported +
                '}';
    }
}
