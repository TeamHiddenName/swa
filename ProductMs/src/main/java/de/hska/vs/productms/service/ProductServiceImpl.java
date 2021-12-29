package de.hska.vs.productms.service;

import de.hska.vs.productms.database.entity.ProductEntity;
import de.hska.vs.productms.database.repository.ProductRepository;
import de.hska.vs.productms.rest.CategoryDto;
import de.hska.vs.productms.rest.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> findAll() {
        LOG.debug("Find all called");
        List<ProductEntity> productEntities = productRepository.findAll();

        List<ProductDto> productList = new ArrayList<>();
        for (ProductEntity entity : productEntities) {
            RestTemplate restTemplate = new RestTemplate();
            CategoryDto result = restTemplate.getForObject("http://category-ms:8080/category/" + entity.getCategory(), CategoryDto.class);
            productList.add(convert(entity, result));
        }
        LOG.info("Found " + productList.size() + " products");
        return productList;
    }

    @Override
    public List<ProductDto> findAllByCategory(int categoryId) {

        LOG.debug("Find all by category for categoryId " + categoryId);
        Optional<CategoryDto> categoryDto = getCategory(categoryId);
        List<ProductDto> result;
        if (categoryDto.isPresent()) {
            List<ProductEntity> productEntities = productRepository.findAllByCategory(categoryId);
            result = productEntities
                    .stream()
                    .map(entity -> convert(entity, categoryDto.get()))
                    .collect(Collectors.toList());
        } else {
            result = Collections.emptyList();
        }
        LOG.info("Found " + result.size() + " products for category with id " + categoryId);
        return result;
    }

    @Override
    public void deleteProduct(int id) {
        LOG.info("Deleting product with id " + id);
        productRepository.deleteById(id);
    }

    @Override
    public Optional<ProductDto> addProduct(ProductDto productDto) {
        LOG.debug("Adding product " + productDto);
        if (!validateProduct(productDto)) {
            LOG.info("Product not created. Was invalid");
            return Optional.empty();
        }
        ProductEntity entity = new ProductEntity(productDto.getName(), productDto.getPrice(), productDto.getCategory().getId(), productDto.getDetails());
        ProductEntity saved = productRepository.save(entity);
        ProductDto result = convert(saved, productDto.getCategory());
        LOG.info("Successfully created product " + result);
        return Optional.of(result);
    }

    @Override
    public Optional<ProductDto> findById(int id) {
        LOG.debug("Getting product by id " + id);
        Optional<ProductEntity> product = productRepository.findById(id);

        if (product.isPresent()) {
            return composeProduct(product.get());
        } else {
            LOG.info("Did not find product with id " + id);
            return Optional.empty();
        }

    }

    @Override
    public Optional<ProductDto> findByName(String name) {
        LOG.debug("Getting product by name " + name);
        Optional<ProductEntity> product = productRepository.findByName(name);

        if (product.isPresent()) {
            return composeProduct(product.get());
        } else {
            LOG.info("Did not find product with name " + name);
            return Optional.empty();
        }
    }

    private Optional<ProductDto> composeProduct(ProductEntity product) {
        Optional<CategoryDto> category = getCategory(product.getCategory());
        if (category.isPresent()) {
            ProductDto result = convert(product, category.get());
            LOG.info("Found product " + result);
            return Optional.of(result);
        } else {
            LOG.info("Did not find category with id " + product.getCategory());
            return Optional.empty();
        }
    }

    private boolean validateProduct(ProductDto product) {
        if (product.getCategory().getId() == 0 || !getCategory(product.getCategory().getId()).isPresent()) {
            LOG.debug("Category of product " + product + " was invalid");
            return false;
        }
        if (product.getName() == null || product.getName().isEmpty()) {
            LOG.debug("Product name was invalid");
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
