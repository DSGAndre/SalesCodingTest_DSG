package com.example.sales.infrastructure.scenario;

import com.example.sales.application.entity.ProductDto;
import com.example.sales.application.service.InputProductReaderService;

import java.util.List;

public class PrintReceiptScenario {

    public static void printAllReceipt() {
        printReceipt("input1.txt");
        printReceipt("input2.txt");
        printReceipt("input3.txt");

    }

    private static void printReceipt(final String fileName) {
        // Lecture des donnnées en entrée
        final List<ProductDto> productDtoList = InputProductReaderService.readProductInput(fileName);
        productDtoList.forEach(productDto -> System.out.println(productDto.toString()));

        // Construire un receipt en faisant une boucle :
        // Pour chaque produit récupéré son prix et son prix taxé
        // Log le toString de receipt.
    }
}
