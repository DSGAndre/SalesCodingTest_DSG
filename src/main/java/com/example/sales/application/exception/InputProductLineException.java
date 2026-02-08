package com.example.sales.application.exception;

public class InputProductLineException extends RuntimeException {
    final static String QUANTITY_NEGATIVE = "[ERROR] : Quantity must be positive. Provided quantity: ";
    final static String FILE_NAME_INVALID = "[ERROR] : File name must be filled and not null. Provided name: ";
    final static String PRICE_INVALID = "[ERROR] : Price must be be filled and not null. Provided price: ";

    public InputProductLineException(String message) {
        super(message);
    }

    public static InputProductLineException quantityNegative(final int quantity) {
        final String messageError = QUANTITY_NEGATIVE + quantity;
        return new InputProductLineException(messageError);
    }

    public static InputProductLineException fileNameInvalid(final String name) {
        final String messageError = FILE_NAME_INVALID + name;
        return new InputProductLineException(messageError);
    }

    public static InputProductLineException priceInvalid(final String price) {
        final String messageError = PRICE_INVALID + price;
        return new InputProductLineException(messageError);
    }
}
