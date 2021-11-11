package de.hska.vs.productms.rest;

import de.hska.vs.productms.service.CategoryService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/category")
public class RestCategoryController {

    private final CategoryService categoryService;

    public RestCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        categoryService.deleteCategory(id);
    }
}
