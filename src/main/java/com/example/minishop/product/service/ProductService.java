package com.example.minishop.product.service;

import com.example.minishop.product.mapper.ProductMapper;
import com.example.minishop.product.model.Product;
import com.example.minishop.product.model.ProductDocument;
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
    public Product create(Product product) {
        Product saved = productRepository.save(product);

        ProductDocument document = ProductMapper.toDocument(saved);
        productSearchRepository.save(document);

        return saved;
    }

    @Transactional
    public Product update(Long id, Product updatedProduct) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        existing.setCategory(updatedProduct.getCategory());

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
