package com.example.minishop.cart.service;

import com.example.minishop.cart.dto.CartItemDto;
import com.example.minishop.cart.dto.CartResponse;
import com.example.minishop.product.model.Product;
import com.example.minishop.product.repository.ProductRepository;
import com.example.minishop.user.model.User;
import com.example.minishop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Value("${cart.ttl-days}")
    private long cartTtlDays;

    @Value("${cart.max-qty}")
    private int maxQty;

    private String getCartKey() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return "cart:" + user.getId();
    }

    public void addItem(Long productId, int quantity) {

        if (quantity < 1 || quantity > maxQty) {
            throw new RuntimeException(
                    "Quantity must be between 1 and " + maxQty
            );
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        String key = getCartKey();

        HashOperations<String, String, CartItemDto> ops =
                redisTemplate.opsForHash();

        String productKey = productId.toString();

        CartItemDto item = ops.get(key, productKey);

        if (item == null) {
            item = CartItemDto.builder()
                    .productId(productId)
                    .productName(product.getName())
                    .price(product.getPrice())
                    .quantity(quantity)
                    .lineTotal(
                            product.getPrice()
                                    .multiply(BigDecimal.valueOf(quantity))
                    )
                    .build();
        } else {
            int newQty = item.getQuantity() + quantity;

            if (newQty > maxQty) {
                throw new RuntimeException(
                        "Quantity must be <= " + maxQty
                );
            }

            item.setQuantity(newQty);
            item.setLineTotal(
                    item.getPrice()
                            .multiply(BigDecimal.valueOf(newQty))
            );
        }

        ops.put(key, productKey, item);

        redisTemplate.expire(
                key,
                Duration.ofDays(cartTtlDays)
        );
    }

    public CartResponse getCart() {

        String key = getCartKey();

        HashOperations<String, String, CartItemDto> ops =
                redisTemplate.opsForHash();

        List<CartItemDto> items = ops.values(key);

        if (items == null || items.isEmpty()) {
            return CartResponse.builder()
                    .items(List.of())
                    .totalAmount(BigDecimal.ZERO)
                    .build();
        }

        BigDecimal total = items.stream()
                .map(CartItemDto::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponse.builder()
                .items(items)
                .totalAmount(total)
                .build();
    }

    public void removeItem(Long productId) {
        redisTemplate.opsForHash()
                .delete(getCartKey(), productId.toString());
    }

    public void clearCart() {
        redisTemplate.delete(getCartKey());
    }
}
