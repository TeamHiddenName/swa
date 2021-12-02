package de.hska.vs.productms.rest;

import de.hska.vs.productms.database.entity.ProductEntity;
import de.hska.vs.productms.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
public class RestProductController {

    private final ProductService productService;

    public RestProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/product/", produces = "application/json")
    public List<ProductDto> getAll(@RequestParam(required = false, defaultValue = "0") int categoryId) {
        if(categoryId > 0){
            return productService.findAllByCategory(categoryId);
        }
        return productService.findAll();
    }

    @DeleteMapping(value = "/product/{id}")
    public void delete(@PathVariable int id) {
        productService.deleteProduct(id);
    }

    @PostMapping(value = "/product",produces = "application/json")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productEntity) {

        Optional<ProductDto> result = productService.addProduct(productEntity);
        return ResponseEntity.of(result);
    }
}
