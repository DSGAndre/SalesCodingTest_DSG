package com.example.sales.application.mapper;

import com.example.sales.application.entity.InputProductLine;
import com.example.sales.application.entity.ProductDto;
import com.example.sales.domain.model.Category;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class InputProductLineToProductDtoMapper {
    public static List<ProductDto> convertList(final List<InputProductLine> inputProductLineList) {
        if (inputProductLineList == null || inputProductLineList.isEmpty()) {
            return Collections.emptyList();
        }

        return inputProductLineList.stream()
                .map(InputProductLineToProductDtoMapper::convert)
                .toList();
    }

    public static ProductDto convert(final InputProductLine inputProductLine) {
        if (inputProductLine == null) {
            return null;
        }

        final String fileName = inputProductLine.fileName();
        final Category category = Category.getCategoryProduct(fileName);
        final BigDecimal price = convertPrice(inputProductLine.price());
        new BigDecimal(inputProductLine.price());

        return new ProductDto(inputProductLine.fileName(),
                inputProductLine.quantity(),
                category,
                price,
                inputProductLine.isImported());
    }

    private static BigDecimal convertPrice(final String price) {
        try {
            return new BigDecimal(price);
        } catch (NumberFormatException numberFormatException) {
            return BigDecimal.ZERO;
        }
    }
}
