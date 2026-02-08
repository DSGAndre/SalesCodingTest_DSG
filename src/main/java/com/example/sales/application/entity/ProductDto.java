package com.example.sales.application.entity;

import com.example.sales.domain.model.Category;

import java.math.BigDecimal;

public class ProductDto {

    final String name;
    final int quantity;
    final Category category;
    final BigDecimal price;
    final boolean isImported;

    public ProductDto(String name, int quantity, Category category, BigDecimal price, boolean isImported) {
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

    @Override
    public String toString() {
        return "ProductDto{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", category=" + category +
                ", price=" + price +
                ", isImported=" + isImported +
                '}';
    }
}
