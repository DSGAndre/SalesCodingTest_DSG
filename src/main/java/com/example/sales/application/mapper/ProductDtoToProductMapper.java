package com.example.sales.application.mapper;

import com.example.sales.application.entity.ProductDto;
import com.example.sales.domain.model.Product;

public class ProductDtoToProductMapper {

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
