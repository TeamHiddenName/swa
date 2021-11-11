package de.hska.vs.categoryms.service;

import de.hska.vs.categoryms.database.entity.CategoryEntity;
import de.hska.vs.categoryms.database.repository.CategoryRepository;
import org.springframework.stereotype.Service;

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
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
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
