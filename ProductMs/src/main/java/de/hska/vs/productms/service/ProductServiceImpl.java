package de.hska.vs.productms.service;

import de.hska.vs.productms.database.entity.ProductEntity;
import de.hska.vs.productms.database.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductEntity> findAllByCategory(int categoryId) {
        return productRepository.findAllByCategory(categoryId);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public Optional<ProductEntity> addProduct(ProductEntity productEntity) {
        if (!validateProduct(productEntity)) {
            return Optional.empty();
        }
        return Optional.of(productRepository.save(productEntity));
    }

    private boolean validateProduct(ProductEntity productEntity) {
        if (productEntity.getCategory() == 0) {
            return false;
        }
        if (productEntity.getName() == null || productEntity.getName().isEmpty()) {
            return false;
        }
        return !(productEntity.getPrice() < 0);
    }
}
