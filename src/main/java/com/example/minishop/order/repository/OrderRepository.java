package com.example.minishop.order.repository;

import com.example.minishop.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(UUID userId);
}
