package com.example.minishop.order.controller;

import com.example.minishop.order.dto.CheckoutResponse;
import com.example.minishop.order.dto.OrderResponse;
import com.example.minishop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // USER и ADMIN — каждый увидит свои
    @GetMapping
    public List<OrderResponse> getMyOrders() {
        return orderService.getMyOrders();
    }

    // ТОЛЬКО USER делает checkout
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/checkout")
    public CheckoutResponse checkout() {
        return orderService.checkout();
    }

    // USER — свой заказ, ADMIN — любой
    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}

