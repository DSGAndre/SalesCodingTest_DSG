package com.example.sales.infrastructure.scenario;

import com.example.sales.application.entity.ProductDto;
import com.example.sales.application.mapper.ProductDtoToProductMapper;
import com.example.sales.application.service.InputProductReaderService;
import com.example.sales.application.service.ReceiptService;
import com.example.sales.domain.model.Product;
import com.example.sales.domain.model.Receipt;

import java.util.List;

public class PrintReceiptScenario {

    public static void printAllReceipt() {
        System.out.println("\n Output 1");
        printReceipt("input1.txt");
        System.out.println("\n Output 2");
        printReceipt("input2.txt");
        System.out.println("\n Output 3");
        printReceipt("input3.txt");

    }

    private static void printReceipt(final String fileName) {
        final List<ProductDto> productDtoList = InputProductReaderService.readProductInput(fileName);
        final List<Product> productList = ProductDtoToProductMapper.convertList(productDtoList);
        final Receipt receipt = ReceiptService.contructReceipt(productList);

        ReceiptService.printReceipt(receipt);
    }
}
