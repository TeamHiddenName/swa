package de.hska.vs.productms.rest;

import de.hska.vs.productms.database.entity.ProductEntity;
import de.hska.vs.productms.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("/product")
public class RestProductController {

    private final ProductService productService;

    public RestProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(produces = "application/json")
    public List<ProductDto> getAll() {
        return productService.findAll();
    }

    @GetMapping(produces = "application/json")
    public List<ProductDto> getAllByCategory(@RequestParam(required = false, defaultValue = "0") int categoryId){
        return productService.findAllByCategory(categoryId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        productService.deleteProduct(id);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productEntity) {

        Optional<ProductDto> result = productService.addProduct(productEntity);
        return ResponseEntity.of(result);
    }
}
