package com.example.minishop.order.controller;

import com.example.minishop.order.dto.CheckoutResponse;
import com.example.minishop.order.dto.OrderResponse;
import com.example.minishop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderResponse> getMyOrders() {
        return orderService.getMyOrders();
    }

    @PostMapping("/checkout")
    public CheckoutResponse checkout() {
        return orderService.checkout();
    }
}
