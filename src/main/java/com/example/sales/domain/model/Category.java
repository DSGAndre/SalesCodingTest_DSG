package com.example.sales.domain.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Category {
    BOOKS(Collections.singletonList("book")),
    FOOD(Collections.singletonList("chocolate")),
    MEDICAL(Collections.singletonList("headache pills")),
    OTHERS(Collections.EMPTY_LIST);

    private final List<String> productNameList;

    Category(final List<String> productNameList) {
        this.productNameList = productNameList;
    }

    public static Category getCategoryProduct(final String productName) {
        if (productName == null || productName.isBlank()) {
            return OTHERS;
        }

        final String productNameInLowerCase = productName.toLowerCase();

        return Arrays.stream(values())
                .filter(category -> isProductNameInCategoryProductList(category, productNameInLowerCase))
                .findFirst()
                .orElse(OTHERS);
    }

    private static boolean isProductNameInCategoryProductList(final Category category, final String productName) {
        return category.productNameList.stream().anyMatch(productName::contains);
    }
}
