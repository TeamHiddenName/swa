package de.hska.vs.productms.service;

import de.hska.vs.productms.database.entity.ProductEntity;
import de.hska.vs.productms.database.repository.ProductRepository;
import de.hska.vs.productms.rest.CategoryDto;
import de.hska.vs.productms.rest.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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
            CategoryDto result = restTemplate.getForObject("http://category-web/category/" + entity.getCategory(), CategoryDto.class);
            productList.add(convert(entity, result));
        }
        return productList;
    }

    @Override
    public List<ProductDto> findAllByCategory(int categoryId) {

        RestTemplate restTemplate = new RestTemplate();
        CategoryDto result = restTemplate.getForObject("http://category-web/category/" + categoryId, CategoryDto.class);


        List<ProductEntity> productEntities = productRepository.findAllByCategory(categoryId);
        return productEntities
                .stream()
                .map(entity -> convert(entity, result))
                .collect(Collectors.toList());
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

    private boolean validateProduct(ProductDto product) {
        if (product.getCategory().getId() == 0) {
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
}
