package com.example.minishop.order.service;

import com.example.minishop.cart.dto.CartResponse;
import com.example.minishop.cart.service.CartService;
import com.example.minishop.order.dto.CheckoutResponse;
import com.example.minishop.order.dto.OrderResponse;
import com.example.minishop.order.mapper.OrderMapper;
import com.example.minishop.order.model.Order;
import com.example.minishop.order.model.OrderItem;
import com.example.minishop.order.model.OrderStatus;
import com.example.minishop.order.repository.OrderRepository;
import com.example.minishop.user.model.User;
import com.example.minishop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartService cartService;


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

    @Transactional
    public CheckoutResponse checkout() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        CartResponse cart = cartService.getCart();

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = Order.builder()
                .userId(user.getId())
                .status(OrderStatus.NEW)
                .totalAmount(cart.getTotalAmount())
                .build();

        List<OrderItem> items = cart.getItems().stream()
                .map(ci -> OrderItem.builder()
                        .order(order)
                        .productId(ci.getProductId())
                        .productNameSnapshot(ci.getProductName())
                        .price(ci.getPrice())
                        .quantity(ci.getQuantity())
                        .lineTotal(ci.getLineTotal())
                        .build())
                .toList();

        order.setItems(items);

        Order saved = orderRepository.save(order);

        cartService.clearCart();

        return CheckoutResponse.builder()
                .orderId(saved.getId())
                .totalAmount(saved.getTotalAmount())
                .status(saved.getStatus())
                .build();
    }
}
