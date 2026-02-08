package com.example.sales.application.service;

import com.example.sales.application.entity.ProductDto;
import com.example.sales.application.exception.InputProductReaderException;
import com.example.sales.domain.builder.ProductDtoTestBuilder;
import com.example.sales.domain.model.Category;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class InputProductReaderServiceTest {

    @Test
    void inputFileNameInvalidException_caseInputFileNameNull() {
        assertThatThrownBy(() -> InputProductReaderService.readProductInput(null))
                .isInstanceOf(InputProductReaderException.class)
                .hasMessage(InputProductReaderException.inputFileNameInvalid(null).getMessage());
    }

    @Test
    void inputFileNameInvalidException_caseInputFileNameBlank() {
        assertThatThrownBy(() -> InputProductReaderService.readProductInput(""))
                .isInstanceOf(InputProductReaderException.class)
                .hasMessage(InputProductReaderException.inputFileNameInvalid("").getMessage());
    }

    @Test
    void inputFileNotFoundxception_caseInputFileNotFound() {
        assertThatThrownBy(() -> InputProductReaderService.readProductInput("notfound.txt"))
                .isInstanceOf(InputProductReaderException.class)
                .hasMessage(InputProductReaderException.inputFileNotFound("notfound.txt").getMessage());
    }

    @Test
    void emptyListProductDto_caseEmptyFile() {
        // Given
        final String fileNameGiven = "inputLineEmpty.txt";

        // When
        final List<ProductDto> productDtoListActual = InputProductReaderService.readProductInput(fileNameGiven);

        // Then
        assertThat(productDtoListActual).isEmpty();
    }

    @Test
    void inputLineInvalidException_caseRegexWithMissinWord() {
        // Given
        final String fileNameGiven = "inputLineMissingWord.txt";

        // When / Then
        final String lineExpected = "1 book 12.49";
        assertThatThrownBy(() -> InputProductReaderService.readProductInput(fileNameGiven))
                .isInstanceOf(InputProductReaderException.class)
                .hasMessage(InputProductReaderException.inputLineInvalid(lineExpected).getMessage());
    }

    @Test
    void inputLineInvalidException_caseRegexWithCommaSeparator() {
        // Given
        final String fileNameGiven = "inputLineWithCommaAsSeparator.txt";

        // When / Then
        final String lineExpected = "1 book at 12,49";
        assertThatThrownBy(() -> InputProductReaderService.readProductInput(fileNameGiven))
                .isInstanceOf(InputProductReaderException.class)
                .hasMessage(InputProductReaderException.inputLineInvalid(lineExpected).getMessage());
    }

    @Test
    void inputLineInvalidException_caseRegexWithWordAdded() {
        // Given
        final String fileNameGiven = "inputLineWithEurosPrecision.txt";

        // When / Then
        final String lineExpected = "1 book at 12.49 euros";
        assertThatThrownBy(() -> InputProductReaderService.readProductInput(fileNameGiven))
                .isInstanceOf(InputProductReaderException.class)
                .hasMessage(InputProductReaderException.inputLineInvalid(lineExpected).getMessage());
    }

    @Test
    void inputLineInvalidException_caseRegexWithoutDecimal() {
        // Given
        final String fileNameGiven = "inputLineWithoutDecimal.txt";

        // When / Then
        final String lineExpected = "1 book at 12";
        assertThatThrownBy(() -> InputProductReaderService.readProductInput(fileNameGiven))
                .isInstanceOf(InputProductReaderException.class)
                .hasMessage(InputProductReaderException.inputLineInvalid(lineExpected).getMessage());
    }

    @Test
    void inputLineInvalidException_caseRegexWithQuantityFloat() {
        // Given
        final String fileNameGiven = "inputLineWithQuantityFloat.txt";

        // When / Then
        final String lineExpected = "1,5 book at 12.49";
        assertThatThrownBy(() -> InputProductReaderService.readProductInput(fileNameGiven))
                .isInstanceOf(InputProductReaderException.class)
                .hasMessage(InputProductReaderException.inputLineInvalid(lineExpected).getMessage());
    }

    @Test
    void productDtoSingleList_caseSingleLine() {
        // Given
        final String fileNameGiven = "inputSuccessSingleLine.txt";

        // When
        final List<ProductDto> productDtoListActual = InputProductReaderService.readProductInput(fileNameGiven);

        // Then
        final ProductDto productDtoExpected = new ProductDto("book",
                1,
                Category.BOOKS,
                new BigDecimal("12.49"),
                false);

        assertThat(productDtoListActual)
                .usingRecursiveComparison()
                .isEqualTo(Collections.singleton(productDtoExpected));
    }

    @Test
    void productDtoMultipleList_caseMultipleLine() {
        // Given
        final String fileNameGiven = "inputSuccessMultipleLine.txt";

        // When
        final List<ProductDto> productDtoListActual = InputProductReaderService.readProductInput(fileNameGiven);

        // Then
        final ProductDto bookProductDtoExpected = ProductDtoTestBuilder.aBookProductDto();
        final ProductDto importedMouseProductDtoExpected = ProductDtoTestBuilder.aWorkingMouseImportedProductDto();
        final List<ProductDto> productDtoListExpected = List.of(bookProductDtoExpected, importedMouseProductDtoExpected);

        assertThat(productDtoListActual)
                .usingRecursiveComparison()
                .isEqualTo(productDtoListExpected);
    }
}
