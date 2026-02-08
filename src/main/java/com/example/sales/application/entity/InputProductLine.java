package com.example.sales.application.entity;

import com.example.sales.application.exception.InputProductLineException;

public record InputProductLine(int quantity, boolean isImported, String fileName, String price) {
    public InputProductLine {
        if (quantity <= 0) {
            throw InputProductLineException.quantityNegative(quantity);
        }

        if (fileName == null || fileName.isBlank()) {
            throw InputProductLineException.fileNameInvalid(fileName);
        }

        if (price == null || price.isBlank()) {
            throw InputProductLineException.priceInvalid(price);
        }
    }

    @Override
    public String toString() {
        return "InputProductLine{" +
                "quantity=" + quantity +
                ", isImported=" + isImported +
                ", fileName='" + fileName + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
