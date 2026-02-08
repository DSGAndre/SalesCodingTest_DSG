package com.example.sales.domain.builder;

import com.example.sales.application.entity.ProductDto;
import com.example.sales.domain.model.Category;
import com.example.sales.domain.model.Product;

import java.math.BigDecimal;

public class ProductDtoTestBuilder {

    private String name = "Test Product";
    private int quantity = 1;
    private Category category = Category.OTHERS;
    private BigDecimal price = BigDecimal.valueOf(10.00);
    private boolean imported = false;

    public ProductDtoTestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductDtoTestBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductDtoTestBuilder withCategory(Category category) {
        this.category = category;
        return this;
    }

    public ProductDtoTestBuilder withPrice(double price) {
        this.price = BigDecimal.valueOf(price);
        return this;
    }

    public ProductDtoTestBuilder withoutPrice() {
        this.price = null;
        return this;
    }

    public ProductDtoTestBuilder imported() {
        this.imported = true;
        return this;
    }

    public ProductDtoTestBuilder notImported() {
        this.imported = false;
        return this;
    }

    public ProductDto build() {
        return new ProductDto(name, quantity, category, price, imported);
    }

    public static ProductDto aBookProductDto() {
        return new ProductDtoTestBuilder()
                .withName("book")
                .withQuantity(1)
                .withCategory(Category.BOOKS)
                .withPrice(12.49)
                .notImported()
                .build();
    }

    public static ProductDto aWorkingMouseImportedProductDto() {
        return new ProductDtoTestBuilder()
                .withName("working mouse")
                .withQuantity(1)
                .withCategory(Category.OTHERS)
                .withPrice(30.48)
                .imported()
                .build();
    }
}
