package com.example.sales.infrastructure.scenario;

import com.example.sales.application.entity.ProductDto;
import com.example.sales.application.mapper.ProductDtoToProductMapper;
import com.example.sales.application.service.InputProductReaderService;
import com.example.sales.domain.model.Product;

import java.util.List;

public class PrintReceiptScenario {

    public static void printAllReceipt() {
        printReceipt("input1.txt");
        printReceipt("input2.txt");
        printReceipt("input3.txt");

    }

    private static void printReceipt(final String fileName) {
        final List<ProductDto> productDtoList = InputProductReaderService.readProductInput(fileName);
        // Construire un receipt en faisant une boucle :
        final List<Product> productList = ProductDtoToProductMapper.convertList(productDtoList);
        productList.forEach(product -> System.out.println(product.toString()));

        // Pour chaque produit récupéré son prix et son prix taxé
        // Log le toString de receipt.
    }
}
