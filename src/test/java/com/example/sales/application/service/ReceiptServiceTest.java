package com.example.sales.application.service;

import com.example.sales.application.exception.ReceiptException;
import com.example.sales.domain.builder.ProductTestBuilder;
import com.example.sales.domain.model.Category;
import com.example.sales.domain.model.Product;
import com.example.sales.domain.model.Receipt;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReceiptServiceTest {

    private static final ByteArrayOutputStream consoleOutputStream = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;

    @BeforeAll
    public static void setUpStreams() {
        System.setOut(new PrintStream(consoleOutputStream));
    }

    @AfterEach
    public void clearConsole() throws IOException {
        consoleOutputStream.reset();
    }

    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void productListInvalidException_caseProductListNull() {
        assertThatThrownBy(() -> ReceiptService.constructReceipt(null))
                .isInstanceOf(ReceiptException.class)
                .hasMessage(ReceiptException.productListInvalid(null).getMessage());
    }

    @Test
    void productListInvalidException_caseProductListEmpty() {
        assertThatThrownBy(() -> ReceiptService.constructReceipt(Collections.emptyList()))
                .isInstanceOf(ReceiptException.class)
                .hasMessage(ReceiptException.productListInvalid(Collections.emptyList()).getMessage());
    }

    @Test
    void productListWithoutTax_caseNoTaxProduct() {
        // Given
        final Product productWithoutTaxProvided = ProductTestBuilder.aBookProduct();
        final List<Product> singletonProductListProvided = Collections.singletonList(productWithoutTaxProvided);

        // When
        final Receipt receiptActual = ReceiptService.constructReceipt(singletonProductListProvided);

        // Then
        final Receipt receiptExpected = new Receipt(singletonProductListProvided, productWithoutTaxProvided.price(), BigDecimal.ZERO);
        assertThat(receiptActual).usingRecursiveComparison()
                .isEqualTo(receiptExpected);
    }

    @Test
    void singletonProductListWithBasicTax_caseBasicTaxProduct() {
        // Given
        final Product productWithBasicTaxProvided = ProductTestBuilder.aMouseProduct();
        final List<Product> singletonProductListProvided = Collections.singletonList(productWithBasicTaxProvided);

        // When
        final Receipt receiptActual = ReceiptService.constructReceipt(singletonProductListProvided);

        // Then
        assertThat(receiptActual.productList()).hasSize(1);

        final Product taxedProduct = receiptActual.productList().getFirst();
        checkMouseProductExpected(taxedProduct);

        assertThat(receiptActual.totalTax()).isEqualByComparingTo("2.35");
        assertThat(receiptActual.totalPrice()).isEqualByComparingTo("25.56");
    }

    @Test
    void singletonProductListWithBothTax_caseBasicAndImportedTaxProduct() {
        // Given
        final Product productWithBasicAndImportedTaxProvided = ProductTestBuilder.aKeyboardImportedProduct();
        final List<Product> singletonProductListProvided = Collections.singletonList(productWithBasicAndImportedTaxProvided);

        // When
        final Receipt receiptActual = ReceiptService.constructReceipt(singletonProductListProvided);

        // Then
        assertThat(receiptActual.productList()).hasSize(1);

        final Product taxedProduct = receiptActual.productList().getFirst();
        checkImportedKeyboardProductExpected(taxedProduct);

        assertThat(receiptActual.totalTax()).isEqualByComparingTo("4.60");
        assertThat(receiptActual.totalPrice()).isEqualByComparingTo("35.08");
    }

    @Test
    void multipleProductListWithBothTax_caseBasicAndImportedTaxProduct() {
        // Given
        final Product productWithoutTaxProvided = ProductTestBuilder.aBookProduct();
        final Product productWithBasicTaxProvided = ProductTestBuilder.aMouseProduct();
        final Product productWithBasicAndImportedTaxProvided = ProductTestBuilder.aKeyboardImportedProduct();
        final List<Product> productListProvided = Arrays.asList(productWithoutTaxProvided, productWithBasicTaxProvided, productWithBasicAndImportedTaxProvided);

        // When
        final Receipt receiptActual = ReceiptService.constructReceipt(productListProvided);

        // Then
        assertThat(receiptActual.productList()).hasSize(3);
        final Product bookProductExpected = receiptActual.productList().get(0);
        assertThat(bookProductExpected).usingRecursiveComparison()
                .isEqualTo(productWithoutTaxProvided);

        final Product mouseProductExpected = receiptActual.productList().get(1);
        checkMouseProductExpected(mouseProductExpected);

        final Product importKeyboardProduct = receiptActual.productList().get(2);
        checkImportedKeyboardProductExpected(importKeyboardProduct);

        assertThat(receiptActual.totalTax()).isEqualByComparingTo("6.95");
        assertThat(receiptActual.totalPrice()).isEqualByComparingTo("67.63");
    }

    @Test
    void receiptInvalidException_caseReceiptNull() {
        assertThatThrownBy(() -> ReceiptService.printReceipt(null))
                .isInstanceOf(ReceiptException.class)
                .hasMessage(ReceiptException.receiptInvalid().getMessage());
    }

    @Test
    void receiptWithoutTaxes_caseSingletonProductWithNoTaxe() {
        // Given
        final Product productWithoutTaxProvided = ProductTestBuilder.aBookProduct();
        final Receipt receiptProvied = new Receipt(Collections.singletonList(productWithoutTaxProvided), productWithoutTaxProvided.price(), BigDecimal.ZERO);

        // When
        ReceiptService.printReceipt(receiptProvied);

        // Then
        assertThat(consoleOutputStream.toString())
                .isEqualToNormalizingWhitespace("1 LivreSansNom: 6.99\nSales Taxes: 0 Total: 6.99");

    }

    @Test
    void receiptWithTax_caseMultipleProductWithAllTax() {
        // Given
        final Product productWithoutTaxProvided = ProductTestBuilder.aBookProduct();
        final Product productWithBasicTaxProvided = ProductTestBuilder.aMouseProduct();
        final Product productWithBasicAndImportedTaxProvided = ProductTestBuilder.aKeyboardImportedProduct();
        final List<Product> productListProvided = Arrays.asList(productWithoutTaxProvided, productWithBasicTaxProvided, productWithBasicAndImportedTaxProvided);
        final Receipt receiptProvied = new Receipt(productListProvided, new BigDecimal("67.63"), new BigDecimal("6.95"));

        // When
        ReceiptService.printReceipt(receiptProvied);

        // Then
        assertThat(consoleOutputStream.toString())
                .isEqualToNormalizingWhitespace("""
                        1 LivreSansNom: 6.99
                        1 Mouse: 23.21
                        1 imported keyboard: 30.48
                        Sales Taxes: 6.95 Total: 67.63""");
    }

    private static void checkMouseProductExpected(Product taxedProduct) {
        assertThat(taxedProduct.name()).isEqualTo("Mouse");
        assertThat(taxedProduct.quantity()).isEqualTo(1);
        assertThat(taxedProduct.category()).isEqualTo(Category.OTHERS);
        assertThat(taxedProduct.price()).isEqualByComparingTo("25.56");
        assertThat(taxedProduct.isImported()).isFalse();
    }

    private static void checkImportedKeyboardProductExpected(Product taxedProduct) {
        assertThat(taxedProduct.name()).isEqualTo("imported keyboard");
        assertThat(taxedProduct.quantity()).isEqualTo(1);
        assertThat(taxedProduct.category()).isEqualTo(Category.OTHERS);
        assertThat(taxedProduct.price()).isEqualByComparingTo("35.08");
        assertThat(taxedProduct.isImported()).isTrue();
    }

}
