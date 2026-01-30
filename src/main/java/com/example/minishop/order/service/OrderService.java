package com.example.minishop.order.service;

import com.example.minishop.order.dto.OrderResponse;
import com.example.minishop.order.mapper.OrderMapper;
import com.example.minishop.order.repository.OrderRepository;
import com.example.minishop.user.model.User;
import com.example.minishop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public List<OrderResponse> getMyOrders() {

        // берём email из SecurityContext
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        // находим пользователя
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // берём ТОЛЬКО его заказы
        return orderRepository.findAllByUserId(user.getId()).stream()
                .map(OrderMapper::toResponse)
                .toList();
    }
}
