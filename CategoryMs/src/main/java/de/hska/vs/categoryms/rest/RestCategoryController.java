package de.hska.vs.categoryms.rest;

import de.hska.vs.categoryms.database.entity.CategoryEntity;
import de.hska.vs.categoryms.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/category")
public class RestCategoryController {

    private final CategoryService categoryService;

    public RestCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryEntity> getCategory(@PathVariable int id) {
        Optional<CategoryEntity> result = categoryService.getCategory(id);
        return ResponseEntity.of(result);
    }

    @GetMapping()
    public List<CategoryEntity> getAllCategories() {
        return categoryService.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable  int id) {
        if (categoryService.deleteCategory(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<CategoryEntity> create(@RequestBody String name) {
        return ResponseEntity.of(categoryService.createCategory(new CategoryEntity(name)));
    }

    @PostMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody String name) {
        categoryService.updateCategory(new CategoryEntity(id, name));
    }
}
