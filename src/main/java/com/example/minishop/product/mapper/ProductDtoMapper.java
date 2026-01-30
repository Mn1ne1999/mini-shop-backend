package com.example.minishop.product.mapper;

import com.example.minishop.product.dto.ProductResponse;
import com.example.minishop.product.model.Product;
import com.example.minishop.product.model.ProductDocument;

public class ProductDtoMapper {

    public static ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
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
    public static ProductResponse fromDocument(ProductDocument doc) {
        return ProductResponse.builder()
                .id(doc.getId())
                .name(doc.getName())
                .description(doc.getDescription())
                .price(doc.getPrice())
                .categoryName(doc.getCategoryName())
                .build();
    }

}
