package com.example.minishop.product.service;

import com.example.minishop.common.exception.NotFoundException;
import com.example.minishop.product.dto.ProductCreateRequest;
import com.example.minishop.product.mapper.ProductMapper;
import com.example.minishop.product.model.Product;
import com.example.minishop.product.repository.ProductRepository;
import com.example.minishop.product.repository.ProductSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductSearchRepository productSearchRepository;

    @Transactional
    public Product create(ProductCreateRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

        Product saved = productRepository.save(product);
        productSearchRepository.save(ProductMapper.toDocument(saved));

        return saved;
    }

    @Transactional
    public Product update(Long id, ProductCreateRequest request) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));


        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setPrice(request.getPrice());

        Product saved = productRepository.save(existing);
        productSearchRepository.save(ProductMapper.toDocument(saved));

        return saved;
    }


    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
        productSearchRepository.deleteById(id);
    }
}
