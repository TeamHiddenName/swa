package de.hska.vs.productms.service;

import de.hska.vs.productms.database.entity.ProductEntity;
import de.hska.vs.productms.database.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductEntity> findAll();

    List<ProductEntity> findAllByCategory(int categoryId);

    void deleteProduct(int id);

    Optional<ProductEntity> addProduct(ProductEntity productEntity);
}
