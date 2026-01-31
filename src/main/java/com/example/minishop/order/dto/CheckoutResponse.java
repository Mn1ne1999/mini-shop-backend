package com.example.minishop.order.dto;

import com.example.minishop.order.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CheckoutResponse {
    private Long orderId;
    private BigDecimal totalAmount;
    private OrderStatus status;
}
