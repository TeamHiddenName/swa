package de.hska.vs.productms.service;

import de.hska.vs.productms.rest.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDto> findAll();

    List<ProductDto> findAllByCategory(int categoryId);

    void deleteProduct(int id);

    Optional<ProductDto> addProduct(ProductDto productEntity);

    Optional<ProductDto> findById(int id);

    Optional<ProductDto> findByName(String name);
}
