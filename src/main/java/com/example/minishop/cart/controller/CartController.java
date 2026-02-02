package com.example.minishop.cart.controller;

import com.example.minishop.cart.dto.CartResponse;
import com.example.minishop.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class CartController {

    private final CartService cartService;

    @PostMapping("/items/{productId}")
    public void add(
            @PathVariable Long productId,
            @RequestParam int qty
    ) {
        cartService.addItem(productId, qty);
    }

    @GetMapping
    public CartResponse get() {
        return cartService.getCart();
    }

    @DeleteMapping("/items/{productId}")
    public void remove(@PathVariable Long productId) {
        cartService.removeItem(productId);
    }

    @DeleteMapping("/clear")
    public void clear() {
        cartService.clearCart();
    }
}

