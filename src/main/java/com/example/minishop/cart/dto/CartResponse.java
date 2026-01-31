package com.example.minishop.cart.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CartResponse {
    private List<CartItemDto> items;
    private BigDecimal totalAmount;
}
