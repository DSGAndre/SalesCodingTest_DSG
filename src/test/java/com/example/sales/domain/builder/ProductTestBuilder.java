package com.example.sales.domain.builder;

import com.example.sales.domain.model.Category;
import com.example.sales.domain.model.Product;

import java.math.BigDecimal;

public class ProductTestBuilder {

    private String name = "Test Product";
    private int quantity = 1;
    private Category category = Category.OTHERS;
    private BigDecimal price = BigDecimal.valueOf(10.00);
    private boolean imported = false;

    public ProductTestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductTestBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductTestBuilder withCategory(Category category) {
        this.category = category;
        return this;
    }

    public ProductTestBuilder withPrice(double price) {
        this.price = BigDecimal.valueOf(price);
        return this;
    }

    public ProductTestBuilder withoutPrice() {
        this.price = null;
        return this;
    }

    public ProductTestBuilder imported() {
        this.imported = true;
        return this;
    }

    public ProductTestBuilder notImported() {
        this.imported = false;
        return this;
    }

    public Product build() {
        return new Product(name, quantity, category, price, imported);
    }

    public static Product aBookProduct() {
        return new ProductTestBuilder()
                .withName("LivreSansNom")
                .withQuantity(1)
                .withCategory(Category.BOOKS)
                .withPrice(6.99)
                .notImported()
                .build();
    }

    public static Product aMouseProduct() {
        return new ProductTestBuilder()
                .withName("Mouse")
                .withQuantity(1)
                .withCategory(Category.OTHERS)
                .withPrice(23.21)
                .notImported()
                .build();
    }

    public static Product aFoodProductImported() {
        return new ProductTestBuilder()
                .withName("imported fish")
                .withQuantity(3)
                .withCategory(Category.FOOD)
                .withPrice(15.45)
                .imported()
                .build();
    }

    public static Product aKeyboardImportedProduct() {
        return new ProductTestBuilder()
                .withName("imported keyboard")
                .withQuantity(1)
                .withCategory(Category.OTHERS)
                .withPrice(30.48)
                .imported()
                .build();
    }
}
