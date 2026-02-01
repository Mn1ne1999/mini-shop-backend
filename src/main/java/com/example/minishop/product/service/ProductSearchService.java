package com.example.minishop.product.service;

import com.example.minishop.product.model.ProductDocument;
import com.example.minishop.product.repository.ProductSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductSearchRepository productSearchRepository;
    private final ElasticsearchOperations elasticsearchOperations;


    public List<ProductDocument> search(String query) {
        return productSearchRepository
                .findByNameContainingOrDescriptionContaining(query, query);
    }

    public List<ProductDocument> search(
            String q,
            String category,
            BigDecimal priceFrom,
            BigDecimal priceTo,
            String sort,
            int page,
            int size
    ) {
        Criteria criteria = new Criteria();

        if (q != null && !q.isBlank()) {
            criteria = criteria.and(
                    new Criteria("name").contains(q)
                            .or(new Criteria("description").contains(q))
            );
        }

        if (category != null) {
            criteria = criteria.and(new Criteria("categoryName").is(category));
        }

        if (priceFrom != null || priceTo != null) {
            Criteria price = new Criteria("price");
            if (priceFrom != null) price = price.greaterThanEqual(priceFrom);
            if (priceTo != null) price = price.lessThanEqual(priceTo);
            criteria = criteria.and(price);
        }

        Sort sortBy = Sort.unsorted();
        if ("price_asc".equals(sort)) {
            sortBy = Sort.by("price").ascending();
        } else if ("price_desc".equals(sort)) {
            sortBy = Sort.by("price").descending();
        } else if ("newest".equals(sort)) {
            sortBy = Sort.by("createdAt").descending();
        }

        CriteriaQuery query = new CriteriaQuery(criteria)
                .setPageable(PageRequest.of(page, size, sortBy));

        SearchHits<ProductDocument> hits =
                elasticsearchOperations.search(query, ProductDocument.class);

        return hits.stream()
                .map(hit -> hit.getContent())
                .toList();
    }
}
