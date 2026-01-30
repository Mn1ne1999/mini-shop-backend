package com.example.minishop.product.repository;

import com.example.minishop.product.model.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductSearchRepository
        extends ElasticsearchRepository<ProductDocument, Long> {

    List<ProductDocument> findByNameContaining(String name);

    List<ProductDocument> findByCategoryName(String categoryName);
    List<ProductDocument> findByNameContainingOrDescriptionContaining(String name, String description);

}
