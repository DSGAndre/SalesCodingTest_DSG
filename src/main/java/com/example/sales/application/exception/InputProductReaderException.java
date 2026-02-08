package com.example.sales.application.exception;

import java.io.IOException;

public class InputProductReaderException extends RuntimeException {
    final static String INPUT_FILE_NAME_INVALID = "[ERROR] : File name must be filled and not null. Provided file name: ";
    final static String INPUT_FILE_NOT_FOUND_FOR_NAME = "[ERROR] : File not found with provided name: ";
    final static String INPUT_FILE_READING_ERROR = "[ERROR] : While reading file with name:";
    final static String INPUT_LINE_INVALID = "[ERROR] : Failed to match regex with file line: ";

    public InputProductReaderException(final String errorMessage) {
        super(errorMessage);
    }

    public InputProductReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public static InputProductReaderException inputFileNameInvalid(final String inputFileName) {
        final String messageError = INPUT_FILE_NAME_INVALID + inputFileName;
        return new InputProductReaderException(messageError);
    }

    public static InputProductReaderException inputFileNotFound(final String inputFileName) {
        final String messageError = INPUT_FILE_NOT_FOUND_FOR_NAME + inputFileName;
        return new InputProductReaderException(messageError);
    }

    public static InputProductReaderException inputFileReadError(final String inputFileName,
                                                                 final IOException ioException) {
        final String messageError = INPUT_FILE_READING_ERROR + inputFileName;
        return new InputProductReaderException(messageError, ioException.getCause());
    }

    public static InputProductReaderException inputLineInvalid(final String fileLine) {
        final String messageError = INPUT_LINE_INVALID + fileLine;
        return new InputProductReaderException(messageError);
    }

}
