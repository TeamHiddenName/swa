package de.hska.vs.productms.service;

import de.hska.vs.productms.database.entity.ProductEntity;
import de.hska.vs.productms.database.repository.ProductRepository;
import de.hska.vs.productms.rest.CategoryDto;
import de.hska.vs.productms.rest.ProductDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> findAll() {

        List<ProductEntity> productEntities = productRepository.findAll();

        List<ProductDto> productList = new ArrayList<>();
        for (ProductEntity entity : productEntities) {
            RestTemplate restTemplate = new RestTemplate();
            CategoryDto result = restTemplate.getForObject("http://category-ms:8080/category/" + entity.getCategory(), CategoryDto.class);
            productList.add(convert(entity, result));
        }
        return productList;
    }

    @Override
    public List<ProductDto> findAllByCategory(int categoryId) {


        Optional<CategoryDto> categoryDto = getCategory(categoryId);
        if (categoryDto.isPresent()) {
            List<ProductEntity> productEntities = productRepository.findAllByCategory(categoryId);
            return productEntities
                    .stream()
                    .map(entity -> convert(entity, categoryDto.get()))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public Optional<ProductDto> addProduct(ProductDto productDto) {
        if (!validateProduct(productDto)) {
            return Optional.empty();
        }
        ProductEntity entity = new ProductEntity(productDto.getName(), productDto.getPrice(), productDto.getCategory().getId(), productDto.getDetails());
        ProductEntity saved = productRepository.save(entity);
        return Optional.of(convert(saved, productDto.getCategory()));
    }

    @Override
    public Optional<ProductDto> findById(int id) {

        Optional<ProductEntity> product = productRepository.findById(id);

        if (product.isPresent()) {
            RestTemplate restTemplate = new RestTemplate();
            CategoryDto result = restTemplate.getForObject("http://category-ms:8080/category/" + product.get().getCategory(), CategoryDto.class);
            return Optional.of(convert(product.get(), result));
        } else {
            return Optional.empty();
        }

    }

    @Override
    public Optional<ProductDto> findByName(String name) {
        Optional<ProductEntity> product = productRepository.findByName(name);

        if (product.isPresent()) {
            RestTemplate restTemplate = new RestTemplate();
            CategoryDto result = restTemplate.getForObject("http://category-ms:8080/category/" + product.get().getCategory(), CategoryDto.class);
            return Optional.of(convert(product.get(), result));
        } else {
            return Optional.empty();
        }
    }

    private boolean validateProduct(ProductDto product) {
        if (product.getCategory().getId() == 0 || !getCategory(product.getCategory().getId()).isPresent()) {
            return false;
        }
        if (product.getName() == null || product.getName().isEmpty()) {
            return false;
        }
        return !(product.getPrice() < 0);
    }

    private ProductDto convert(ProductEntity entity, CategoryDto categoryDto) {
        return new ProductDto(entity.getId(), entity.getName(), entity.getPrice(), categoryDto, entity.getDetails());
    }

    private Optional<CategoryDto> getCategory(int id) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<CategoryDto> result = restTemplate.exchange(new RequestEntity<>(HttpMethod.GET, new URI("http://category-ms:8080/category/" + id)), CategoryDto.class);
            if (result.getStatusCode() == HttpStatus.OK) {
                return Optional.ofNullable(result.getBody());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
