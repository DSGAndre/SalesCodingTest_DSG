package com.example.sales.application.mapper;

import com.example.sales.application.entity.ProductDto;
import com.example.sales.domain.model.Product;

import java.util.Collections;
import java.util.List;

public class ProductDtoToProductMapper {

    public static List<Product> convertList(final List<ProductDto> productDtoList) {
        if (productDtoList == null || productDtoList.isEmpty()) {
            return Collections.emptyList();
        }

        return productDtoList.stream()
                .map(ProductDtoToProductMapper::convert)
                .toList();
    }

    public static Product convert(final ProductDto productDto) {
        if (productDto == null) {
            return null;
        }
        return new Product(productDto.getName(),
                productDto.getQuantity(),
                productDto.getCategory(),
                productDto.getPrice(),
                productDto.isImported());
    }
}
