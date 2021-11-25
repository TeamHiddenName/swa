package de.hska.vs.productms.service;

import de.hska.vs.productms.database.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final ProductRepository productRepository;

    public CategoryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void deleteCategory(int id) {
        productRepository.deleteByCategory(id);
    }

    @Override
    public boolean canDelete(int id) {
        return productRepository.countAllByCategory(id) == 0;
    }
}
