package com.example.minishop.product.controller;

import com.example.minishop.product.dto.ProductCreateRequest;
import com.example.minishop.product.dto.ProductResponse;
import com.example.minishop.product.mapper.ProductDtoMapper;
import com.example.minishop.product.service.ProductSearchService;
import com.example.minishop.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductSearchService productSearchService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProductResponse create(@RequestBody @Valid ProductCreateRequest request) {
        return ProductDtoMapper.toResponse(
                productService.create(request)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ProductResponse update(
            @PathVariable Long id,
            @RequestBody @Valid ProductCreateRequest request
    ) {
        return ProductDtoMapper.toResponse(
                productService.update(id, request)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping("/search")
    public List<ProductResponse> search(@RequestParam String q) {
        return productSearchService.search(q).stream()
                .map(ProductDtoMapper::fromDocument)
                .toList();
    }

    @GetMapping
    public List<ProductResponse> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal priceFrom,
            @RequestParam(required = false) BigDecimal priceTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort
    ) {
        return productSearchService
                .search(q, category, priceFrom, priceTo, sort, page, size)
                .stream()
                .map(ProductDtoMapper::fromDocument)
                .toList();
    }


}
