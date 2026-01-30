package com.example.minishop.product.service;

import com.example.minishop.product.model.ProductDocument;
import com.example.minishop.product.repository.ProductSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductSearchRepository productSearchRepository;

    public List<ProductDocument> search(String query) {
        return productSearchRepository
                .findByNameContainingOrDescriptionContaining(query, query);
    }
}
