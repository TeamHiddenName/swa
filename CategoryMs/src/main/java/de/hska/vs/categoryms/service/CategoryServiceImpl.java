package de.hska.vs.categoryms.service;

import de.hska.vs.categoryms.database.entity.CategoryEntity;
import de.hska.vs.categoryms.database.repository.CategoryRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        Boolean result = restTemplate.getForObject("http://product-web/category/deletable/" + id, Boolean.class);
        if (result != null && result) {
            restTemplate.delete("http://product-web/category/" + id);
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
    public List<CategoryEntity> getAll() {
        return categoryRepository.findAll();
    }

    private boolean validateCategory(CategoryEntity category) {
        return category.getName() != null && !category.getName().isEmpty();
    }
}
