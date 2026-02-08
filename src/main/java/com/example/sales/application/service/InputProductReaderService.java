package com.example.sales.application.service;

import com.example.sales.application.entity.InputProductLine;
import com.example.sales.application.entity.ProductDto;
import com.example.sales.application.exception.InputProductReaderException;
import com.example.sales.application.mapper.InputProductLineToProductDtoMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputProductReaderService {
    final static String IMPORTED_WORD = "imported";
    final static Pattern INPUT_TO_PRODUCT_PATTERN = Pattern.compile("^(\\d+)\\s+(.+)\\s+at\\s+(\\d+\\.\\d{2})$");;

    public static List<ProductDto> readProductInput(final String inputFileName) {
        if (inputFileName == null || inputFileName.isBlank()) {
            throw InputProductReaderException.inputFileNameInvalid(inputFileName);
        }

        final List<String> lines = readLineFromFile(inputFileName);
        final List<InputProductLine> inputProductLineList = lines
                .stream()
                .map(InputProductReaderService::parseLineToInputProductLine)
                .toList();

        return InputProductLineToProductDtoMapper.convertList(inputProductLineList);
    }

    private static List<String> readLineFromFile(final String inputFileName) {
        final InputStream fileResourceInputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(inputFileName);

        if (fileResourceInputStream == null) {
            throw InputProductReaderException.inputFileNotFound(inputFileName);
        }

        final InputStreamReader inputStreamReader = new InputStreamReader(fileResourceInputStream);
        try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            return bufferedReader.lines().toList();
        } catch (IOException ioException) {
            throw InputProductReaderException.inputFileReadError(inputFileName, ioException);
        }
    }

    private static InputProductLine parseLineToInputProductLine(final String line) {
        final Matcher matcher = INPUT_TO_PRODUCT_PATTERN.matcher(line);

        if (!matcher.matches()) {
            throw InputProductReaderException.inputLineInvalid(line);
        }

        final String productNameWithImportedWord = matcher.group(2);
        final boolean isImported = productNameWithImportedWord.contains(IMPORTED_WORD);

        return new InputProductLine(
                Integer.parseInt(matcher.group(1)),
                isImported,
                productNameWithImportedWord,
                matcher.group(3));
    }
}
