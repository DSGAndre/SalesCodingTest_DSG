package com.example.sales.domain.model;

import java.math.BigDecimal;

public record Product(String name, int quantity, Category category, BigDecimal price, boolean isImported) {

    public Product {
        // TODO - Gestion des exceptions pour le valueObject
    }

    public boolean isBasicSaleTaxable() {
        return category == Category.OTHERS;
    }
}
