package com.example.minishop.product.mapper;

import com.example.minishop.product.dto.ProductResponse;
import com.example.minishop.product.model.Product;

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
}
