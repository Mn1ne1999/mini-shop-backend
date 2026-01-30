package com.example.minishop.order.mapper;

import com.example.minishop.order.dto.OrderItemResponse;
import com.example.minishop.order.dto.OrderResponse;
import com.example.minishop.order.model.Order;

import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .items(
                        order.getItems().stream()
                                .map(item -> OrderItemResponse.builder()
                                        .productId(item.getProductId())
                                        .productName(item.getProductNameSnapshot())
                                        .price(item.getPrice())
                                        .quantity(item.getQuantity())
                                        .lineTotal(item.getLineTotal())
                                        .build()
                                )
                                .collect(Collectors.toList())
                )
                .build();
    }
}
