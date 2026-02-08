package com.example.sales.domain.model;

import java.math.BigDecimal;

public class Product {

    private final String name;
    private final int quantity;
    private final Category category;
    private final BigDecimal price;
    private final boolean isImported;

    public Product(String name, int quantity, Category category, BigDecimal price, boolean isImported) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.price = price;
        this.isImported = isImported;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Category getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isImported() {
        return isImported;
    }
}
