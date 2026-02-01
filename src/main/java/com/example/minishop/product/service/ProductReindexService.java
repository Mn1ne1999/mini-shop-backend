package com.example.minishop.product.service;

import com.example.minishop.product.mapper.ProductMapper;
import com.example.minishop.product.repository.ProductRepository;
import com.example.minishop.product.repository.ProductSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductReindexService {

    private final ProductRepository productRepository;
    private final ProductSearchRepository productSearchRepository;

    @Transactional(readOnly = true)
    public void reindexAll() {
        // очищаем индекс
        productSearchRepository.deleteAll();

        // загружаем все товары из БД
        productRepository.findAll().forEach(product ->
                productSearchRepository.save(
                        ProductMapper.toDocument(product)
                )
        );
    }
}
