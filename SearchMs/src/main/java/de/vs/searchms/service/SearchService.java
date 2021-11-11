package de.vs.searchms.service;

import de.vs.searchms.database.dao.ProductDao;
import de.vs.searchms.database.entity.ProductEntity;
import de.vs.searchms.database.repository.ProductRepository;
import de.vs.searchms.rest.SearchController;
import de.vs.searchms.rest.dto.SearchResult;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchService {

    private final ProductDao productDao;
    private ProductRepository productRepository;

    public SearchService(ProductDao productDao) {
        this.productDao = productDao;
        productRepository.f
    }

    public List<SearchResult> search(double minPrice, double maxPrice, String description) {
        return productDao.findByCriteria(minPrice, maxPrice, description).stream()
                .map(entity -> new SearchResult(entity.getId(), entity.getName(), entity.getPrice(), entity.getCategory().getName()))
                .collect(Collectors.toList());
    }
}
