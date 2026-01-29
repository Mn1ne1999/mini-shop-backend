package com.example.minishop.product.mapper;

import com.example.minishop.product.model.Product;
import com.example.minishop.product.model.ProductDocument;

public class ProductMapper {

    public static ProductDocument toDocument(Product product) {
        return ProductDocument.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .categoryName(
                        product.getCategory() != null
                                ? product.getCategory().getName()
                                : null
                )
                .build();
    }
}
