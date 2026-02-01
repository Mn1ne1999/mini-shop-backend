package com.example.minishop.product.controller;

import com.example.minishop.product.service.ProductReindexService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductReindexService reindexService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/reindex")
    public void reindex() {
        reindexService.reindexAll();
    }
}
