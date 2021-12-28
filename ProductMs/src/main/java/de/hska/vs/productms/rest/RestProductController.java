package de.hska.vs.productms.rest;

import de.hska.vs.productms.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("")
public class RestProductController {

    private final ProductService productService;

    public RestProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/product", produces = "application/json")
    public List<ProductDto> getAll(@RequestParam(defaultValue = "-1") String categoryId) {
        int cId = Integer.valueOf(categoryId, 10);
        if (cId <= -1) {
            return productService.findAll();
        } else {
            return productService.findAllByCategory(cId);
        }

    }

    @GetMapping(value = "/product/{id}", produces = "application/json")
    public ResponseEntity<ProductDto> getById(@PathVariable int id) {
        return ResponseEntity.of(productService.findById(id));
    }

    @GetMapping(value = "/product?name={name}", produces = "application/json")
    public ResponseEntity<ProductDto> getByName(@PathVariable String name) {
        return ResponseEntity.of(productService.findByName(name));
    }

    @DeleteMapping(value = "/product/{id}")
    public void delete(@PathVariable int id) {
        productService.deleteProduct(id);
    }

    @PostMapping(value = "/product", produces = "application/json")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productEntity) {

        Optional<ProductDto> result = productService.addProduct(productEntity);
        return ResponseEntity.of(result);
    }
}
