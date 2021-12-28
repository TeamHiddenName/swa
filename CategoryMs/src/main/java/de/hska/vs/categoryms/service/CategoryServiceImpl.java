package de.hska.vs.categoryms.service;

import de.hska.vs.categoryms.database.entity.CategoryEntity;
import de.hska.vs.categoryms.database.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<CategoryEntity> createCategory(CategoryEntity category) {
        if (validateCategory(category)) {
            return Optional.of(categoryRepository.save(category));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteCategory(int id) {
        RestTemplate restTemplate = new RestTemplate();
        Object[] result = restTemplate.getForObject("http://product-ms:8080/product?categoryId=" + id, Object[].class);
        if (result == null || result.length == 0) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;

    }

    @Override
    public boolean updateCategory(CategoryEntity category) {
        if (validateCategory(category) && category.getId() >= 0) {
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CategoryEntity> getCategory(int id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<CategoryEntity> getCategory(String name) {
        return categoryRepository.findCategoryEntityByName(name);
    }

    @Override
    public List<CategoryEntity> getAll() {
        return categoryRepository.findAll();
    }

    private boolean validateCategory(CategoryEntity category) {
        return category.getName() != null && !category.getName().isEmpty();
    }
}
