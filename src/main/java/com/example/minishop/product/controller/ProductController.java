package com.example.minishop.product.controller;

import com.example.minishop.product.dto.ProductCreateRequest;
import com.example.minishop.product.dto.ProductResponse;
import com.example.minishop.product.mapper.ProductDtoMapper;
import com.example.minishop.product.model.Product;
import com.example.minishop.product.model.ProductDocument;
import com.example.minishop.product.service.ProductSearchService;
import com.example.minishop.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductSearchService productSearchService;

    @PostMapping
    public ProductResponse create(@RequestBody @Valid ProductCreateRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

        return ProductDtoMapper.toResponse(productService.create(product));
    }

    @PutMapping("/{id}")
    public ProductResponse update(
            @PathVariable Long id,
            @RequestBody @Valid ProductCreateRequest request
    ) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

        return ProductDtoMapper.toResponse(productService.update(id, product));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping("/search")
    public List<ProductDocument> search(@RequestParam String q) {
        return productSearchService.search(q);
    }
}
